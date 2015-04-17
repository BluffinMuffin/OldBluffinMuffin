namespace BluffinMuffin.Protocol.Commands.Lobby.Training
{
    public class IdentifyResponse : AbstractBluffinReponse<IdentifyCommand>
    {
        public bool Ok { get; set; }

        public IdentifyResponse(IdentifyCommand command)
            : base(command)
        {
        }
    }
}
