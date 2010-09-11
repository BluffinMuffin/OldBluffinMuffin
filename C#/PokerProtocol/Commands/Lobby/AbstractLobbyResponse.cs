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
