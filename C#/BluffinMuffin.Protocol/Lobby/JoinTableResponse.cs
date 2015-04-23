namespace BluffinMuffin.Protocol.Lobby
{
    public class JoinTableResponse : AbstractBluffinReponse<JoinTableCommand>
    {
        public bool Success { get; set; }

        public JoinTableResponse(JoinTableCommand command)
            : base(command)
        {
        }
    }
}
