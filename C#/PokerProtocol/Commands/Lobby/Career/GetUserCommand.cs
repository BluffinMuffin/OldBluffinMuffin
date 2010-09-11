using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class GetUserCommand : AbstractLobbyCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyCAREER_GET_USER";
        
        private readonly string m_Username;

        public string Username
        {
            get { return m_Username; }
        }

        public GetUserCommand(StringTokenizer argsToken)
        {
            m_Username = argsToken.NextToken();
        }

        public GetUserCommand(string p_Username)
        {
            m_Username = p_Username;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Username);
        }

        public string EncodeResponse(string mail, string display, double money)
        {
            return new GetUserResponse(this, mail, display, money).Encode();
        }
    }
}
