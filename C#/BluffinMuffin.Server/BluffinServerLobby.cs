using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;
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
    public class BluffinServerLobby : IServerLobby, IBluffinServer, IBluffinLobby
    {
        private bool USE_OLD = false;

        private readonly BluffinTcpServer m_TcpServer;
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
            if (USE_OLD)
                m_SocketServer = new TcpListener(IPAddress.Any, port);
            else
            {
                LogManager.Log(LogLevel.Message, "BEST BLUFFIN SERVER EVER","Server started on port {0} !", port);
                LobbyCommands = new BlockingCollection<CommandEntry<AbstractBluffinCommand>>();
                var lobbyWorker = new BluffinLobbyWorker(this,this);
                Task.Factory.StartNew(lobbyWorker.Start);
                m_TcpServer = new BluffinTcpServer(port, this);
            }
        }

        public bool NameUsed(string name)
        {
            return IsNameUsed(name);
        }
        public bool IsNameUsed(string name)
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
            if (USE_OLD)
            {
                m_SocketServer.Start();
                new Thread(Run).Start();
            }
            else
                m_TcpServer.Run().Wait();
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

        public BlockingCollection<CommandEntry<AbstractBluffinCommand>> LobbyCommands { get; private set; }

        public void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var entry = new CommandEntry<AbstractBluffinCommand>() { Client = client, Command = command };
            switch (command.CommandType)
            {
                case BluffinCommandEnum.General:
                    LobbyCommands.Add(entry);
                    break;
                case BluffinCommandEnum.Lobby:
                    LobbyCommands.Add(entry);
                    break;
                case BluffinCommandEnum.Game:
                    break;
            }
        }
    }
}
