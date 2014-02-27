using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;
using System.Web;

namespace PokerProtocol.Commands.Lobby
{
    public class GameCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyGAME_COMMAND";

        public int TableID { get; private set; }
        public string Command { get; private set; }

        public GameCommand(JObject obj)
        {
            Command = (string)obj["Command"];
            TableID = (int)obj["TableID"];
        }

        public GameCommand(int p_tableID, string p_Command)
        {
            TableID = p_tableID;
            Command = HttpUtility.UrlEncode(p_Command);
        }
    }
}
