using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Observer
{
    public class GameServerCommandObserver : TextCommandObserver
    {
        protected override char Delimitter { get { return AbstractTextCommand.Delimitter; } }

        public event EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>> PlayMoneyCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<DisconnectTextCommand>> DisconnectCommandReceived = delegate { };
    }
}
