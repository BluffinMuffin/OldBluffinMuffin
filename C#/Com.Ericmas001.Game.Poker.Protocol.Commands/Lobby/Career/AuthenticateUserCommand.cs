﻿using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class AuthenticateUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER";

        public string Username { get; set; }
        public string Password { get; set; }

        public string EncodeResponse(bool success)
        {
            return new AuthenticateUserResponse(this) { Success = success }.Encode();
        }
    }
}
