using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;
using System.Web;
using Newtonsoft.Json;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
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
