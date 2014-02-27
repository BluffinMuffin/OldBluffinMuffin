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

        public int NoSeat { get; private set; }

        public JoinTableResponse(JObject obj)
            : base(new JoinTableCommand((JObject)obj["Command"]))
        {
            NoSeat = (int)obj["NoSeat"];
        }


        public JoinTableResponse(JoinTableCommand command, int seat)
            : base(command)
        {
            NoSeat = seat;
        }
    }
}
