using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server.Test.Mocking
{
    class ServerMock : IBluffinServer 
    {
        public BlockingCollection<CommandEntry<AbstractBluffinCommand>> LobbyCommands { get; private set; }
        public void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            throw new NotImplementedException();
        }
    }
}
