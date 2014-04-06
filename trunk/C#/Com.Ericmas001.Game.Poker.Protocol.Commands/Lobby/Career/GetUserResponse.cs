using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using System.Globalization;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class GetUserResponse : AbstractLobbyResponse<GetUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_GET_USER_RESPONSE";

        public string Email { get; set; }
        public string DisplayName { get; set; }
        public double Money { get; set; }

        public GetUserResponse()
            : base()
        {
        }

        public GetUserResponse(GetUserCommand command)
            : base(command)
        {
        }
    }
}
