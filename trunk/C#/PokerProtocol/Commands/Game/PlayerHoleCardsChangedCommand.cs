using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameHOLE_CARDS_CHANGED";

        public int PlayerPos { get; set; }
        public bool IsPlaying { get; set; }
        public List<int> CardsID { get; set; }

        public PlayerHoleCardsChangedCommand()
        {
        }

        public PlayerHoleCardsChangedCommand(int pos, bool playing, params int[] cardsID)
        {
            CardsID = new List<int>(cardsID);
            PlayerPos = pos;
            IsPlaying = playing;
        }

        public PlayerHoleCardsChangedCommand(int pos, bool playing, List<int> cardsID)
        {
            CardsID = cardsID;
            PlayerPos = pos;
            IsPlaying = playing;
        }
    }
}
