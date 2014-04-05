using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_USER";

        public string Username { get;  set; }
        public string Password { get;  set; }
        public string Email { get;  set; }
        public string DisplayName { get;  set; }

        public string EncodeResponse(bool success)
        {
            return new CreateUserResponse(this) { Success = success }.Encode();
        }
    }
}
