using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class IdentifyResponse : AbstractLobbyResponse<IdentifyCommand>
    {
        public static string COMMAND_NAME = "lobbyIDENTIFY_TRAINING_RESPONSE";
        private readonly bool m_OK;
        public bool OK
        {
            get { return m_OK; }
        } 


        public IdentifyResponse(StringTokenizer argsToken)
            : base(new IdentifyCommand(argsToken))
        {
            m_OK = bool.Parse(argsToken.NextToken());
        }

        public IdentifyResponse(IdentifyCommand command, bool ok)
            : base(command)
        {
            m_OK = ok;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_OK);
        }
    }
}
