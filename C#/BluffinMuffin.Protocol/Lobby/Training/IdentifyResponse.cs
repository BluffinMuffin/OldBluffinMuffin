namespace BluffinMuffin.Protocol.Lobby.Training
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
