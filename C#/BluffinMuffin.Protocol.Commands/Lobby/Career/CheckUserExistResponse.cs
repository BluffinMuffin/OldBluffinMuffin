namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CheckUserExistResponse : AbstractBluffinReponse<CheckUserExistCommand>
    {
        public bool Exist { get; set; }

        public CheckUserExistResponse(CheckUserExistCommand command)
            : base(command)
        {
        }
    }
}
