using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class IdentifyResponse : AbstractLobbyResponse<IdentifyCommand>
    {
        public static string COMMAND_NAME = "lobbyIDENTIFY_TRAINING_RESPONSE";

        public bool OK { get; private set; }

        public IdentifyResponse(JObject obj)
            : base(new IdentifyCommand((JObject)obj["Command"]))
        {
            OK = (bool)obj["OK"];
        }

        public IdentifyResponse(IdentifyCommand command, bool ok)
            : base(command)
        {
            OK = ok;
        }
    }
}
