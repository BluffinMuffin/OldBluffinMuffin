using System;
using System.Collections.Generic;
using System.Text;
using System.Net.Sockets;
using PokerWorld.Game;
using System.Threading;
using PokerProtocol.Commands.Lobby;
using PokerProtocol;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;
using System.Net;
using EricUtility;

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
            m_SocketServer = new TcpListener(IPAddress.Any,port);
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
                    LogManager.Log(LogLevel.Error, "ServerLobby.Run", e.StackTrace);
                }
            }
        }

        public int CreateTrainingTable(CreateTrainingTableCommand c)
        {
            ListTrainingTables();
            ListCareerTables();
            m_LastUsedID++;
            while (m_Games.ContainsKey(m_LastUsedID))
                m_LastUsedID++;
            PokerGameTraining game = new PokerGameTraining(new TableInfoTraining(c.TableName, c.BigBlind, c.MaxPlayers, c.Limit, c.StartingMoney), c.WaitingTimeAfterPlayerAction, c.WaitingTimeAfterBoardDealed, c.WaitingTimeAfterPotWon);
            m_Games.Add(m_LastUsedID, game);
            game.Start();
            return m_LastUsedID;
        }

        public int CreateCareerTable(CreateCareerTableCommand c)
        {
            ListTrainingTables();
            ListCareerTables();
            m_LastUsedID++;
            while (m_Games.ContainsKey(m_LastUsedID))
                m_LastUsedID++;
            PokerGameCareer game = new PokerGameCareer(new TableInfoCareer(c.TableName, c.BigBlind, c.MaxPlayers, c.Limit), c.WaitingTimeAfterPlayerAction, c.WaitingTimeAfterBoardDealed, c.WaitingTimeAfterPotWon);
            m_Games.Add(m_LastUsedID, game);
            game.Start();
            return m_LastUsedID;
        }

        public List<TupleTableInfoTraining> ListTrainingTables()
        {
            List<TupleTableInfoTraining> tables = new List<TupleTableInfoTraining>();
            List<int> tablesToRemove = new List<int>();

            foreach (KeyValuePair<int, PokerGame> kvp in m_Games)
            {
                PokerGame game = kvp.Value;
                int noPort = kvp.Key;
                if (game.IsRunning)
                {
                    TableInfo t = game.Table;
                    if( t is TableInfoTraining )
                        tables.Add(new TupleTableInfoTraining(noPort, t.Name, t.BigBlindAmnt, t.Players.Count, t.NbMaxSeats, t.BetLimit, PossibleActionType.None));
                }
                else
                    tablesToRemove.Add(noPort);
            }
            foreach (int i in tablesToRemove)
                m_Games.Remove(i);
            return tables;
        }

        public List<TupleTableInfoCareer> ListCareerTables()
        {
            List<TupleTableInfoCareer> tables = new List<TupleTableInfoCareer>();
            List<int> tablesToRemove = new List<int>();

            foreach (KeyValuePair<int, PokerGame> kvp in m_Games)
            {
                PokerGame game = kvp.Value;
                int noPort = kvp.Key;
                if (game.IsRunning)
                {
                    TableInfo t = game.Table;
                    if (t is TableInfoCareer)
                        tables.Add(new TupleTableInfoCareer(noPort, t.Name, t.BigBlindAmnt, t.Players.Count, t.NbMaxSeats, t.BetLimit, PossibleActionType.None));
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
