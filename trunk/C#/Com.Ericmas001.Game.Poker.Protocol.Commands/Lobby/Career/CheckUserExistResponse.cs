using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class CheckUserExistResponse : AbstractLobbyResponse<CheckUserExistCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST_RESPONSE";

        public bool Exist { get; set; }

        public CheckUserExistResponse()
            : base()
        {
        }

        public CheckUserExistResponse(CheckUserExistCommand command)
            : base(command)
        {
        }
    }
}
