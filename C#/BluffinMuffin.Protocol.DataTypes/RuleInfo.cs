using BluffinMuffin.Protocol.DataTypes.Enums;
using System.Collections.Generic;

namespace BluffinMuffin.Protocol.DataTypes
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
