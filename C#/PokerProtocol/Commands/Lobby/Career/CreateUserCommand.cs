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

        public CreateUserCommand()
        {
        }

        public CreateUserCommand(string p_Username, string p_Password, string p_Email, string p_DisplayName)
        {
            Username = p_Username;
            Password = p_Password;
            Email = p_Email;
            DisplayName = p_DisplayName;
        }

        public string EncodeResponse(bool yes)
        {
            return new CreateUserResponse(this, yes).Encode();
        }
    }
}
