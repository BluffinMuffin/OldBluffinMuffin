using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;
using PokerProtocol.Entities;
using Newtonsoft.Json.Linq;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Lobby
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
