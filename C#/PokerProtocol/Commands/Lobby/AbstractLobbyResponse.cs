﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public abstract class AbstractLobbyResponse<T>: AbstractJsonCommandResponse<T>
        where T : AbstractLobbyCommand
    {
        public AbstractLobbyResponse(T command) : base(command)
        {
        }
    }
}