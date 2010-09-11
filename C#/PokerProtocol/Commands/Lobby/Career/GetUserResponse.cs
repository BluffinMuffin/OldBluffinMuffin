using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using System.Globalization;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class GetUserResponse : AbstractLobbyResponse<GetUserCommand>
    {

        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyCAREER_GET_USER_RESPONSE";

        private readonly string m_Email;
        private readonly string m_DisplayName;
        private readonly double m_Money;


        public string Email
        {
            get { return m_Email; }
        }

        public string DisplayName
        {
            get { return m_DisplayName; }
        }

        public double Money
        {
            get { return m_Money; }
        } 


        public GetUserResponse(StringTokenizer argsToken)
            : base(new GetUserCommand(argsToken))
        {
            m_Email = argsToken.NextToken();
            m_DisplayName = argsToken.NextToken();
            m_Money = double.Parse(argsToken.NextToken(), CultureInfo.InvariantCulture);
        }

        public GetUserResponse(GetUserCommand command, string mail, string display, double money)
            : base(command)
        {
            m_Email = mail;
            m_DisplayName = display;
            m_Money = money;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_Email);
            Append(sb, m_DisplayName);
            Append(sb, m_Money.ToString(CultureInfo.InvariantCulture));
        }
    }
}
