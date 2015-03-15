using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class SupportedRulesResponse : AbstractLobbyResponse<SupportedRulesCommand>
    {
        public List<RuleInfo> Rules { get; set; }

        public SupportedRulesResponse()
        {
        }
        public SupportedRulesResponse(SupportedRulesCommand command)
            : base(command)
        {
        }
    }
}
