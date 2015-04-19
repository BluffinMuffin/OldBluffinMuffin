namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerWonPotCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
        public int PotId { get; set; }
        public int Shared { get; set; }
        public int PlayerMoney { get; set; }
    }
}
