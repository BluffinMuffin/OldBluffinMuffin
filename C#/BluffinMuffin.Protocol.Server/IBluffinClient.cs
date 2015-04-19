using BluffinMuffin.Protocol.Commands;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinClient
    {
        string PlayerName { get; set; }

        void SendCommand(AbstractBluffinCommand command);

        void AddPlayer(RemotePlayer p);
        void RemovePlayer(RemotePlayer p);
    }
}
