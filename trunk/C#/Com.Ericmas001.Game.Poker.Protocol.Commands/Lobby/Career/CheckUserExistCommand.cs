using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class CheckUserExistCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST";

        public string Username { get; set; }

        public string EncodeResponse(bool exist)
        {
            return new CheckUserExistResponse(this) { Exist = exist }.Encode();
        }
    }
}
