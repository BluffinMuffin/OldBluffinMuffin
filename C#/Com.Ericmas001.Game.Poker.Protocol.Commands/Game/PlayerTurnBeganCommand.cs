using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerTurnBeganCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_TURN_BEGAN";

        public int PlayerPos { get; set; }
        public int LastPlayerNoSeat { get; set; }
        public int MinimumRaise { get; set; }
    }
}
