namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class GetUserCommand : AbstractLobbyCommand
    {
        public string Username { get; set; }

        public string EncodeResponse(string mail, string display, double money)
        {
            return new GetUserResponse(this)
            {
                Email = mail,
                DisplayName = display,
                Money = money,
            }.Encode();
        }
    }
}
