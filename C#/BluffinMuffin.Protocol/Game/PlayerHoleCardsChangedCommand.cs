using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Game
{
    public class PlayerHoleCardsChangedCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
        public List<int> CardsId { get; set; }

        public PlayerStateEnum State { get; set; }
    }
}
