using System;
using BluffinMuffin.Poker.DataTypes.Annotations;
using BluffinMuffin.Protocol.Game;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Client
{
    public class GameObserver : JsonCommandObserver
    {
        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<BetTurnEndedCommand>> BetTurnEndedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<BetTurnStartedCommand>> BetTurnStartedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<GameEndedCommand>> GameEndedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<GameStartedCommand>> GameStartedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerHoleCardsChangedCommand>> PlayerHoleCardsChangedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerJoinedCommand>> PlayerJoinedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<SeatUpdatedCommand>> SeatUpdatedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerMoneyChangedCommand>> PlayerMoneyChangedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerTurnBeganCommand>> PlayerTurnBeganCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerTurnEndedCommand>> PlayerTurnEndedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerWonPotCommand>> PlayerWonPotCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<TableClosedCommand>> TableClosedCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<TableInfoCommand>> TableInfoCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerSitInResponse>> PlayerSitInResponseReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<PlayerSitOutResponse>> PlayerSitOutResponseReceived = delegate { };
    }
}
