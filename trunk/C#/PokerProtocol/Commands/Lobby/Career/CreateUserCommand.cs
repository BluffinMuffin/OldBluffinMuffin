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

        public string Username { get; private set; }
        public string Password { get; private set; }
        public string Email { get; private set; }
        public string DisplayName { get; private set; }

        public CreateUserCommand(JObject obj)
        {
            Username = (string)obj["Username"];
            Password = (string)obj["Password"];
            Email = (string)obj["Email"];
            DisplayName = (string)obj["DisplayName"];
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
