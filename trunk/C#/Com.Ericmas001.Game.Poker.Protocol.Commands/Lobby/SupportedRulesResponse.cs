using System.Collections.Generic;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
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
