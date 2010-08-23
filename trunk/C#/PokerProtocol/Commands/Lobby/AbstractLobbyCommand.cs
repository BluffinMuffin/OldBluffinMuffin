using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public abstract class AbstractLobbyCommand : AbstractCommand
    {
        public static new char Delimitter { get { return '|'; } }

        protected override void Append(StringBuilder sb, string s)
        {
            sb.Append(s);
            sb.Append(AbstractLobbyCommand.Delimitter);
        }
        protected override void Append(StringBuilder sb, bool b)
        {
            sb.Append(b);
            sb.Append(AbstractLobbyCommand.Delimitter);
        }
        protected override void Append(StringBuilder sb, int i)
        {
            sb.Append(i);
            sb.Append(AbstractLobbyCommand.Delimitter);
        }
    }
}
