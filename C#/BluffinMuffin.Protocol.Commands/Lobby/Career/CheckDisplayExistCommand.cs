namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CheckDisplayExistCommand : AbstractLobbyCommand
    {
        public string DisplayName { get; set; }

        public string EncodeResponse(bool exist)
        {
            return new CheckDisplayExistResponse(this) { Exist = exist }.Encode();
        }
    }
}
