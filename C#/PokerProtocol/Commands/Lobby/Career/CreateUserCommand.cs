using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_USER";
        
        private readonly string m_Username;
        private readonly string m_Password;
        private readonly string m_Email;
        private readonly string m_DisplayName;

        public string Username
        {
            get { return m_Username; }
        }

        public string Password
        {
            get { return m_Password; }
        }

        public string Email
        {
            get { return m_Email; }
        }

        public string DisplayName
        {
            get { return m_DisplayName; }
        }

        public CreateUserCommand(StringTokenizer argsToken)
        {
            m_Username = argsToken.NextToken();
            m_Password = argsToken.NextToken();
            m_Email = argsToken.NextToken();
            m_DisplayName = argsToken.NextToken();
        }

        public CreateUserCommand(string p_Username, string p_Password, string p_Email, string p_DisplayName)
        {
            m_Username = p_Username;
            m_Password = p_Password;
            m_Email = p_Email;
            m_DisplayName = p_DisplayName;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Username);
            Append(sb, m_Password);
            Append(sb, m_Email);
            Append(sb, m_DisplayName);
        }

        public string EncodeResponse(bool yes)
        {
            return new CreateUserResponse(this, yes).Encode();
        }
    }
}
