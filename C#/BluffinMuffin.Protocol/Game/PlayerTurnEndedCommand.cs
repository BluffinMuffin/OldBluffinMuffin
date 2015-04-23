using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Game
{
    public class PlayerTurnEndedCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
        public int PlayerBet { get; set; }
        public int PlayerMoney { get; set; }
        public int TotalPot { get; set; }
        public GameActionEnum ActionType { get; set; }
        public int ActionAmount { get; set; }
        public PlayerStateEnum State { get; set; }
    }
}
