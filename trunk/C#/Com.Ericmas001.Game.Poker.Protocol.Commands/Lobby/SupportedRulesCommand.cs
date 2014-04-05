using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Newtonsoft.Json.Linq;
using PokerWorld.Game;
using PokerWorld.Game.Rules;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbySUPPORTED_RULES";

        public string EncodeResponse()
        {
            return new SupportedRulesResponse(this) { Rules = RuleFactory.SupportedRules.ToList() }.Encode();
        }
    }
}
