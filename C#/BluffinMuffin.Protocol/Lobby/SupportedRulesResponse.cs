using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Protocol.Lobby
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
