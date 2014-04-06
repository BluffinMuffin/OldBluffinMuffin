using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class CheckDisplayExistCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST";

        public string DisplayName { get; set; }

        public string EncodeResponse(bool exist)
        {
            return new CheckDisplayExistResponse(this) { Exist = exist }.Encode();
        }
    }
}
