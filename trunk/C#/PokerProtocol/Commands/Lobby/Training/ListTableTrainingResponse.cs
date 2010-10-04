using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class ListTableTrainingResponse : AbstractLobbyResponse<ListTableCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyTRAINING_LIST_TABLES_RESPONSE";
        private readonly List<TupleTableInfoTraining> m_Tables;
        public List<TupleTableInfoTraining> Tables
        {
            get { return m_Tables; }
        }


        public ListTableTrainingResponse(StringTokenizer argsToken)
            : base(new ListTableCommand(argsToken))
        {
            m_Tables = new List<TupleTableInfoTraining>();
            int count = int.Parse(argsToken.NextToken());
            for (int i = 0; i < count; ++i)
            {
                m_Tables.Add(new TupleTableInfoTraining(argsToken));
            }
        }

        public ListTableTrainingResponse(ListTableCommand command, List<TupleTableInfoTraining> tables)
            : base(command)
        {
            m_Tables = tables;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Tables.Count);
            foreach (TupleTableInfoTraining tti in m_Tables)
                Append(sb, tti.ToString(AbstractLobbyCommand.Delimitter));
        }
    }
}
