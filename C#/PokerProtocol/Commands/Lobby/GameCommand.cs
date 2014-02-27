using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;
using System.Web;
using Newtonsoft.Json;

namespace PokerProtocol.Commands.Lobby
{
    public class GameCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyGAME_COMMAND";

        public int TableID { get; set; }

        public string EncodedCommand { get; set; }

        [JsonIgnore]
        public string Command 
        {
            get
            {
                return HttpUtility.UrlDecode(EncodedCommand);
            }
            private set
            {
                EncodedCommand = HttpUtility.UrlEncode(value);
            }
        }

        public GameCommand()
        {
        }

        public GameCommand(int p_tableID, string p_Command)
        {
            TableID = p_tableID;
            EncodedCommand = p_Command;
        }
    }
}
