﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby
{
    public class JoinTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyJOIN_TABLE";

        public int TableID { get; private set; }
        public string PlayerName { get; private set; }

        public JoinTableCommand(JObject obj)
        {
            PlayerName = (string)obj["PlayerName"];
            TableID = (int)obj["TableID"];
        }

        public JoinTableCommand(int p_tableID, string p_playerName)
        {
            TableID = p_tableID;
            PlayerName = p_playerName;
        }

        public string EncodeResponse(int seat)
        {
            return new JoinTableResponse(this, seat).Encode();
        }

        public string EncodeErrorResponse()
        {
            return new JoinTableResponse(this, -1).Encode();
        }
    }
}