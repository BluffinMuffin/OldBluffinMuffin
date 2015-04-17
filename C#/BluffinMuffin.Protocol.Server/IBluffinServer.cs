using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinServer
    {
        BlockingCollection<CommandEntry<AbstractBluffinCommand>> LobbyCommands { get; } 
        void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client);
    }
}
