using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Entities;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class ListTableTrainingResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyTRAINING_LIST_TABLES_RESPONSE";
        private readonly List<TableTraining> m_Tables;
        public List<TableTraining> Tables
        {
            get { return m_Tables; }
        }


        public ListTableTrainingResponse(StringTokenizer argsToken)
            : base(new ListTableCommand(argsToken))
        {
            m_Tables = new List<TableTraining>();
            int count = int.Parse(argsToken.NextToken());
            for (int i = 0; i < count; ++i)
            {
                m_Tables.Add(new TableTraining(argsToken));
            }
        }

        public ListTableTrainingResponse(ListTableCommand command, List<TableTraining> tables)
            : base(command)
        {
            m_Tables = tables;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Tables.Count);
            foreach (TableTraining tti in m_Tables)
                Append(sb, tti.ToString(AbstractLobbyCommand.Delimitter));
        }
    }
}
