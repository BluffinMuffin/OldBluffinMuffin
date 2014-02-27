using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CheckDisplayExistCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST";

        public string DisplayName { get; private set; }

        public CheckDisplayExistCommand(JObject obj)
        {
            DisplayName = (string)obj["DisplayName"];
        }

        public CheckDisplayExistCommand(string p_DisplayName)
        {
            DisplayName = p_DisplayName;
        }

        public string EncodeResponse(bool yes)
        {
            return new CheckDisplayExistResponse(this, yes).Encode();
        }
    }
}
