using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerMoneyChangedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_MONEY_CHANGED";

        public int PlayerPos { get; set; }
        public int PlayerMoney { get; set; }
    }
}
