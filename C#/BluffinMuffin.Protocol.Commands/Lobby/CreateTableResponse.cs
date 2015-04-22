namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class CreateTableResponse : AbstractBluffinReponse<CreateTableCommand>
    {
        public int IdTable { get; set; }

        public CreateTableResponse(CreateTableCommand command)
            : base(command)
        {
        }
    }
}
