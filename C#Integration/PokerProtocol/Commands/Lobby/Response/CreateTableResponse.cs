using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Response
{
    public class CreateTableResponse : AbstractCommandResponse<CreateTableCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyCREATE_TABLE_RESPONSE";
        private readonly int m_Port;
        public int Port
        {
            get { return m_Port; }
        } 


        public CreateTableResponse(StringTokenizer argsToken)
            : base(new CreateTableCommand(argsToken))
        {
            m_Port = int.Parse(argsToken.NextToken());
        }

        public CreateTableResponse(CreateTableCommand command, int port)
            : base(command)
        {
            m_Port = Port;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Port);
        }
    }
}
