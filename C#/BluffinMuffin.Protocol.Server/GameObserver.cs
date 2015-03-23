using System;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Game;
using BluffinMuffin.Protocol.Server.Annotations;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Server
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
