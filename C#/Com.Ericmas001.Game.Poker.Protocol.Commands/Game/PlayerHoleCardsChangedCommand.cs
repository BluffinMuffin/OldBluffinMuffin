using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameHOLE_CARDS_CHANGED";

        public int PlayerPos { get; set; }
        public List<int> CardsID { get; set; }

        public PlayerStateEnum State { get; set; }
    }
}
