using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Networking;
using EricUtility;
using System.IO;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby;
using PokerProtocol.Commands.Lobby.Response;
using System.Net.Sockets;
using PokerWorld.Game;

namespace PokerProtocol
{
    public class LobbyTCPClient : TCPCommunicator
    {
        private string m_PlayerName;
        private string m_ServerAddress;
        private int m_ServerPort;

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
        private List<GameTCPClient> m_Clients = new List<GameTCPClient>();

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

        private StringTokenizer ReceiveCommand(string expected)
        {
            return ReceiveCommand(m_Input, expected);
        }

        private StringTokenizer ReceiveCommand(StreamReader reader, string expected)
        {
            string s = Receive(reader);
            StringTokenizer token = new StringTokenizer(s, AbstractCommand.Delimitter);
            string commandName = token.NextToken();
            while (s != null && commandName != expected)
            {
                s = Receive(reader);
                token = new StringTokenizer(s, AbstractCommand.Delimitter);
                commandName = token.NextToken();
            }
            return token;
        }

        private string Receive(StreamReader reader)
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
        private void Send(StreamWriter writer, AbstractCommand command)
        {
            writer.WriteLine(command.Encode());
        }
        private void Send(AbstractCommand command)
        {
            base.Send(command.Encode());
        }
        public void Disconnect()
        {
            while (m_Clients.Count != 0)
            {
                m_Clients[0].Disconnect();
                m_Clients.RemoveAt(0);
            }
            if (IsConnected)
            {
                Send(new DisconnectCommand());
                Close();
            }
        }
        public bool Identify(string name)
        {
            m_PlayerName = name;
            Send(new IdentifyCommand(m_PlayerName));
            StringTokenizer token = ReceiveCommand(IdentifyResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;
            IdentifyResponse response = new IdentifyResponse(token);
            return response.OK;
        }

        public GameTCPClient FindClient(int noPort)
        {
            for (int i = 0; i < m_Clients.Count; ++i)
                if (m_Clients[i].NoPort == noPort)
                    return m_Clients[i];
            return null;
        }

        public GameTCPClient JoinTable(int p_noPort, string p_tableName, IPokerViewer gui)
        {
            TcpClient tableSocket = null;
            StreamWriter toTable = null;
            StreamReader fromTable = null;
            try
            {
                Console.WriteLine("Trying connection with the table manager...");
                tableSocket = new TcpClient(m_ServerAddress, p_noPort);
                toTable = new StreamWriter(tableSocket.GetStream());
                toTable.AutoFlush = true;

                fromTable = new StreamReader(tableSocket.GetStream());

                Send(toTable, new IdentifyCommand(m_PlayerName));

                StringTokenizer token = ReceiveCommand(fromTable, IdentifyResponse.COMMAND_NAME);
                if (!token.HasMoreTokens())
                    return null;
                IdentifyResponse response = new IdentifyResponse(token);
                if (!response.OK)
                {
                    Console.WriteLine("Authentification failed on the table: " + p_tableName);
                    return null;
                }

                JoinTableCommand command = new JoinTableCommand(m_PlayerName, p_tableName);
                Send(toTable, command);
                StringTokenizer token2 = ReceiveCommand(fromTable, JoinTableResponse.COMMAND_NAME);
                if (!token2.HasMoreTokens())
                    return null;
                JoinTableResponse response2 = new JoinTableResponse(token2);
                int noSeat = response2.NoSeat;

                if (noSeat == -1)
                {
                    Console.WriteLine("Cannot sit at this table: " + p_tableName);
                    return null;
                }

                GameTCPClient client = new GameTCPClient(tableSocket, noSeat, m_PlayerName, p_noPort);
                if (gui != null)
                {
                    gui.SetGame(client, client.NoSeat);
                    gui.Start();
                }
                client.Start();
                m_Clients.Add(client);
                return client;

            }
            catch
            {
                Console.WriteLine(p_noPort + " not open.");
            }

            return null;
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
    }
}
