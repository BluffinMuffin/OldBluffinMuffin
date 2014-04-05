using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public abstract class AbstractLobbyResponse<T>: AbstractJsonCommandResponse<T>
        where T : AbstractLobbyCommand
    {
        public AbstractLobbyResponse()
            : base()
        {
        }
        public AbstractLobbyResponse(T command)
            : base(command)
        {
        }
    }
}
