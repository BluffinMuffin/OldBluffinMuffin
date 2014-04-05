using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerPlayMoneyCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAY_MONEY";

        public int Played { get; set; }
    }
}
