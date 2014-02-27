using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CheckUserExistCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST";

        public string Username { get; private set; }

        public CheckUserExistCommand(JObject obj)
        {
            Username = (string)obj["Username"];
        }

        public CheckUserExistCommand(string p_Username)
        {
            Username = p_Username;
        }

        public string EncodeResponse(bool yes)
        {
            return new CheckUserExistResponse(this, yes).Encode();
        }
    }
}
