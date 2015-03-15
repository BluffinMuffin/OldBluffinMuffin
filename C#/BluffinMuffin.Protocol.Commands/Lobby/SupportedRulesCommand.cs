using System.Linq;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public string EncodeResponse(RuleInfo[] rules)
        {
            return new SupportedRulesResponse(this) { Rules = rules.ToList() }.Encode();
        }
    }
}
