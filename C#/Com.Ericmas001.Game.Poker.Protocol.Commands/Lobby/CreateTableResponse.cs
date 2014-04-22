namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class CreateTableResponse : AbstractLobbyResponse<CreateTableCommand>
    {
        public int IdTable { get; set; }

        public CreateTableResponse()
        {
        }

        public CreateTableResponse(CreateTableCommand command)
            : base(command)
        {
        }
    }
}
