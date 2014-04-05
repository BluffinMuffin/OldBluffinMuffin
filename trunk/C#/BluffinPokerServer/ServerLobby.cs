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
using PokerProtocol.Entities;
using PokerProtocol.Entities.Enums;
using System.Linq;
using PokerWorld.Game.Rules;
using PokerWorld.Game.Enums;

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
        public int CreateTable(CreateTableCommand c)
        {
            ListTables();

            m_LastUsedID++;
            while (m_Games.ContainsKey(m_LastUsedID))
                m_LastUsedID++;

            PokerGame game = new PokerGame(new TableInfo(c.GameRules));

            m_Games.Add(m_LastUsedID, game);
            game.Start();

            return m_LastUsedID;
        }

        public List<Table> ListTables(params LobbyTypeEnum[] lobbyTypes)
        {
            List<Table> tables = new List<Table>();

            // Remove non-running tables
            m_Games.Where(kvp => !kvp.Value.IsRunning).Select(kvp => kvp.Key).ToList().ForEach(i => m_Games.Remove(i));

            //List Tables
            foreach (KeyValuePair<int, PokerGame> kvp in m_Games.Where(kvp => kvp.Value.IsRunning))
            {
                TableInfo t = kvp.Value.Table;
                if (lobbyTypes.Length == 0 || lobbyTypes.Contains(t.Rules.CurrentLobby.LobbyType))
                    tables.Add(new Table(kvp.Key, t.Rules, t.Players.Count, LobbyActionEnum.None));
            }

            return tables;
        }
    }
}
