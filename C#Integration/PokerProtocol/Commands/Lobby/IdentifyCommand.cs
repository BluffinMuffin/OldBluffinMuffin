using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands.Lobby.Response;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public class IdentifyCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return IdentifyCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyIDENTIFY";

        private readonly string m_Name;


        public string Name
        {
            get { return m_Name; }
        } 


        public IdentifyCommand(StringTokenizer argsToken)
        {
            m_Name = argsToken.NextToken();
        }

        public IdentifyCommand(string name)
        {
            m_Name = name;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_Name);
        }

        public string EncodeResponse( bool success )
        {
            return new IdentifyResponse(this, success).Encode();
        }
    }
}
