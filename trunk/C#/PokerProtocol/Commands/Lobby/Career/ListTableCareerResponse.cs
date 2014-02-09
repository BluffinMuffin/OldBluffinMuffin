using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class ListTableCareerResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_LIST_TABLES_RESPONSE";
        private readonly List<TupleTableInfoCareer> m_Tables;
        public List<TupleTableInfoCareer> Tables
        {
            get { return m_Tables; }
        }


        public ListTableCareerResponse(StringTokenizer argsToken)
            : base(new ListTableCommand(argsToken))
        {
            m_Tables = new List<TupleTableInfoCareer>();
            int count = int.Parse(argsToken.NextToken());
            for (int i = 0; i < count; ++i)
            {
                m_Tables.Add(new TupleTableInfoCareer(argsToken));
            }
        }

        public ListTableCareerResponse(ListTableCommand command, List<TupleTableInfoCareer> tables)
            : base(command)
        {
            m_Tables = tables;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Tables.Count);
            foreach (TupleTableInfoCareer tti in m_Tables)
                Append(sb, tti.ToString(AbstractLobbyCommand.Delimitter));
        }
    }
}
