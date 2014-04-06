using System;
using System.Collections.Generic;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using System.Text;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training
{
    public class IdentifyCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyTRAINING_IDENTIFY";

        public string Name { get; set; }

        public string EncodeResponse( bool success )
        {
            return new IdentifyResponse(this) { OK = success }.Encode();
        }
    }
}
