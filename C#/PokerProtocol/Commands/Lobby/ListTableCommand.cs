using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;
using PokerProtocol.Entities;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyLIST_TABLES";
        public bool Training { get; private set; }

        public ListTableCommand(JObject obj)
        {
            Training = (bool)obj["Training"];
        }
        public ListTableCommand(bool training)
        {
            Training = training;
        }

        public string EncodeTrainingResponse(List<TableTraining> tables)
        {
            return new ListTableTrainingResponse(this, tables).Encode();
        }

        public string EncodeCareerResponse(List<TableCareer> tables)
        {
            return new ListTableCareerResponse(this, tables).Encode();
        }
    }
}
