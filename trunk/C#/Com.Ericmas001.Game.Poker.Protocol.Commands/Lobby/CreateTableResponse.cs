using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class CreateTableResponse : AbstractLobbyResponse<CreateTableCommand>
    {
        public static string COMMAND_NAME = "lobbyCREATE_TABLE_RESPONSE";

        public int IdTable { get; set; }

        public CreateTableResponse()
            : base()
        {
        }

        public CreateTableResponse(CreateTableCommand command)
            : base(command)
        {
        }
    }
}
