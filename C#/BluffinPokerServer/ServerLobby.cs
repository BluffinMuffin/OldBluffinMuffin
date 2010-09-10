using System;
using System.Collections.Generic;
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

        private int m_LastUsedID = 0;

        public PokerGame GetGame(int id)
        {
            return m_Games[id];
        }

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
            m_LastUsedID++;
            while (m_Games.ContainsKey(m_LastUsedID))
                m_LastUsedID++;
            PokerGame game = new PokerGame(new TableInfo(c.TableName, c.BigBlind, c.MaxPlayers, c.Limit), c.WaitingTimeAfterPlayerAction, c.WaitingTimeAfterBoardDealed, c.WaitingTimeAfterPotWon);
            m_Games.Add(m_LastUsedID, game);
            game.Start();
            return m_LastUsedID;
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
