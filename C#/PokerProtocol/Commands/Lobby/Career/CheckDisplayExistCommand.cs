using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CheckDisplayExistCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST";
        
        private readonly string m_DisplayName;

        public string DisplayName
        {
            get { return m_DisplayName; }
        }

        public CheckDisplayExistCommand(StringTokenizer argsToken)
        {
            m_DisplayName = argsToken.NextToken();
        }

        public CheckDisplayExistCommand(string p_DisplayName)
        {
            m_DisplayName = p_DisplayName;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_DisplayName);
        }

        public string EncodeResponse(bool yes)
        {
            return new CheckDisplayExistResponse(this, yes).Encode();
        }
    }
}
