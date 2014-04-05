using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game.Rules
{
    public static class BlindFactory
    {
        public static BlindInfo GetInfos(BlindEnum blindType)
        {
            switch(blindType)
            {
                case BlindEnum.Blinds:
                    return new BlindInfo()
                    {
                        Name = "Blinds",
                        Type = blindType,
                        HasConfigurableAmount = true,
                        ConfigurableAmountName = "Big Blind",
                        ConfigurableDefaultValue = 10,
                    };

                case BlindEnum.Antes:
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
