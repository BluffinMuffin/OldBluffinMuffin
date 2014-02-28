using PokerWorld.Game.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerWorld.Game
{
    public class RuleInfo
    {
        public string Name { get; set; }
        public GameTypeEnum GameType { get; set; }
        public int MinPlayers { get; set; }
        public int MaxPlayers { get; set; }
        public List<BetEnum> AvailableLimits { get; set; }
        public List<BlindEnum> AvailableBlinds { get; set; }
        public bool CanConfigWaitingTime { get; set; }
        public bool HasTrainingMode { get; set; }

        public RuleInfo()
        {
        }

        public RuleInfo(string name, GameTypeEnum gameType, int minPlayers, int maxPlayers, List<BetEnum> availableLimits, List<BlindEnum> availableBlinds, bool canConfigWaitingTime, bool hasTrainingMode)
        {
            Name = name;
            GameType = gameType;
            MinPlayers = minPlayers;
            MaxPlayers = maxPlayers;
            AvailableLimits = availableLimits;
            AvailableBlinds = availableBlinds;
            CanConfigWaitingTime = canConfigWaitingTime;
            HasTrainingMode = hasTrainingMode;
        }
    }
}
