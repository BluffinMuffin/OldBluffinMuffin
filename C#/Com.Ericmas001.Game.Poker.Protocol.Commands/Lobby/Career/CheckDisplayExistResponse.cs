using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class CheckDisplayExistResponse : AbstractLobbyResponse<CheckDisplayExistCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST_RESPONSE";
        public bool Exist { get; set; }

        public CheckDisplayExistResponse()
            : base()
        {
        }

        public CheckDisplayExistResponse(CheckDisplayExistCommand command)
            : base(command)
        {
        }
    }
}
