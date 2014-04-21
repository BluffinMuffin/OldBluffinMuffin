using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.Logic
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
                        AvailableLimits = new List<LimitTypeEnum>(){LimitTypeEnum.NoLimit/*,LimitTypeEnum.FixedLimit,LimitTypeEnum.PotLimit*/ },
                        DefaultLimit = LimitTypeEnum.NoLimit,
                        AvailableBlinds = new List<BlindTypeEnum>(){BlindTypeEnum.Blinds, BlindTypeEnum.Antes, BlindTypeEnum.None},
                        DefaultBlind = BlindTypeEnum.Blinds,
                        CanConfigWaitingTime = true,
                        AvailableLobbys = new List<LobbyTypeEnum>(){LobbyTypeEnum.Training, LobbyTypeEnum.Career},
                    },
                };
            }
        }
    }
}
