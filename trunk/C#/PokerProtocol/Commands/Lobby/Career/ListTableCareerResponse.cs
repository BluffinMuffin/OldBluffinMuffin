using System;
using System.Collections.Generic;
using System.Linq;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Entities;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class ListTableCareerResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_LIST_TABLES_RESPONSE";

        public List<TableCareer> Tables { get; set; }

        public ListTableCareerResponse()
            : base()
        {
        }

        public ListTableCareerResponse(ListTableCommand command, List<TableCareer> tables)
            : base(command)
        {
            Tables = tables;
        }
    }
}
