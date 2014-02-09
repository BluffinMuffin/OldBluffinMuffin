using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CheckUserExistResponse : AbstractLobbyResponse<CheckUserExistCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST_RESPONSE";
        private readonly bool m_Exist;
        public bool Exist
        {
            get { return m_Exist; }
        } 


        public CheckUserExistResponse(StringTokenizer argsToken)
            : base(new CheckUserExistCommand(argsToken))
        {
            m_Exist = bool.Parse(argsToken.NextToken());
        }

        public CheckUserExistResponse(CheckUserExistCommand command, bool exist)
            : base(command)
        {
            m_Exist = exist;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Exist);
        }
    }
}
