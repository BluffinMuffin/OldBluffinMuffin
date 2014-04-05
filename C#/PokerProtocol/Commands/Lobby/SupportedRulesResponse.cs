using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;
using PokerProtocol.Entities;
using Newtonsoft.Json.Linq;
using PokerWorld.Game;
using PokerWorld.Game.Rules;

namespace PokerProtocol.Commands.Lobby
{
    public class SupportedRulesResponse : AbstractLobbyResponse<SupportedRulesCommand>
    {
        public static string COMMAND_NAME = "lobbySUPPORTED_RULES_RESPONSE";

        public List<RuleInfo> Rules { get; set; }

        public SupportedRulesResponse()
            : base()
        {
        }
        public SupportedRulesResponse(SupportedRulesCommand command, List<RuleInfo> rules)
            : base(command)
        {
            Rules = rules;
        }
    }
}
