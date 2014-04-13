using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class LimitOptionsNoLimit : LimitOptions
    {
        public override LimitTypeEnum LimitType { get { return LimitTypeEnum.NoLimit; } }
    }
}
