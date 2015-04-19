using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
        public List<int> CardsId { get; set; }

        public PlayerStateEnum State { get; set; }
    }
}
