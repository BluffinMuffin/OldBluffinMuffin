using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Enums
{
    public enum LimitTypeEnum
    {
        [Description("No Limit")]
        NoLimit,

        [Description("Fixed Limit")]
        FixedLimit,

        [Description("Pot Limit")]
        PotLimit
    }
}
