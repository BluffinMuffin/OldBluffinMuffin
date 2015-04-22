namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CheckDisplayExistResponse : AbstractBluffinReponse<CheckDisplayExistCommand>
    {
        public bool Exist { get; set; }

        public CheckDisplayExistResponse(CheckDisplayExistCommand command)
            : base(command)
        {
        }
    }
}
