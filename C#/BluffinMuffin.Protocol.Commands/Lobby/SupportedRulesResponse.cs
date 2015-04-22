using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class SupportedRulesResponse : AbstractBluffinReponse<SupportedRulesCommand>
    {
        public List<RuleInfo> Rules { get; set; }

        public SupportedRulesResponse(SupportedRulesCommand command)
            : base(command)
        {
        }
    }
}
