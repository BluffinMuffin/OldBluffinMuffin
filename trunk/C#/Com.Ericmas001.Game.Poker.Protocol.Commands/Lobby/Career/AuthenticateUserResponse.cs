﻿using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class AuthenticateUserResponse : AbstractLobbyResponse<AuthenticateUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER_RESPONSE";

        public bool Success { get; set; }

        public AuthenticateUserResponse()
            : base()
        {
        }

        public AuthenticateUserResponse(AuthenticateUserCommand command)
            : base(command)
        {
        }
    }
}