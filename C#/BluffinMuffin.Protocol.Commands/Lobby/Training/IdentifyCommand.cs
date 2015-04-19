namespace BluffinMuffin.Protocol.Commands.Lobby.Training
{
    public class IdentifyCommand : AbstractLobbyCommand
    {
        public string Name { get; set; }

        public IdentifyResponse Response(bool success)
        {
            return new IdentifyResponse(this) { Ok = success };
        }
    }
}
