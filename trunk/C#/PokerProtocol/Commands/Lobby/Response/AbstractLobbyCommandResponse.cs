using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Response
{
    public abstract class AbstractLobbyCommandResponse<T>: AbstractCommandResponse<T>
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

        public AbstractLobbyCommandResponse(T command) : base(command)
        {
        }

    }
}
