﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Observer
{
    public class GameServerCommandObserver : CommandObserver
    {
        protected override char Delimitter { get { return AbstractCommand.Delimitter; } }

        public event EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>> PlayMoneyCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
    }
}