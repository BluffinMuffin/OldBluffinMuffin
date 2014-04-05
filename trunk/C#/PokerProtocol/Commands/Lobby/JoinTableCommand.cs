using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby
{
    public class JoinTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyJOIN_TABLE";

        public static int NOT_SEATED { get { return -1; } }

        public int TableID { get; set; }
        public string PlayerName { get; set; }

        public string EncodeResponse(int seat)
        {
            return new JoinTableResponse(this) { NoSeat = seat }.Encode();
        }

        public string EncodeErrorResponse()
        {
            return new JoinTableResponse(this) { NoSeat = JoinTableCommand.NOT_SEATED }.Encode();
        }
    }
}
