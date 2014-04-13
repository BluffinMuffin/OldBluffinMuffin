using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public abstract class BlindOptions
    {
        protected int MoneyUnit { get; private set; }
        public abstract BlindTypeEnum BlindType { get; }

        public BlindOptions( int moneyUnit )
        {
            MoneyUnit = moneyUnit;
        }

        
    }
}
