namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class JoinTableCommand : AbstractLobbyCommand
    {
        public int TableId { get; set; }
        public string PlayerName { get; set; }

        public string EncodeResponse(bool success)
        {
            return new JoinTableResponse(this) { Success = success }.Encode();
        }
    }
}
