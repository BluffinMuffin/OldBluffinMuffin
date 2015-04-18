using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Lobby;

namespace BluffinMuffin.Protocol.Server.Test.Mocking
{
    public class ServerMock : IBluffinServer, IBluffinLobby
    {
        private ClientMock m_Client;
        private BluffinLobbyWorker m_Worker;
        public ServerMock()
        {
            LobbyCommands = new BlockingCollection<CommandEntry<AbstractBluffinCommand>>();
            ServerSendedCommands = new BlockingCollection<CommandEntry<AbstractBluffinCommand>>();
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
            LobbyCommands.Add(new CommandEntry<AbstractBluffinCommand>(){Client = m_Client,Command = c});
        }

        public BlockingCollection<CommandEntry<AbstractBluffinCommand>> LobbyCommands { get; private set; }
        public BlockingCollection<CommandEntry<AbstractBluffinCommand>> ServerSendedCommands { get; private set; }
        public void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
        }

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
