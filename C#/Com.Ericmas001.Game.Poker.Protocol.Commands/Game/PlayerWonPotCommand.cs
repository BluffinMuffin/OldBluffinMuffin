using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerWonPotCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_WON_POT";

        public int PlayerPos { get; set; }
        public int PotID { get; set; }
        public int Shared { get; set; }
        public int PlayerMoney { get; set; }
    }
}
