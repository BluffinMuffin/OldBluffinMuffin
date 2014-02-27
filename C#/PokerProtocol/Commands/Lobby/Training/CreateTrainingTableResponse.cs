using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class CreateTrainingTableResponse : AbstractLobbyResponse<CreateTrainingTableCommand>
    {
        public static string COMMAND_NAME = "lobbyTRAINING_CREATE_TABLE_RESPONSE";

        public int Port { get; private set; }

        public CreateTrainingTableResponse(JObject obj)
            : base(new CreateTrainingTableCommand((JObject)obj["Command"]))
        {
            Port = (int)obj["Port"];
        }

        public CreateTrainingTableResponse(CreateTrainingTableCommand command, int port)
            : base(command)
        {
            Port = port;
        }
    }
}
