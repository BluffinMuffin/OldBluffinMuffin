namespace BluffinMuffin.Protocol.Lobby.Career
{
    public class CheckDisplayExistCommand : AbstractLobbyCommand
    {
        public string DisplayName { get; set; }

        public CheckDisplayExistResponse Response(bool exist)
        {
            return new CheckDisplayExistResponse(this) { Exist = exist };
        }
    }
}
