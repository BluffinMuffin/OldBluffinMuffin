using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game.Rules
{
    public class RuleInfo
    {
        public string Name { get; set; }
        public GameTypeEnum GameType { get; set; }
        public int MinPlayers { get; set; }
        public int MaxPlayers { get; set; }
        public List<BetEnum> AvailableLimits { get; set; }
        public BetEnum DefaultLimit { get; set; }
        public List<BlindEnum> AvailableBlinds { get; set; }
        public BlindEnum DefaultBlind { get; set; }
        public bool CanConfigWaitingTime { get; set; }
        public List<LobbyEnum> AvailableLobbys { get; set; }
    }
}
