using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Entities;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class ListTableCareerResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_LIST_TABLES_RESPONSE";
        private readonly List<TableCareer> m_Tables;
        public List<TableCareer> Tables
        {
            get { return m_Tables; }
        }


        public ListTableCareerResponse(StringTokenizer argsToken)
            : base(new ListTableCommand(argsToken))
        {
            m_Tables = new List<TableCareer>();
            int count = int.Parse(argsToken.NextToken());
            for (int i = 0; i < count; ++i)
            {
                m_Tables.Add(new TableCareer(argsToken));
            }
        }

        public ListTableCareerResponse(ListTableCommand command, List<TableCareer> tables)
            : base(command)
        {
            m_Tables = tables;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Tables.Count);
            foreach (TableCareer tti in m_Tables)
                Append(sb, tti.ToString(AbstractLobbyCommand.Delimitter));
        }
    }
}
