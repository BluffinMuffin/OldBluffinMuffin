namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class CreateTableResponse : AbstractBluffinReponse<CreateTableCommand>
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
