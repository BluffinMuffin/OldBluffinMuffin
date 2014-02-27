using System;
using System.Collections.Generic;
using EricUtility;
using EricUtility.Networking.Commands;
using System.Text;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class IdentifyCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyTRAINING_IDENTIFY";

        public string Name { get; set; }

        public IdentifyCommand()
        {
        }

        public IdentifyCommand(string name)
        {
            Name = name;
        }

        public string EncodeResponse( bool success )
        {
            return new IdentifyResponse(this, success).Encode();
        }
    }
}
