using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerTurnEndedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_TURN_ENDED";

        public int PlayerPos { get; set; }
        public int PlayerBet { get; set; }
        public int PlayerMoney { get; set; }
        public int TotalPot { get; set; }
        public GameActionEnum ActionType { get; set; }
        public int ActionAmount { get; set; }
        public bool IsPlaying { get; set; }
    }
}
