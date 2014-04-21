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
    public class LobbyTCPClient : TCPCommunicator
    {
        #region Fields
        protected string m_PlayerName;
        protected string m_ServerAddress;
        protected int m_ServerPort;
        protected Dictionary<int, GameTCPClient> m_Clients = new Dictionary<int, GameTCPClient>();
        protected BlockingQueue<string> m_Incoming = new BlockingQueue<string>();
        #endregion Fields

        #region Events
        public event DisconnectDelegate ServerLost = delegate{};
        #endregion Events

        #region Properties
        public string PlayerName { get { return m_PlayerName; } }
        public string ServerAddress { get { return m_ServerAddress; } }
        public int ServerPort { get { return m_ServerPort; } }
        #endregion Properties

        #region Ctors & Init
        public LobbyTCPClient(string serverAddress, int serverPort)
            : base()
        {
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;
        }
        #endregion Ctors & Init

        #region GameClient Event Handler
        protected void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            Send(new GameCommand() { TableID = ((GameTCPClient)sender).NoPort, EncodedCommand = e.Key });
        }

        #endregion GameClient Event Handler

        #region Public Methods
        public bool Connect()
        {
            return base.Connect(m_ServerAddress, m_ServerPort);
        }

        public void LeaveTable(int idGame)
        {
            if (m_Clients.ContainsKey(idGame))
            {
                GameTCPClient client = m_Clients[idGame];

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
            string encode = command.Encode();
            LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Receive", "{0} SENT [{1}]", m_PlayerName, encode);
            base.Send(encode);
        }

        public void Disconnect()
        {
            foreach (GameTCPClient client in m_Clients.Values)
                client.Disconnect();

            m_Clients.Clear();

            if (IsConnected)
            {
                Send(new DisconnectCommand());
                Close();
            }
        }

        public GameTCPClient FindClient(int noPort)
        {
            if (m_Clients.ContainsKey(noPort))
                return m_Clients[noPort];

            return null;
        }

        public GameTCPClient JoinTable(int idTable, string tableName, IPokerViewer gui)
        {
            bool ok = GetJoinedSeat(idTable, m_PlayerName);
            if (!ok)
            {
                LogManager.Log(LogLevel.MessageLow, "LobbyTCPClient.JoinTable", "Cannot join the table: {0}:{1}", tableName, idTable);
                return null;
            }

            GameTCPClient client = new GameTCPClient(m_PlayerName, idTable);
            client.SendedSomething += new EventHandler<KeyEventArgs<string>>(client_SendedSomething);

            if (gui != null)
            {
                gui.SetGame(client, m_PlayerName);
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
            string expected = (string)typeof(T).GetField(AbstractCommand.CommandNameField, (BindingFlags.Static | BindingFlags.FlattenHierarchy | BindingFlags.Public)).GetValue(null);
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
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Receive", "{0} RECV [{1}]", m_PlayerName, line);
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
                TableID = idTable,
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
                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Run", "{0} IS WAITING", m_PlayerName);

                string line = Receive();
                if (line == null)
                {
                    ServerLost();
                    return;
                }

                LogManager.Log(LogLevel.MessageVeryLow, "LobbyTCPClient.Run", "{0} RECV [{1}]", m_PlayerName, line);

                JObject jObj = JsonConvert.DeserializeObject<dynamic>(line);
                String cmdName = (string)jObj["CommandName"];

                if (cmdName == GameCommand.COMMAND_NAME)
                {
                    GameCommand c = JsonConvert.DeserializeObject<GameCommand>(line);
                    int count = 0;

                    //Be patient
                    while (!m_Clients.ContainsKey(c.TableID) && (count++ < 5))
                        Thread.Sleep(100);

                    if (m_Clients.ContainsKey(c.TableID))
                        m_Clients[c.TableID].Incoming(c.DecodedCommand);
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
