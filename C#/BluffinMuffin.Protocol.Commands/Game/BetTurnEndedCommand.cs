using System.Collections.Generic;
using Com.Ericmas001.Net.Protocol.JSON;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class BetTurnEndedCommand : AbstractGameCommand
    {
        public RoundTypeEnum Round { get; set; }
        public List<int> PotsAmounts { get; set; }
    }
}
