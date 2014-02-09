using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;
using PokerProtocol.Entities;

namespace PokerProtocol.Commands.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        //TODO RICK: Make ListTrainingTableCommand and ListCareerTableCommand and get rid of the boolean!

        public static string COMMAND_NAME = "lobbyLIST_TABLES";
        private readonly bool m_Training;
        public bool Training
        {
            get { return m_Training; }
        } 

        public ListTableCommand(StringTokenizer argsToken)
        {
            m_Training = bool.Parse(argsToken.NextToken());
        }


        public ListTableCommand(bool training)
        {
            m_Training = training;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Training);
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
