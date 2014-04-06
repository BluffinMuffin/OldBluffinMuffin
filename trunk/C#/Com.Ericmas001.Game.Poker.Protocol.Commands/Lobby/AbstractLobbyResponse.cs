using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

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
