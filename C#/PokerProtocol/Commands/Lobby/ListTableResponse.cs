using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Entities;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby
{
    public class ListTableResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";

        public List<Table> Tables { get; set; }

        public ListTableResponse()
            : base()
        {
        }

        public ListTableResponse(ListTableCommand command, List<Table> tables)
            : base(command)
        {
            Tables = tables;
        }
    }
}
