using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game.Rules
{
    public static class LimitFactory
    {
        public static LimitInfo GetInfos(LimitTypeEnum limitType)
        {
            switch (limitType)
            {
                case LimitTypeEnum.NoLimit:
                    return new LimitInfo()
                    {
                        Name = "No Limit",
                        Type = limitType,
                    };

                case LimitTypeEnum.BetLimit:
                    return new LimitInfo()
                    {
                        Name = "Bet Limit",
                        Type = limitType,
                    };

                case LimitTypeEnum.PotLimit:
                    return new LimitInfo()
                    {
                        Name = "Pot Limit",
                        Type = limitType,
                    };

                default:
                    return new LimitInfo()
                    {
                        Name = limitType.ToString(),
                        Type = limitType,
                    };
            }
        }
    }
}
