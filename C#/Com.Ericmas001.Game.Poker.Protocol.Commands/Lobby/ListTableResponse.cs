using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class ListTableResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";

        public List<TupleTable> Tables { get; set; }

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
