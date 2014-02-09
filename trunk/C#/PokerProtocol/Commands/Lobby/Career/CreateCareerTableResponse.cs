using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateCareerTableResponse : AbstractLobbyResponse<CreateCareerTableCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_TABLE_RESPONSE";
        private readonly int m_Port;
        public int Port
        {
            get { return m_Port; }
        } 


        public CreateCareerTableResponse(StringTokenizer argsToken)
            : base(new CreateCareerTableCommand(argsToken))
        {
            m_Port = int.Parse(argsToken.NextToken());
        }

        public CreateCareerTableResponse(CreateCareerTableCommand command, int port)
            : base(command)
        {
            m_Port = port;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Port);
        }
    }
}
