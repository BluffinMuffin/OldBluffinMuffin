namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CreateUserCommand : AbstractLobbyCommand
    {
        public string Username { get;  set; }
        public string Password { get;  set; }
        public string Email { get;  set; }
        public string DisplayName { get;  set; }

        public string EncodeResponse(bool success)
        {
            return Response(success).Encode();
        }
        public CreateUserResponse Response(bool success)
        {
            return new CreateUserResponse(this) { Success = success };
        }
    }
}
