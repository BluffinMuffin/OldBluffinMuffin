using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class CreateTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCREATE_TABLE";
        public TableParams Params { get; set; }

        public string EncodeResponse(int id)
        {
            return new CreateTableResponse(this) { IdTable = id }.Encode();
        }
    }
}
