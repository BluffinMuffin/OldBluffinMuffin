using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game
{
    public static class RuleFactory
    {
        public static RuleInfo[] SupportedRules
        {
            get
            {
                return new RuleInfo[]
                {
                    new RuleInfo("Texas Hold'em", GameTypeEnum.Holdem, 2, 10, new List<BetEnum>(){BetEnum.NoLimit}, new List<BlindEnum>(){BlindEnum.Blinds}, true, true)
                };
            }
        }
    }
}
