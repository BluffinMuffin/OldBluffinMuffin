using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CheckUserExistCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_USER_EXIST";
        
        private readonly string m_Username;

        public string Username
        {
            get { return m_Username; }
        }

        public CheckUserExistCommand(StringTokenizer argsToken)
        {
            m_Username = argsToken.NextToken();
        }

        public CheckUserExistCommand(string p_Username)
        {
            m_Username = p_Username;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Username);
        }

        public string EncodeResponse(bool yes)
        {
            return new CheckUserExistResponse(this, yes).Encode();
        }
    }
}
