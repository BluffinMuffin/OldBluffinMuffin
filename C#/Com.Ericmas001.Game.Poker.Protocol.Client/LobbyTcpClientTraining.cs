using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public class LobbyTcpClientTraining : LobbyTcpClient
    {
        public LobbyTcpClientTraining(string serverAddress, int serverPort)
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
