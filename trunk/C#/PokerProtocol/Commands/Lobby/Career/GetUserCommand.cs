using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class GetUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_GET_USER";

        public string Username { get; set; }

        public GetUserCommand()
        {
        }

        public GetUserCommand(string p_Username)
        {
            Username = p_Username;
        }

        public string EncodeResponse(string mail, string display, double money)
        {
            return new GetUserResponse(this, mail, display, money).Encode();
        }
    }
}
