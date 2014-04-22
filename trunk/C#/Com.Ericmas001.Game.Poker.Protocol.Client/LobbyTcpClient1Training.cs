using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public class LobbyTcpClient1Training : LobbyTcpClient1
    {
        public LobbyTcpClient1Training(string serverAddress, int serverPort)
            : base(serverAddress, serverPort)
        {
        }

        public bool Identify(string name)
        {
            PlayerName = name;

            Send(new IdentifyCommand() { Name = PlayerName });

            return WaitAndReceive<IdentifyResponse>().Ok;
        }
    }
}
