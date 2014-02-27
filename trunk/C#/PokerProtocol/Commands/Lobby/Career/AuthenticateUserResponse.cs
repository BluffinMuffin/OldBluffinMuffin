using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class AuthenticateUserResponse : AbstractLobbyResponse<AuthenticateUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER_RESPONSE";

        public bool Success { get; private set; }

        public AuthenticateUserResponse(JObject obj)
            : base(new AuthenticateUserCommand((JObject)obj["Command"]))
        {
            Success = (bool)obj["Success"];
        }

        public AuthenticateUserResponse(AuthenticateUserCommand command, bool success)
            : base(command)
        {
            Success = success;
        }
    }
}
