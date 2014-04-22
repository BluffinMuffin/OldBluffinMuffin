using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using Com.Ericmas001.Collections;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Util;
using Com.Ericmas001.Net.Protocol;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public delegate void DisconnectDelegate();

    public class LobbyTcpClient1 : TcpCommunicator1
    {
        #region Fields

        private readonly Dictionary<int, GameTcpClient1> m_Clients = new Dictionary<int, GameTcpClient1>();
        private readonly BlockingQueue<string> m_Incoming = new BlockingQueue<string>();

        #endregion Fields

        #region Events

        public event DisconnectDelegate ServerLost = delegate { };

        #endregion Events

        #region Properties

        public string PlayerName { get; protected set; }
        public string ServerAddress { get; protected set; }
        public int ServerPort { get; protected set; }

        #endregion Properties

        #region Ctors & Init

        public LobbyTcpClient1(string serverAddress, int serverPort)
        {
            ServerAddress = serverAddress;
            ServerPort = serverPort;
        }

        #endregion Ctors & Init

        #region GameClient Event Handler

        protected void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            Send(new GameCommand() {TableId = ((GameTcpClient1) sender).NoPort, EncodedCommand = e.Key});
        }

        #endregion GameClient Event Handler

        #region Public Methods

        public bool Connect()
        {
            return base.Connect(ServerAddress, ServerPort);
        }

        public void LeaveTable(int idGame)
        {
            if (m_Clients.ContainsKey(idGame))
            {
                var client = m_Clients[idGame];

                m_Clients.Remove(idGame);

                if (client != null)
                    client.Disconnect();
            }
        }

        public override void OnReceiveCrashed(Exception e)
        {
            if (e is IOException)
            {
                LogManager.Log(LogLevel.Error, "LobbyTCPClient.OnReceiveCrashed", "Lobby lost connection with server");
                Disconnect();
            }
            else
                base.OnReceiveCrashed(e);
        }

        public override void OnSendCrashed(Exception e)
        {
            if (e is IOException)
            {
                LogManager.Log(LogLevel.Error, "LobbyTCPClient.OnReceiveCrashed", "Lobby lost connection with server");
                Disconnect();
            }
            else
                base.OnSendCrashed(e);
        }

        public void Send(AbstractCommand command)
        {
            var encode = command.Encode();
            LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Receive", "{0} SENT [{1}]", PlayerName, encode);
            base.Send(encode);
        }

        public void Disconnect()
        {
            foreach (var client in m_Clients.Values)
                client.Disconnect();

            m_Clients.Clear();

            if (IsConnected)
            {
                Send(new DisconnectCommand());
                Close();
            }
        }

        public GameTcpClient1 FindClient(int noPort)
        {
            if (m_Clients.ContainsKey(noPort))
                return m_Clients[noPort];

            return null;
        }

        public GameTcpClient1 JoinTable(int idTable, string tableName, IPokerViewer gui)
        {
            var ok = GetJoinedSeat(idTable, PlayerName);
            if (!ok)
            {
                LogManager.Log(LogLevel.MessageLow, "LobbyTCPClient.JoinTable", "Cannot join the table: {0}:{1}",
                    tableName, idTable);
                return null;
            }

            var client = new GameTcpClient1(PlayerName, idTable);
            client.SendedSomething += client_SendedSomething;

            if (gui != null)
            {
                gui.SetGame(client, PlayerName);
                gui.Start();
            }

            client.Start();

            m_Clients.Add(idTable, client);

            return client;
        }

        public int CreateTable(TableParams parms)
        {
            Send(new CreateTableCommand() {Params = parms});

            return WaitAndReceive<CreateTableResponse>().IdTable;
        }

        #endregion Public Methods

        #region Protected Methods

        protected JObject WaitAndReceive(string expected)
        {
            string s;
            string commandName;

            JObject jObj;

            do
            {
                s = m_Incoming.Dequeue();
                jObj = JsonConvert.DeserializeObject<dynamic>(s);
                commandName = (string) jObj["CommandName"];
            } while (s != null && commandName != expected);

            return jObj;
        }

        protected T WaitAndReceive<T>() where T : AbstractCommand
        {
            string s;
            string commandName;

            do
            {
                s = m_Incoming.Dequeue();
                JObject jObj = JsonConvert.DeserializeObject<dynamic>(s);
                commandName = (string) jObj["CommandName"];
            } while (s != null && commandName != typeof(T).Name);

            return JsonConvert.DeserializeObject<T>(s);
        }

        protected string Receive(StreamReader reader)
        {
            string line;
            try
            {
                line = reader.ReadLine();
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Receive", "{0} RECV [{1}]", PlayerName, line);
            }
            catch
            {
                return null;
            }
            return line;
        }

        protected virtual bool GetJoinedSeat(int idTable, string player)
        {
            Send(new JoinTableCommand()
            {
                TableId = idTable,
                PlayerName = player,
            });

            return WaitAndReceive<JoinTableResponse>().Success;
        }

        public List<RuleInfo> GetSupportedRules()
        {
            var cmd = new SupportedRulesCommand();
            Send(cmd);

            return WaitAndReceive<SupportedRulesResponse>().Rules;
        }

        protected override void Run()
        {
            while (IsConnected)
            {
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Run", "{0} IS WAITING", PlayerName);

                var line = Receive();
                if (line == null)
                {
                    ServerLost();
                    return;
                }

                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Run", "{0} RECV [{1}]", PlayerName, line);

                JObject jObj = JsonConvert.DeserializeObject<dynamic>(line);
                var cmdName = (string) jObj["CommandName"];

                if (cmdName == typeof(GameCommand).Name)
                {
                    var c = JsonConvert.DeserializeObject<GameCommand>(line);
                    var count = 0;

                    //Be patient
                    while (!m_Clients.ContainsKey(c.TableId) && (count++ < 5))
                        Thread.Sleep(100);

                    if (m_Clients.ContainsKey(c.TableId))
                        m_Clients[c.TableId].Incoming(c.DecodedCommand);
                }
                else
                    m_Incoming.Enqueue(line);
            }
        }

        public List<TupleTable> ListTables(params LobbyTypeEnum[] lobbyTypes)
        {
            Send(new ListTableCommand() {LobbyTypes = lobbyTypes});

            return WaitAndReceive<ListTableResponse>().Tables;
        }

        #endregion Protected Methods
    }
}