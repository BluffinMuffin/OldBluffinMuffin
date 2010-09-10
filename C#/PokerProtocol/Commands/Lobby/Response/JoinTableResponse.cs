using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Response
{
    public class JoinTableResponse : AbstractLobbyCommandResponse<JoinTableCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyJOIN_TABLE_RESPONSE";
        private readonly int m_NoSeat;
        public int NoSeat
        {
            get { return m_NoSeat; }
        } 


        public JoinTableResponse(StringTokenizer argsToken)
            : base(new JoinTableCommand(argsToken))
        {
            m_NoSeat = int.Parse(argsToken.NextToken());
        }

        public JoinTableResponse(JoinTableCommand command, int seat)
            : base(command)
        {
            m_NoSeat = seat;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_NoSeat);
        }
    }
}
