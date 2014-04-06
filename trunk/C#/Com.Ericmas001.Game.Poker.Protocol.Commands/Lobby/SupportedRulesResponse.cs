using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes.Rules;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class SupportedRulesResponse : AbstractLobbyResponse<SupportedRulesCommand>
    {
        public static string COMMAND_NAME = "lobbySUPPORTED_RULES_RESPONSE";

        public List<RuleInfo> Rules { get; set; }

        public SupportedRulesResponse()
            : base()
        {
        }
        public SupportedRulesResponse(SupportedRulesCommand command)
            : base(command)
        {
        }
    }
}
