using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using System.Linq;
using Com.Ericmas001.Collections;
using Com.Ericmas001.Net;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.Reflection;
using System.Web;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Runtime.Serialization.Formatters;
using Com.Ericmas001.Util;
using Com.Ericmas001.Net.Protocol;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public delegate void DisconnectDelegate();
    public class LobbyTcpClient : TcpCommunicator
    {
        #region Fields
        protected Dictionary<int, GameTcpClient> m_Clients = new Dictionary<int, GameTcpClient>();
        protected BlockingQueue<string> m_Incoming = new BlockingQueue<string>();
        #endregion Fields

        #region Events
        public event DisconnectDelegate ServerLost = delegate{};
        #endregion Events

        #region Properties

        public string PlayerName { get; protected set; }
        public string ServerAddress { get; protected set; }
        public int ServerPort { get; protected set; }
        #endregion Properties

        #region Ctors & Init
        public LobbyTcpClient(string serverAddress, int serverPort)
            : base()
        {
            ServerAddress = serverAddress;
            ServerPort = serverPort;
        }
        #endregion Ctors & Init

        #region GameClient Event Handler
        protected void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            Send(new GameCommand() { TableId = ((GameTcpClient)sender).NoPort, EncodedCommand = e.Key });
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
                GameTcpClient client = m_Clients[idGame];

                m_Clients.Remove(idGame);

                if (client != null)
                    client.Disconnect();
            }
        }

        public override void OnReceiveCrashed(Exception e)
        {
            if (e is IOException)
            {
                LogManager.Log(LogLevel.Error, "LobbyTcpClient.OnReceiveCrashed", "Lobby lost connection with server");
                Disconnect();
            }
            else
                base.OnReceiveCrashed(e);
        }

        public override void OnSendCrashed(Exception e)
        {
            if (e is IOException)
            {
                LogManager.Log(LogLevel.Error, "LobbyTcpClient.OnReceiveCrashed", "Lobby lost connection with server");
                Disconnect();
            }
            else
                base.OnSendCrashed(e);
        }

        public void Send(AbstractCommand command)
        {
            string encode = command.Encode();
            LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Receive", "{0} SENT [{1}]", PlayerName, encode);
            base.Send(encode);
        }

        public void Disconnect()
        {
            foreach (GameTcpClient client in m_Clients.Values)
                client.Disconnect();

            m_Clients.Clear();

            if (IsConnected)
            {
                Send(new DisconnectCommand());
                Close();
            }
        }

        public GameTcpClient FindClient(int noPort)
        {
            if (m_Clients.ContainsKey(noPort))
                return m_Clients[noPort];

            return null;
        }

        public GameTcpClient JoinTable(int idTable, string tableName, IPokerViewer gui)
        {
            bool ok = GetJoinedSeat(idTable, PlayerName);
            if (!ok)
            {
                LogManager.Log(LogLevel.MessageLow, "LobbyTcpClient.JoinTable", "Cannot join the table: {0}:{1}", tableName, idTable);
                return null;
            }

            GameTcpClient client = new GameTcpClient(PlayerName, idTable);
            client.SendedSomething += new EventHandler<KeyEventArgs<string>>(client_SendedSomething);

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
            Send(new CreateTableCommand() { Params = parms });

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
                commandName = (string)jObj["CommandName"];
            }
            while (s != null && commandName != expected);

            return jObj;
        }
        protected T WaitAndReceive<T>() where T : AbstractCommand
        {
            string expected = typeof (T).Name;
            string s;
            string commandName;

            JObject jObj;

            do
            {
                s = m_Incoming.Dequeue();
                jObj = JsonConvert.DeserializeObject<dynamic>(s);
                commandName = (string)jObj["CommandName"];
            }
            while (s != null && commandName != expected);

            return JsonConvert.DeserializeObject<T>(s);
        }

        protected string Receive(StreamReader reader)
        {
            string line;
            try
            {
                line = reader.ReadLine();
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Receive", "{0} RECV [{1}]", PlayerName, line);
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
            SupportedRulesCommand cmd = new SupportedRulesCommand();
            Send(cmd);

            return WaitAndReceive<SupportedRulesResponse>().Rules;
        }

        protected override void Run()
        {
            while (IsConnected)
            {
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Run", "{0} IS WAITING", PlayerName);

                string line = Receive();
                if (line == null)
                {
                    ServerLost();
                    return;
                }

                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Run", "{0} RECV [{1}]", PlayerName, line);

                JObject jObj = JsonConvert.DeserializeObject<dynamic>(line);
                String cmdName = (string)jObj["CommandName"];

                if (cmdName == typeof(GameCommand).Name)
                {
                    GameCommand c = JsonConvert.DeserializeObject<GameCommand>(line);
                    int count = 0;

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
            Send(new ListTableCommand() { LobbyTypes = lobbyTypes });

            return WaitAndReceive<ListTableResponse>().Tables;
        }

        #endregion Protected Methods
    }
}
