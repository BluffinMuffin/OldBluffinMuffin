using System;
using Com.Ericmas001.Game.Poker.DataTypes.Annotations;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Game;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Net.Protocol;

namespace Com.Ericmas001.Game.Poker.Protocol.Server
{
    public class GameObserver : JsonCommandObserver
    {
        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerSitInCommand>> SitInCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerSitOutCommand>> SitOutCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>> PlayMoneyCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
    }
}
