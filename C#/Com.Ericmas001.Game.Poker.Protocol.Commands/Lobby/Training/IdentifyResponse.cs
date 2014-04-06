using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training
{
    public class IdentifyResponse : AbstractLobbyResponse<IdentifyCommand>
    {
        public static string COMMAND_NAME = "lobbyIDENTIFY_TRAINING_RESPONSE";

        public bool OK { get; set; }

        public IdentifyResponse()
            : base()
        {
        }

        public IdentifyResponse(IdentifyCommand command)
            : base(command)
        {
        }
    }
}
