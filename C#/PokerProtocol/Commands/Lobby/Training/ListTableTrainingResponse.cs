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

        public List<TableTraining> Tables { get; set; }

        public ListTableTrainingResponse()
            : base()
        {
        }

        public ListTableTrainingResponse(ListTableCommand command, List<TableTraining> tables)
            : base(command)
        {
            Tables = tables;
        }
    }
}
