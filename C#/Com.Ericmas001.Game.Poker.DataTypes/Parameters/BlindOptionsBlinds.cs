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
        public int BigBlindAmount { get { return MoneyUnit; } }
        public int SmallBlindAmount { get { return MoneyUnit / 2; } }

        public BlindOptionsBlinds(int moneyUnit) : base(moneyUnit) { }
    }
}
