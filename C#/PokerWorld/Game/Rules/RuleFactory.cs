using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game.Rules
{
    public static class RuleFactory
    {
        public static RuleInfo[] SupportedRules
        {
            get
            {
                //The order here is important! The most important game should be at the top, and so on.
                return new RuleInfo[]
                {
                    new RuleInfo()
                    {
                        Name = "Texas Hold'em",
                        GameType = GameTypeEnum.Holdem,
                        MinPlayers = 2,
                        MaxPlayers = 10,
                        AvailableLimits = new List<BetEnum>(){BetEnum.NoLimit,BetEnum.PotLimit,BetEnum.BetLimit},
                        DefaultLimit = BetEnum.NoLimit,
                        AvailableBlinds = new List<BlindEnum>(){BlindEnum.Blinds,BlindEnum.Antes,BlindEnum.None},
                        DefaultBlind = BlindEnum.Blinds,
                        CanConfigWaitingTime = true,
                        AvailableLobbys = new List<LobbyEnum>(){LobbyEnum.Training,LobbyEnum.Career},
                    },
                };
            }
        }
    }
}
