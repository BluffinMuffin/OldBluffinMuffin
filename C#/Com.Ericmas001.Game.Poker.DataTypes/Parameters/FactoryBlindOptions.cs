using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public static class FactoryBlindOptions
    {
        public static BlindOptions GenerateOptions(BlindTypeEnum type)
        {
            switch (type)
            {
                case BlindTypeEnum.None: return new BlindOptionsNone();
                case BlindTypeEnum.Blinds: return new BlindOptionsBlinds();
                case BlindTypeEnum.Antes: return new BlindOptionsAnte();
            }
            return null;
        }
    }
}
