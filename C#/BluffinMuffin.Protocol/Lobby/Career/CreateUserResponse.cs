namespace BluffinMuffin.Protocol.Lobby.Career
{
    public class CreateUserResponse : AbstractBluffinReponse<CreateUserCommand>
    {
        public bool Success { get; set; }

        public CreateUserResponse(CreateUserCommand command)
            : base(command)
        {
        }
    }
}
