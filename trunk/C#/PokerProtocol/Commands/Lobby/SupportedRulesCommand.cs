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

namespace PokerProtocol.Commands.Lobby
{
    public class SupportedRulesCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbySUPPORTED_RULES";

        public SupportedRulesCommand()
        {
        }

        public string EncodeResponse()
        {
            return new SupportedRulesResponse(this, RuleFactory.SupportedRules.ToList()).Encode();
        }
    }
}
