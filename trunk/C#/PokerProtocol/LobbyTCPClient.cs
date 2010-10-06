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
        public event DisconnectDelegate ServerLost = delegate{};
        protected string m_PlayerName;
        protected string m_ServerAddress;
        protected int m_ServerPort;

        public string PlayerName
        {
            get { return m_PlayerName; }
        }

        public string ServerAddress
        {
            get { return m_ServerAddress; }
        }

        public int ServerPort
        {
            get { return m_ServerPort; }
        }
        protected Dictionary<int, GameClient> m_Clients = new Dictionary<int, GameClient>();
        protected BlockingQueue<string> m_Incoming = new BlockingQueue<string>();

        public LobbyTCPClient(string serverAddress, int serverPort)
            : base()
        {
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;
        }

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
                {
                    client.Disconnect();
                }
            }
        }

        protected StringTokenizer ReceiveCommand(string expected)
        {
            string s = m_Incoming.Dequeue();
            StringTokenizer token = new StringTokenizer(s, AbstractLobbyCommand.Delimitter);
            string commandName = token.NextToken();
            while (s != null && commandName != expected)
            {
                s = m_Incoming.Dequeue();
                token = new StringTokenizer(s, AbstractLobbyCommand.Delimitter);
                commandName = token.NextToken();
            }
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
        public void Send(StreamWriter writer, AbstractCommand command)
        {
            writer.WriteLine(command.Encode());
        }
        public void Send(AbstractCommand command)
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
                Send(new DisconnectCommand());
                Close();
            }
        }
        public GameClient FindClient(int noPort)
        {
            if (m_Clients.ContainsKey(noPort))
                return m_Clients[noPort];
            return null;
        }

        protected virtual int GetJoinedSeat(int p_noPort, string player)
        {
            JoinTableCommand command = new JoinTableCommand(p_noPort, player);
            Send(command);

            StringTokenizer token2 = ReceiveCommand(JoinTableResponse.COMMAND_NAME);
            if (!token2.HasMoreTokens())
                return -1;
            JoinTableResponse response2 = new JoinTableResponse(token2);
            return response2.NoSeat;
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

        protected void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            GameClient client = (GameClient)sender;
            Send(new GameCommand(client.NoPort, e.Key));
        }

        public List<TupleTableInfoTraining> getListTrainingTables()
        {
            Send(new ListTableCommand(true));

            StringTokenizer token = ReceiveCommand(ListTableTrainingResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                ListTableTrainingResponse response = new ListTableTrainingResponse(token);
                return response.Tables;
            }
            else
                return new List<TupleTableInfoTraining>();
        }

        public List<TupleTableInfoCareer> getListCareerTables()
        {
            Send(new ListTableCommand(false));

            StringTokenizer token = ReceiveCommand(ListTableCareerResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                ListTableCareerResponse response = new ListTableCareerResponse(token);
                return response.Tables;
            }
            else
                return new List<TupleTableInfoCareer>();
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
                String commandName = token.NextToken();
                if (commandName == GameCommand.COMMAND_NAME)
                {
                    GameCommand c = new GameCommand(token);
                    int count = 0;
                    while (!m_Clients.ContainsKey(c.TableID) && (count++ < 5))
                        Thread.Sleep(100);
                    if (m_Clients.ContainsKey(c.TableID))
                        m_Clients[c.TableID].Incoming(c.Command);
                }
                else
                    m_Incoming.Enqueue(line);
            }
        }


        public int CreateCareerTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit)
        {
            Send(new CreateCareerTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit));

            StringTokenizer token = ReceiveCommand(CreateCareerTableResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                CreateCareerTableResponse response = new CreateCareerTableResponse(token);
                return response.Port;
            }
            else
                return -1;
        }


        public int CreateTrainingTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit, int startingMoney)
        {
            Send(new CreateTrainingTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit, startingMoney));

            StringTokenizer token = ReceiveCommand(CreateTrainingTableResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                CreateTrainingTableResponse response = new CreateTrainingTableResponse(token);
                return response.Port;
            }
            else
                return -1;
        }
    }
}
