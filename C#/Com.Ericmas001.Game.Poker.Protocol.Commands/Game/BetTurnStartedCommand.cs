using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class BetTurnStartedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameBET_TURN_STARTED";

        public RoundTypeEnum Round { get; set; }
        public List<int> CardsID { get; set; }
    }
}
