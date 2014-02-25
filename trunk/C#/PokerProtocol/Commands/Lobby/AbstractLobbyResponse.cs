using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public abstract class AbstractLobbyResponse<T>: AbstractTextCommandResponse<T>
        where T : AbstractLobbyCommand
    {
        protected override void Append<T2>(StringBuilder sb, T2 thing)
        {
            sb.Append(thing);
            sb.Append(AbstractLobbyCommand.Delimitter);
        }

        public AbstractLobbyResponse(T command) : base(command)
        {
        }

    }
}
