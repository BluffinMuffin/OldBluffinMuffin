using System.Collections.Concurrent;

namespace BluffinMuffin.Protocol.Server.DataTypes
{
    public interface IBluffinServer
    {
        BlockingCollection<CommandEntry> LobbyCommands { get; }
        BlockingCollection<GameCommandEntry> GameCommands { get; } 
    }
}
