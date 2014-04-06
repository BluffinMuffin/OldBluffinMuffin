using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyLIST_TABLES";
        public LobbyTypeEnum[] LobbyTypes { get; set; }

        public string EncodeResponse(List<TupleTable> tables)
        {
            return new ListTableResponse(this) { Tables = tables }.Encode();
        }
    }
}
