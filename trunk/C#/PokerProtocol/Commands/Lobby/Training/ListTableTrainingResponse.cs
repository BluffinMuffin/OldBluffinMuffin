using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Entities;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class ListTableTrainingResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyTRAINING_LIST_TABLES_RESPONSE";

        public List<TableTraining> Tables { get; private set; }

        public ListTableTrainingResponse(JObject obj)
            : base(new ListTableCommand((JObject)obj["Command"]))
        {
            Tables = ((JArray)obj["Tables"]).Select(x => new TableTraining((JObject)x)).ToList();
        }

        public ListTableTrainingResponse(ListTableCommand command, List<TableTraining> tables)
            : base(command)
        {
            Tables = tables;
        }
    }
}
