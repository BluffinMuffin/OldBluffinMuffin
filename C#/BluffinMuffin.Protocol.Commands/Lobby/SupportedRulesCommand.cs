using System.Linq;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public SupportedRulesResponse Response(RuleInfo[] rules)
        {
            return new SupportedRulesResponse(this) { Rules = rules.ToList() };
        }
        public string EncodeResponse(RuleInfo[] rules)
        {
            return Response(rules).Encode();
        }
    }
}
