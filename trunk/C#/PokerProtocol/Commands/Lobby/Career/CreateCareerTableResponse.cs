using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateCareerTableResponse : AbstractLobbyResponse<CreateCareerTableCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_TABLE_RESPONSE";

        public int Port { get; set; }

        public CreateCareerTableResponse()
            : base()
        {
        }

        public CreateCareerTableResponse(CreateCareerTableCommand command, int port)
            : base(command)
        {
            Port = port;
        }
    }
}
