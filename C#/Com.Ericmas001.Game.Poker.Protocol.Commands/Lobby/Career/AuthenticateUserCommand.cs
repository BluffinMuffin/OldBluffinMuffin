namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class AuthenticateUserCommand : AbstractLobbyCommand
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public string EncodeResponse(bool success)
        {
            return new AuthenticateUserResponse(this) { Success = success }.Encode();
        }
    }
}
