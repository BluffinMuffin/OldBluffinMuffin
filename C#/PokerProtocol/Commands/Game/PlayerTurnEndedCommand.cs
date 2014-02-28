using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Game
{
    public class PlayerTurnEndedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_TURN_ENDED";

        public int PlayerPos { get; set; }
        public int PlayerBet { get; set; }
        public int PlayerMoney { get; set; }
        public int TotalPot { get; set; }
        public GameActionEnum ActionType { get; set; }
        public int ActionAmount { get; set; }
        public bool IsPlaying { get; set; }

        public PlayerTurnEndedCommand()
        {
        }

        public PlayerTurnEndedCommand(int pos, int bet, int money, int totalPot, GameActionEnum actionType, int actionAmount, bool isPlaying)
        {
            PlayerPos = pos;
            PlayerBet = bet;
            PlayerMoney = money;
            TotalPot = totalPot;
            ActionType = actionType;
            ActionAmount = actionAmount;
            IsPlaying = isPlaying;
        }
    }
}
