namespace BluffinMuffin.Protocol.Game
{
    public class PlayerSitInResponse : AbstractBluffinReponse<PlayerSitInCommand>, IGameCommand
    {
        public int NoSeat { get; set; }

        public PlayerSitInResponse(PlayerSitInCommand command)
            : base(command)
        {
        }

        public int TableId 
        {
            get { return Command.TableId; }
        }
    }
}
