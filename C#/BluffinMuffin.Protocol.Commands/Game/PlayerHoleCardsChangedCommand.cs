using System.Collections.Generic;
using Com.Ericmas001.Net.Protocol.JSON;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractJsonCommand
    {
        public int PlayerPos { get; set; }
        public List<int> CardsId { get; set; }

        public PlayerStateEnum State { get; set; }
    }
}
