using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;
using BluffinMuffin.Protocol.Lobby;
using Com.Ericmas001.Collections;
using Com.Ericmas001.Util;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using BluffinMuffin.Poker.DataTypes;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Client
{
    public delegate void DisconnectDelegate();
    public class LobbyTcpClient : TcpCommunicator
    {
        #region Fields

        private readonly Dictionary<int, GameTcpClient> m_Clients = new Dictionary<int, GameTcpClient>();
        private readonly BlockingQueue<string> m_Incoming = new BlockingQueue<string>();
        #endregion Fields

        #region Events
        public event DisconnectDelegate ServerLost = delegate{};
        #endregion Events

        #region Properties

        public string PlayerName { get; protected set; }
        public string ServerAddress { get; private set; }
        public int ServerPort { get; private set; }
        #endregion Properties

        #region Ctors & Init

        protected LobbyTcpClient(string serverAddress, int serverPort)
        {
            ServerAddress = serverAddress;
            ServerPort = serverPort;
        }
        #endregion Ctors & Init

        #region GameClient Event Handler

        private void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            Send(e.Key);
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

        protected void Send(AbstractCommand command)
        {
            var encode = command.Encode();
            LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Receive", "{0} SENT [{1}]", PlayerName, encode);
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

        public GameTcpClient FindClient(int noPort)
        {
            if (m_Clients.ContainsKey(noPort))
                return m_Clients[noPort];

            return null;
        }

        public void JoinTable(int idTable, string tableName, IPokerViewer gui)
        {
            var ok = GetJoinedSeat(idTable, PlayerName);
            if (!ok)
            {
                LogManager.Log(LogLevel.MessageLow, "LobbyTcpClient.JoinTable", "Cannot join the table: {0}:{1}", tableName, idTable);
                return;
            }

            var client = new GameTcpClient(PlayerName, idTable);
            client.SendedSomething += client_SendedSomething;

            if (gui != null)
            {
                gui.SetGame(client);
                gui.Start();
            }

            client.Start();

            m_Clients.Add(idTable, client);
        }

        public int CreateTable(TableParams parms)
        {
            Send(new CreateTableCommand() { Params = parms });

            return WaitAndReceive<CreateTableResponse>().IdTable;
        }

        #endregion Public Methods

        #region Protected Methods

        protected T WaitAndReceive<T>() where T : AbstractCommand
        {
            var expected = typeof (T).Name;
            string s;
            string commandName;

            do
            {
                s = m_Incoming.Dequeue();
                JObject jObj = JsonConvert.DeserializeObject<dynamic>(s);
                commandName = (string)jObj["CommandName"];
            }
            while (s != null && commandName != expected);

            return JsonConvert.DeserializeObject<T>(s);
        }

        protected virtual bool GetJoinedSeat(int idTable, string player)
        {
            Send(new JoinTableCommand()
            {
                TableId = idTable,
            });

            return WaitAndReceive<JoinTableResponse>().Success;
        }

        public IEnumerable<RuleInfo> GetSupportedRules()
        {
            var cmd = new SupportedRulesCommand();
            Send(cmd);

            return WaitAndReceive<SupportedRulesResponse>().Rules;
        }

        protected override void Run()
        {
            while (IsConnected)
            {
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Run", "{0} IS WAITING", PlayerName);

                var line = Receive();
                if (line == null)
                {
                    ServerLost();
                    return;
                }

                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTcpClient.Run", "{0} RECV [{1}]", PlayerName, line);

                AbstractBluffinCommand cmd = AbstractBluffinCommand.DeserializeCommand(line);
                if (cmd.CommandType == BluffinCommandEnum.Game)
                {
                    var c = (IGameCommand) cmd;

                    //Be patient
                    var count = 0;
                    while (!m_Clients.ContainsKey(c.TableId) && (count++ < 5))
                        Thread.Sleep(100);

                    if (m_Clients.ContainsKey(c.TableId))
                        m_Clients[c.TableId].Incoming(line);
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
