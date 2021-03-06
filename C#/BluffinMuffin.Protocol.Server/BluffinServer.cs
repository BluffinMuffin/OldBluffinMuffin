﻿using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;
using BluffinMuffin.Protocol.Lobby;
using BluffinMuffin.Protocol.Server.DataTypes;
using BluffinMuffin.Protocol.Server.Workers;
using Com.Ericmas001.Util;
using System.Linq;
using BluffinMuffin.Poker.Logic;

namespace BluffinMuffin.Protocol.Server
{
    public class BluffinServer : IBluffinServer, IBluffinLobby
    {
        public BlockingCollection<CommandEntry> LobbyCommands { get; private set; }
        public BlockingCollection<GameCommandEntry> GameCommands { get; private set; }

        private readonly LocalTcpServer m_TcpServer;

        private readonly List<string> m_UsedNames = new List<string>();
        private readonly Dictionary<int, PokerGame> m_Games = new Dictionary<int, PokerGame>();

        private int m_LastUsedId;

        public PokerGame GetGame(int id)
        {
            return m_Games[id];
        }

        public BluffinServer(int port)
        {
            LogManager.Log(LogLevel.Message, "BluffinServerLobby", "Server started on port {0} !", port);
            LobbyCommands = new BlockingCollection<CommandEntry>();
            GameCommands = new BlockingCollection<GameCommandEntry>();
            Task.Factory.StartNew(new BluffinLobbyWorker(this, this).Start);
            Task.Factory.StartNew(new BluffinGameWorker(this).Start);
            m_TcpServer = new LocalTcpServer(port, this);
        }

        public bool IsNameUsed(string name)
        {
            return m_UsedNames.Any(s => s.Equals(name, StringComparison.InvariantCultureIgnoreCase));
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
            m_TcpServer.Run().Wait();
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
            // Remove non-running tables
            m_Games.Where(kvp => !kvp.Value.IsRunning).Select(kvp => kvp.Key).ToList().ForEach(i => m_Games.Remove(i));

            //List Tables
            return (from kvp in m_Games.Where(kvp => kvp.Value.IsRunning)
                let t = kvp.Value.Table
                where lobbyTypes.Length == 0 || lobbyTypes.Contains(t.Params.Lobby.OptionType)
                select new TupleTable()
                {
                    IdTable = kvp.Key, 
                    Params = t.Params, 
                    NbPlayers = t.Players.Count, 
                    PossibleAction = LobbyActionEnum.None,
                }).ToList();
        }
    }
}
