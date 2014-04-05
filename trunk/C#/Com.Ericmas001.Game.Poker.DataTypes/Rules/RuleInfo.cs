using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.DataTypes.Rules
{
    public class RuleInfo
    {
        public string Name { get; set; }
        public GameTypeEnum GameType { get; set; }
        public int MinPlayers { get; set; }
        public int MaxPlayers { get; set; }
        public List<LimitTypeEnum> AvailableLimits { get; set; }
        public LimitTypeEnum DefaultLimit { get; set; }
        public List<BlindTypeEnum> AvailableBlinds { get; set; }
        public BlindTypeEnum DefaultBlind { get; set; }
        public bool CanConfigWaitingTime { get; set; }
        public List<LobbyTypeEnum> AvailableLobbys { get; set; }
    }
}
