using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateUserResponse : AbstractLobbyResponse<CreateUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_USER_RESPONSE";

        public bool Success { get; private set; }

        public CreateUserResponse(JObject obj)
            : base(new CreateUserCommand((JObject)obj["Command"]))
        {
            Success = (bool)obj["Success"];
        }

        public CreateUserResponse(CreateUserCommand command, bool success)
            : base(command)
        {
            Success = success;
        }
    }
}
