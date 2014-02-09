using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class AuthenticateUserResponse : AbstractLobbyResponse<AuthenticateUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER_RESPONSE";
        private readonly bool m_Success;
        public bool Success
        {
            get { return m_Success; }
        } 


        public AuthenticateUserResponse(StringTokenizer argsToken)
            : base(new AuthenticateUserCommand(argsToken))
        {
            m_Success = bool.Parse(argsToken.NextToken());
        }

        public AuthenticateUserResponse(AuthenticateUserCommand command, bool success)
            : base(command)
        {
            m_Success = success;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Success);
        }
    }
}
