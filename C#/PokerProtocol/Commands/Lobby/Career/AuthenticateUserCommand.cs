using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class AuthenticateUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER";

        public string Username { get; set; }
        public string Password { get; set; }

        public AuthenticateUserCommand()
        {
        }

        public AuthenticateUserCommand(string p_Username, string p_Password)
        {
            Username = p_Username;
            Password = p_Password;
        }

        public string EncodeResponse(bool yes)
        {
            return new AuthenticateUserResponse(this, yes).Encode();
        }
    }
}
