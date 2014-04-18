using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public abstract class LimitOptions : IOption<LimitTypeEnum>
    {
        public abstract LimitTypeEnum OptionType { get; }
    }
}
