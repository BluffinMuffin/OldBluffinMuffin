namespace BluffinMuffin.Protocol.Game
{
    public class PlayerMoneyChangedCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
        public int PlayerMoney { get; set; }
    }
}
