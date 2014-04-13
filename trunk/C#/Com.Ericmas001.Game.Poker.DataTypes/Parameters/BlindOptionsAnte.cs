using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class BlindOptionsAnte : BlindOptions
    {
        public override BlindTypeEnum BlindType { get { return BlindTypeEnum.Antes; } }
        public int AnteAmount { get { return MoneyUnit; } }

        public BlindOptionsAnte(int moneyUnit) : base(moneyUnit) { }
    }
}
