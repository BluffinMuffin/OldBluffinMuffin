namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class JoinTableCommand : AbstractLobbyCommand
    {
        public int TableId { get; set; }
        public string PlayerName { get; set; }

        public JoinTableResponse Response(bool success)
        {
            return new JoinTableResponse(this) { Success = success };
        }
    }
}
