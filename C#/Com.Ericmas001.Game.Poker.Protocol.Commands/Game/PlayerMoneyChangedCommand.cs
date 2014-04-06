using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerMoneyChangedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_MONEY_CHANGED";

        public int PlayerPos { get; set; }
        public int PlayerMoney { get; set; }
    }
}
