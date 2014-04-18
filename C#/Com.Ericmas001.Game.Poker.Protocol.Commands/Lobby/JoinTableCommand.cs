﻿using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class JoinTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyJOIN_TABLE";

        public static int NOT_SEATED { get { return -1; } }

        public int TableID { get; set; }
        public string PlayerName { get; set; }

        public string EncodeResponse(bool success)
        {
            return new JoinTableResponse(this) { Success = success }.Encode();
        }
    }
}