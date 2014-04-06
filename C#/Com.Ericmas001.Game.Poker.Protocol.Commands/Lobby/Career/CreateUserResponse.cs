﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class CreateUserResponse : AbstractLobbyResponse<CreateUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_USER_RESPONSE";

        public bool Success { get; set; }

        public CreateUserResponse()
            : base()
        {
        }

        public CreateUserResponse(CreateUserCommand command)
            : base(command)
        {
        }
    }
}