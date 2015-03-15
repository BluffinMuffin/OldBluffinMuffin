namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CheckUserExistResponse : AbstractLobbyResponse<CheckUserExistCommand>
    {
        public bool Exist { get; set; }

        public CheckUserExistResponse()
        {
        }

        public CheckUserExistResponse(CheckUserExistCommand command)
            : base(command)
        {
        }
    }
}
