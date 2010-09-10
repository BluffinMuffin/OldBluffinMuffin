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

namespace PokerProtocol
{
    public class LobbyTCPClient : TCPCommunicator
    {
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
                Console.WriteLine(m_PlayerName + " RECV [" + line + "]");
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
                Console.WriteLine("Lobby lost connection with server");
                Disconnect();
            }
            else
                base.OnReceiveCrashed(e);
        }
        public override void OnSendCrashed(Exception e)
        {
            if (e is IOException)
            {
                Console.WriteLine("Lobby lost connection with server");
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
            foreach(GameClient client in m_Clients.Values)
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

        public GameClient JoinTable(int p_noPort, string p_tableName, IPokerViewer gui)
        {
            JoinTableCommand command = new JoinTableCommand(p_noPort, m_PlayerName);
            Send(command);
            
                StringTokenizer token2 = ReceiveCommand(JoinTableResponse.COMMAND_NAME);
                if (!token2.HasMoreTokens())
                    return null;
                JoinTableResponse response2 = new JoinTableResponse(token2);
                int noSeat = response2.NoSeat;

                if (noSeat == -1)
                {
                    Console.WriteLine("Cannot sit at this table: " + p_tableName);
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
                m_Clients.Add(p_noPort,client);
                return client;
        }

        protected void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            GameClient client = (GameClient)sender;
            Send(new GameCommand(client.NoPort, e.Key));
        }

        public int CreateTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit)
        {
            Send(new CreateTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit));

            StringTokenizer token = ReceiveCommand(CreateTableResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                CreateTableResponse response = new CreateTableResponse(token);
                return response.Port;
            }
            else
                return -1;
        }

        public List<TupleTableInfo> getListTables()
        {
            Send(new ListTableCommand());

            StringTokenizer token = ReceiveCommand(ListTableResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                ListTableResponse response = new ListTableResponse(token);
                return response.Tables;
            }
            else 
                return new List<TupleTableInfo>();
        }
        protected override void Run()
        {
            while (IsConnected)
            {
                Console.WriteLine(m_PlayerName + " IS WAITING");
                string line = Receive();
                if (line == null)
                    return;
                Console.WriteLine(m_PlayerName + " RECV [" + line + "]");
                StringTokenizer token = new StringTokenizer(line, AbstractLobbyCommand.Delimitter);
                String commandName = token.NextToken();
                if (commandName == GameCommand.COMMAND_NAME)
                {
                    GameCommand c = new GameCommand(token);
                    while (!m_Clients.ContainsKey(c.TableID))
                        Thread.Sleep(100);
                    m_Clients[c.TableID].Incoming(c.Command);
                }
                else
                    m_Incoming.Enqueue(line);
            }
        }
    }
}
