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
        public string DecodedCommand 
        {
            get
            {
                return HttpUtility.UrlDecode(EncodedCommand);
            }
        }
    }
}
