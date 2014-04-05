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

        public bool Success { get; set; }

        public AuthenticateUserResponse()
            : base()
        {
        }

        public AuthenticateUserResponse(AuthenticateUserCommand command)
            : base(command)
        {
        }
    }
}
