using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class AuthenticateUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_AUTHENTICATE_USER";
        
        private readonly string m_Username;
        private readonly string m_Password;

        public string Username
        {
            get { return m_Username; }
        }

        public string Password
        {
            get { return m_Password; }
        }

        public AuthenticateUserCommand(StringTokenizer argsToken)
        {
            m_Username = argsToken.NextToken();
            m_Password = argsToken.NextToken();
        }

        public AuthenticateUserCommand(string p_Username, string p_Password)
        {
            m_Username = p_Username;
            m_Password = p_Password;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Username);
            Append(sb, m_Password);
        }

        public string EncodeResponse(bool yes)
        {
            return new AuthenticateUserResponse(this, yes).Encode();
        }
    }
}
