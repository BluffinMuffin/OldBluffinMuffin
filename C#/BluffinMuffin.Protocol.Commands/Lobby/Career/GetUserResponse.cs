namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class GetUserResponse : AbstractLobbyResponse<GetUserCommand>
    {
        public string Email { get; set; }
        public string DisplayName { get; set; }
        public double Money { get; set; }

        public GetUserResponse()
        {
        }

        public GetUserResponse(GetUserCommand command)
            : base(command)
        {
        }
    }
}
