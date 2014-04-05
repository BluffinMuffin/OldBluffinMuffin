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

        public BetTurnStartedCommand()
        {
        }

        public BetTurnStartedCommand(RoundTypeEnum round,params int[] cardsID)
        {
            CardsID = new List<int>(cardsID);
            Round = round;
        }

        public BetTurnStartedCommand(RoundTypeEnum round, List<int> cardsID)
        {
            CardsID = cardsID;
            Round = round;
        }
    }
}
