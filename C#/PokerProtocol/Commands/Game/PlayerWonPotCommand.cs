using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
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
