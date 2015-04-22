using System.Collections.Generic;
using System.Linq;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public SupportedRulesResponse Response(IEnumerable<RuleInfo> rules)
        {
            return new SupportedRulesResponse(this) { Rules = rules.ToList() };
        }
    }
}
