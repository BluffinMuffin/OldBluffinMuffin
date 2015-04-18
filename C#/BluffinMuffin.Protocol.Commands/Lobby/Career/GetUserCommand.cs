namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class GetUserCommand : AbstractLobbyCommand
    {
        public string Username { get; set; }

        public string EncodeResponse(string mail, string display, double money)
        {
            return Response(mail, display, money).Encode();
        }
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
