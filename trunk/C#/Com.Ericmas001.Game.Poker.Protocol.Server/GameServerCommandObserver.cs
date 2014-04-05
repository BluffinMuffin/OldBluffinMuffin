using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Game;
using EricUtility.Networking.Commands;

namespace Com.Ericmas001.Game.Poker.Protocol.Server
{
    public class GameServerCommandObserver : JsonCommandObserver
    {
        public event EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>> PlayMoneyCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
    }
}
