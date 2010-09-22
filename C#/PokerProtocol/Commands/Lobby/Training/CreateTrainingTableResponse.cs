using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class CreateTrainingTableResponse : AbstractLobbyResponse<CreateTrainingTableCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyTRAINING_CREATE_TABLE_RESPONSE";
        private readonly int m_Port;
        public int Port
        {
            get { return m_Port; }
        } 


        public CreateTrainingTableResponse(StringTokenizer argsToken)
            : base(new CreateTrainingTableCommand(argsToken))
        {
            m_Port = int.Parse(argsToken.NextToken());
        }

        public CreateTrainingTableResponse(CreateTrainingTableCommand command, int port)
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
