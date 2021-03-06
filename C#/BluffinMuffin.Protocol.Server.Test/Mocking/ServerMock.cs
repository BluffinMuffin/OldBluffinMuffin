﻿using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading.Tasks;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;
using BluffinMuffin.Protocol.Lobby;
using BluffinMuffin.Protocol.Server.DataTypes;
using BluffinMuffin.Protocol.Server.Workers;

namespace BluffinMuffin.Protocol.Server.Test.Mocking
{
    public class ServerMock : IBluffinServer, IBluffinLobby
    {
        private ClientMock m_Client;
        private BluffinLobbyWorker m_Worker;
        public ServerMock()
        {
            LobbyCommands = new BlockingCollection<CommandEntry>();
            ServerSendedCommands = new BlockingCollection<CommandEntry>();
            m_Client = new ClientMock(this);
            m_Worker = new BluffinLobbyWorker(this, this);
            Task.Factory.StartNew(StartWorker);
        }

        public void StartWorker()
        {
            try
            {
                m_Worker.Start();
            }
            finally
            {
                ServerSendedCommands.CompleteAdding();
            }
        }

        public void Send(AbstractBluffinCommand c)
        {
            LobbyCommands.Add(new CommandEntry(){Client = m_Client,Command = c});
        }

        public BlockingCollection<CommandEntry> LobbyCommands { get; private set; }
        public BlockingCollection<GameCommandEntry> GameCommands { get; private set; }
        public BlockingCollection<CommandEntry> ServerSendedCommands { get; private set; }

        public bool IsNameUsed(string name)
        {
            return true;
        }

        public void AddName(string name)
        {
            
        }

        public void RemoveName(string name)
        {
        }

        public PokerGame GetGame(int id)
        {
            return new PokerGame(new PokerTable());
        }

        public List<TupleTable> ListTables(params LobbyTypeEnum[] lobbyTypes)
        {
            return new List<TupleTable>();
        }

        public int CreateTable(CreateTableCommand c)
        {
            return 42;
        }
    }
}
