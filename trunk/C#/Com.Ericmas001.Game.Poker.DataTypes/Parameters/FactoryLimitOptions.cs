using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public static class FactoryLimitOptions
    {
        public static LimitOptions GenerateOptions(LimitTypeEnum type)
        {
            switch (type)
            {
                case LimitTypeEnum.NoLimit: return new LimitOptionsNoLimit();
                case LimitTypeEnum.FixedLimit: return new LimitOptionsFixed();
                case LimitTypeEnum.PotLimit: return new LimitOptionsPot();
            }
            return null;
        }
    }
}
