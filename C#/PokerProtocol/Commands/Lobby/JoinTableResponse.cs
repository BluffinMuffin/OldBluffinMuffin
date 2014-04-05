using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby
{
    public class JoinTableResponse : AbstractLobbyResponse<JoinTableCommand>
    {
        public static string COMMAND_NAME = "lobbyJOIN_TABLE_RESPONSE";

        public int NoSeat { get; set; }

        public JoinTableResponse()
            : base()
        {
        }

        public JoinTableResponse(JoinTableCommand command)
            : base(command)
        {
        }
    }
}
