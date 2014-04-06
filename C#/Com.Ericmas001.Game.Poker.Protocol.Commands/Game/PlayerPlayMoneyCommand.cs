using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerPlayMoneyCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAY_MONEY";

        public int Played { get; set; }
    }
}
