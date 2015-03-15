namespace BluffinMuffin.Protocol.Commands.Lobby.Career
{
    public class CheckDisplayExistResponse : AbstractLobbyResponse<CheckDisplayExistCommand>
    {
        public bool Exist { get; set; }

        public CheckDisplayExistResponse()
        {
        }

        public CheckDisplayExistResponse(CheckDisplayExistCommand command)
            : base(command)
        {
        }
    }
}
