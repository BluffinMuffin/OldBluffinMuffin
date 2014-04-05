using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Rules
{
    public static class BlindFactory
    {
        public static BlindInfo GetInfos(BlindTypeEnum blindType)
        {
            switch(blindType)
            {
                case BlindTypeEnum.Blinds:
                    return new BlindInfo()
                    {
                        Name = "Blinds",
                        Type = blindType,
                        HasConfigurableAmount = true,
                        ConfigurableAmountName = "Big Blind",
                        ConfigurableDefaultValue = 10,
                    };

                case BlindTypeEnum.Antes:
                    return new BlindInfo()
                    {
                        Name = "Antes",
                        Type = blindType,
                        HasConfigurableAmount = true,
                        ConfigurableAmountName = "Ante",
                        ConfigurableDefaultValue = 5,
                    };

                default:
                    return new BlindInfo()
                    {
                        Name = blindType.ToString(),
                        Type = blindType,
                        HasConfigurableAmount = false,
                    };
            }
        }
    }
}
