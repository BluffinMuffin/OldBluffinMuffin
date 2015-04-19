using System.Collections.Concurrent;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinServer
    {
        BlockingCollection<CommandEntry> LobbyCommands { get; }
        BlockingCollection<GameCommandEntry> GameCommands { get; } 
    }
}
