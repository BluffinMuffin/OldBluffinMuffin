namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class JoinTableResponse : AbstractLobbyResponse<JoinTableCommand>
    {
        public bool Success { get; set; }

        public JoinTableResponse()
        {
        }

        public JoinTableResponse(JoinTableCommand command)
            : base(command)
        {
        }
    }
}
