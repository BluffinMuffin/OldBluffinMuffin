using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class CreateTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCREATE_TABLE";
        public GameRule GameRules { get; set; }

        public string EncodeResponse(int id)
        {
            return new CreateTableResponse(this) { IdTable = id }.Encode();
        }
    }
}
