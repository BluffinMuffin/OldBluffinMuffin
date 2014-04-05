using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class ListTableResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";

        public List<TableInfo> Tables { get; set; }

        public ListTableResponse()
            : base()
        {
        }

        public ListTableResponse(ListTableCommand command)
            : base(command)
        {
        }
    }
}
