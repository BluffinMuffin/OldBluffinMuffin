namespace BluffinMuffin.Protocol.Lobby.Career
{
    public class AuthenticateUserResponse : AbstractBluffinReponse<AuthenticateUserCommand>
    {
        public bool Success { get; set; }

        public AuthenticateUserResponse(AuthenticateUserCommand command)
            : base(command)
        {
        }
    }
}
