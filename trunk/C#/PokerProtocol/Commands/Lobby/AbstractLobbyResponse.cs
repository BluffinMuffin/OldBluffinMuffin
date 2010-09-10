using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public abstract class AbstractLobbyResponse<T>: AbstractCommandResponse<T>
        where T : AbstractCommand
    {

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

        public AbstractLobbyResponse(T command) : base(command)
        {
        }

    }
}
