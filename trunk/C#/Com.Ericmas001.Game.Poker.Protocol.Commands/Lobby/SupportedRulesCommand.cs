using System.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public string EncodeResponse(RuleInfo[] rules)
        {
            return new SupportedRulesResponse(this) { Rules = rules.ToList() }.Encode();
        }
    }
}
