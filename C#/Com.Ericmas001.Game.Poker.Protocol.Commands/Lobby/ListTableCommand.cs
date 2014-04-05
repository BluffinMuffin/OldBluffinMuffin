using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
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

        public string EncodeResponse(List<TableInfo> tables)
        {
            return new ListTableResponse(this) { Tables = tables }.Encode();
        }
    }
}
