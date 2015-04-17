namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class JoinTableResponse : AbstractBluffinReponse<JoinTableCommand>
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
