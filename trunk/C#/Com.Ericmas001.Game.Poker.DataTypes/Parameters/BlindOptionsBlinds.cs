using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class BlindOptionsBlinds : BlindOptions
    {
        public override BlindTypeEnum BlindType { get { return BlindTypeEnum.Blinds; } }
        public int BigBlindAmount { get; set; }
        public int SmallBlindAmount { get { return BigBlindAmount / 2; } }

        public override int MinimumRaiseAmount { get { return BigBlindAmount; } }
    }
}
