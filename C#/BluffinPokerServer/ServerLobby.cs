using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using PokerWorld.Game;
using System.Threading;
using PokerProtocol.Commands.Lobby;
using PokerProtocol;

namespace BluffinPokerServer
{
    public class ServerLobby
    {
        private readonly int m_NoPort;
        private readonly TcpListener m_SocketServer;

        private readonly List<string> m_UsedNames = new List<string>();
        private readonly Dictionary<int, PokerGame> m_Games = new Dictionary<int, PokerGame>();

        public ServerLobby(int port)
        {
            m_NoPort = port;
            m_SocketServer = new TcpListener(port);
        }

        public bool NameUsed(string name)
        {
            foreach (string s in m_UsedNames)
                if (s.Equals(name, StringComparison.InvariantCultureIgnoreCase))
                    return true;
            return false;
        }

        public void AddName(string name)
        {
            m_UsedNames.Add(name);
        }

        public void RemoveName(string name)
        {
            m_UsedNames.Remove(name);
        }

        public void Start()
        {
            m_SocketServer.Start();
            new Thread(new ThreadStart(Run)).Start();
        }

        private void Run()
        {
            while (true)
            {
                try
                {
                    TcpClient client = m_SocketServer.AcceptTcpClient();
                    ServerClientLobby lobby = new ServerClientLobby(client, this);
                    lobby.Start();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        public int CreateTable(CreateTableCommand c)
        {
            ListTables();
            if (m_Games.Count >= 10)
                return -1;
        int noPort = m_NoPort + 1;
        int endPortRange = m_NoPort + 10;
        for (; noPort <= endPortRange; ++noPort)
        {
            try
            {
                for (; noPort < endPortRange && m_Games.ContainsKey(noPort); ++noPort) ;

                PokerGame game = new PokerGame(new TableInfo(c.TableName, c.BigBlind, c.MaxPlayers, c.Limit), c.WaitingTimeAfterPlayerAction, c.WaitingTimeAfterBoardDealed, c.WaitingTimeAfterPotWon);
                game.Start();
                ServerTableManager manager = new ServerTableManager(game,noPort);
                manager.Start();
                m_Games.Add(noPort, game);

                return noPort;
            }
            catch
            {
            }
        }

            return -1;
        }

        public List<TupleTableInfo> ListTables()
        {
            List<TupleTableInfo> tables = new List<TupleTableInfo>();
            List<int> tablesToRemove = new List<int>();

            foreach( KeyValuePair<int,PokerGame> kvp in m_Games )
            {
                PokerGame game = kvp.Value;
                int noPort = kvp.Key;
                if (game.IsRunning)
                {
                    TableInfo t = game.Table;
                    tables.Add(new TupleTableInfo(noPort, t.Name, t.BigBlindAmnt, t.Players.Count, t.NbMaxSeats, t.BetLimit));
                }
                else
                    tablesToRemove.Add(noPort);
            }
            foreach (int i in tablesToRemove)
                m_Games.Remove(i);
            return tables;
        }
    }
}
