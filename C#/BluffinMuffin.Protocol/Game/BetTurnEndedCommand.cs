using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Game
{
    public class BetTurnEndedCommand : AbstractGameCommand
    {
        public RoundTypeEnum Round { get; set; }
        public List<int> PotsAmounts { get; set; }
    }
}
