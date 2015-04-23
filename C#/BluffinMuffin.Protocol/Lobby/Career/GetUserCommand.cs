namespace BluffinMuffin.Protocol.Lobby.Career
{
    public class GetUserCommand : AbstractLobbyCommand
    {
        public string Username { get; set; }

        public GetUserResponse Response(string mail, string display, double money)
        {
            return new GetUserResponse(this)
            {
                Email = mail,
                DisplayName = display,
                Money = money,
            };
        }
    }
}
