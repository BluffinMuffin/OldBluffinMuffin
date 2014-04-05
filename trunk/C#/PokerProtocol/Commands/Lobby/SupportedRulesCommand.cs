using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
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
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbySUPPORTED_RULES";

        public string EncodeResponse()
        {
            return new SupportedRulesResponse(this) { Rules = RuleFactory.SupportedRules.ToList() }.Encode();
        }
    }
}
