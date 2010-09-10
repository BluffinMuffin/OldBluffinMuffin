using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Response
{
    public class ListTableResponse : AbstractLobbyCommandResponse<ListTableCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyLIST_TABLES_RESPONSE";
        private readonly List<TupleTableInfo> m_Tables;
        public List<TupleTableInfo> Tables
        {
            get { return m_Tables; }
        }


        public ListTableResponse(StringTokenizer argsToken)
            : base(new ListTableCommand(argsToken))
        {
            m_Tables = new List<TupleTableInfo>();
            int count = int.Parse(argsToken.NextToken());
            for (int i = 0; i < count; ++i)
            {
                m_Tables.Add(new TupleTableInfo(argsToken));
            }
        }

        public ListTableResponse(ListTableCommand command, List<TupleTableInfo> tables)
            : base(command)
        {
            m_Tables = tables;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Tables.Count);
            foreach (TupleTableInfo tti in m_Tables)
                Append(sb, tti.ToString(AbstractLobbyCommand.Delimitter));
        }
    }
}
