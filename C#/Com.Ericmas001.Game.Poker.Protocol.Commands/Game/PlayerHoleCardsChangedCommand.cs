using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameHOLE_CARDS_CHANGED";

        public int PlayerPos { get; set; }
        public bool IsPlaying { get; set; }
        public List<int> CardsID { get; set; }
    }
}
