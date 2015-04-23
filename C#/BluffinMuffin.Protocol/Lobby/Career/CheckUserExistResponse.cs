namespace BluffinMuffin.Protocol.Lobby.Career
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
