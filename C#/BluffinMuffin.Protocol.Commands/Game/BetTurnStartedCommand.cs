using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class BetTurnStartedCommand : AbstractGameCommand
    {
        public RoundTypeEnum Round { get; set; }
        public List<int> CardsId { get; set; }
    }
}
