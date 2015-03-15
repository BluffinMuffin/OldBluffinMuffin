using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Threading;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Server;
using System.Net;
using Com.Ericmas001.Util;
using BluffinMuffin.Poker.DataTypes;
using System.Linq;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.Logic;

namespace BluffinMuffin.Server
{
    public class BluffinServerLobby : IServerLobby
    {
        private readonly TcpListener m_SocketServer;

        private readonly List<string> m_UsedNames = new List<string>();
        private readonly Dictionary<int, PokerGame> m_Games = new Dictionary<int, PokerGame>();

        private int m_LastUsedId;

        public PokerGame GetGame(int id)
        {
            return m_Games[id];
        }

        public BluffinServerLobby(int port)
        {
            m_SocketServer = new TcpListener(IPAddress.Any,port);
        }

        public bool NameUsed(string name)
        {
            foreach (var s in m_UsedNames)
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
            new Thread(Run).Start();
        }

        private void Run()
        {
            while (true)
            {
                try
                {
                    var client = m_SocketServer.AcceptTcpClient();
                    var lobby = new LobbyTcpServer(client, this);
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

            m_LastUsedId++;
            while (m_Games.ContainsKey(m_LastUsedId))
                m_LastUsedId++;

            var game = new PokerGame(new PokerTable(c.Params));

            m_Games.Add(m_LastUsedId, game);
            game.Start();

            return m_LastUsedId;
        }

        public List<TupleTable> ListTables(params LobbyTypeEnum[] lobbyTypes)
        {
            var tables = new List<TupleTable>();

            // Remove non-running tables
            m_Games.Where(kvp => !kvp.Value.IsRunning).Select(kvp => kvp.Key).ToList().ForEach(i => m_Games.Remove(i));

            //List Tables
            foreach (var kvp in m_Games.Where(kvp => kvp.Value.IsRunning))
            {
                var t = kvp.Value.Table;
                if (lobbyTypes.Length == 0 || lobbyTypes.Contains(t.Params.Lobby.OptionType))
                    tables.Add(new TupleTable()
                    {
                        IdTable = kvp.Key,
                        Params = t.Params,
                        NbPlayers = t.Players.Count,
                        PossibleAction = LobbyActionEnum.None,
                    });
            }

            return tables;
        }
    }
}
