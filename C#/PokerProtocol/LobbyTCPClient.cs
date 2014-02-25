using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using EricUtility;
using EricUtility.Collections;
using EricUtility.Networking;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby;
using PokerWorld.Game;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;

namespace PokerProtocol
{
    public delegate void DisconnectDelegate();
    public class LobbyTCPClient : TCPCommunicator
    {
        #region Fields
        protected string m_PlayerName;
        protected string m_ServerAddress;
        protected int m_ServerPort;
        protected Dictionary<int, GameClient> m_Clients = new Dictionary<int, GameClient>();
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
            Send(new GameCommand(((GameClient)sender).NoPort, e.Key));
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
                GameClient client = m_Clients[idGame];

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

        public void Send(StreamWriter writer, AbstractTextCommand command)
        {
            writer.WriteLine(command.Encode());
        }

        public void Send(AbstractTextCommand command)
        {
            base.Send(command.Encode());
        }

        public void Disconnect()
        {
            foreach (GameClient client in m_Clients.Values)
                client.Disconnect();

            m_Clients.Clear();

            if (IsConnected)
            {
                Send(new DisconnectTextCommand());
                Close();
            }
        }

        public GameClient FindClient(int noPort)
        {
            if (m_Clients.ContainsKey(noPort))
                return m_Clients[noPort];

            return null;
        }

        public GameClient JoinTable(int p_noPort, string p_tableName, IPokerViewer gui)
        {
            int noSeat = GetJoinedSeat(p_noPort, m_PlayerName);
            if (noSeat == -1)
            {
                LogManager.Log(LogLevel.MessageLow, "LobbyTCPClient.JoinTable", "Cannot sit at this table: {0}", p_tableName);
                return null;
            }

            GameClient client = new GameClient(noSeat, m_PlayerName, p_noPort);
            client.SendedSomething += new EventHandler<KeyEventArgs<string>>(client_SendedSomething);

            if (gui != null)
            {
                gui.SetGame(client, client.NoSeat);
                gui.Start();
            }

            client.Start();

            m_Clients.Add(p_noPort, client);

            return client;
        }

        #endregion Public Methods

        #region Protected Methods
        protected StringTokenizer WaitAndReceive(string expected)
        {
            string s;
            StringTokenizer token;
            string commandName;

            do
            {
                s = m_Incoming.Dequeue();
                token = new StringTokenizer(s, AbstractLobbyCommand.Delimitter);
                commandName = token.NextToken();
            } 
            while (s != null && commandName != expected);

            return token;
        }

        protected string Receive(StreamReader reader)
        {
            string line;
            try
            {
                line = reader.ReadLine();
                LogManager.Log(LogLevel.MessageLow, "LobbyTCPClient.Receive", "{0} RECV [{1}]", m_PlayerName, line);
            }
            catch
            {
                return null;
            }
            return line;
        }

        protected virtual int GetJoinedSeat(int p_noPort, string player)
        {
            JoinTableCommand cmd = new JoinTableCommand(p_noPort, player);
            Send(cmd);

            StringTokenizer token = WaitAndReceive(JoinTableResponse.COMMAND_NAME);

            if (!token.HasMoreTokens())
                return -1;

            return new JoinTableResponse(token).NoSeat;
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

                LogManager.Log(LogLevel.MessageLow, "LobbyTCPClient.Run", "{0} RECV [{1}]", m_PlayerName, line);

                StringTokenizer token = new StringTokenizer(line, AbstractLobbyCommand.Delimitter);
                String cmdName = token.NextToken();

                if (cmdName == GameCommand.COMMAND_NAME)
                {
                    GameCommand c = new GameCommand(token);
                    int count = 0;

                    //Be patient
                    while (!m_Clients.ContainsKey(c.TableID) && (count++ < 5))
                        Thread.Sleep(100);

                    if (m_Clients.ContainsKey(c.TableID))
                        m_Clients[c.TableID].Incoming(c.Command);
                }
                else
                    m_Incoming.Enqueue(line);
            }
        }

        #endregion Protected Methods
    }
}
