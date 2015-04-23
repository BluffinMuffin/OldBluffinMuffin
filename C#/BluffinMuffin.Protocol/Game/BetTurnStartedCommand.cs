using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Game
{
    public class BetTurnStartedCommand : AbstractGameCommand
    {
        public RoundTypeEnum Round { get; set; }
        public List<int> CardsId { get; set; }
    }
}
