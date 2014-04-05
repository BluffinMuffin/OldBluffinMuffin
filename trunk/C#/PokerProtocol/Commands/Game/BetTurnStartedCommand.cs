using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Game
{
    public class BetTurnStartedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameBET_TURN_STARTED";

        public RoundTypeEnum Round { get; set; }
        public List<int> CardsID { get; set; }
    }
}
