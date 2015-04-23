using System.Collections.Generic;
using System.Linq;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Protocol.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public SupportedRulesResponse Response(IEnumerable<RuleInfo> rules)
        {
            return new SupportedRulesResponse(this) { Rules = rules.ToList() };
        }
    }
}
