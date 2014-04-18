using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class LimitOptionsFixed : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.FixedLimit; } }
    }
}
