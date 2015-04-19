namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerSitOutResponse : AbstractBluffinReponse<PlayerSitOutCommand>,IGameCommand
    {
        public bool Success { get; set; }

        public PlayerSitOutResponse(PlayerSitOutCommand command)
            : base(command)
        {
        }

        public int TableId
        {
            get { return Command.TableId; }
        }
    }
}
