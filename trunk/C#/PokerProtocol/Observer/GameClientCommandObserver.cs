using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Observer
{
    public class GameClientCommandObserver : TextCommandObserver
    {
        protected override char Delimitter { get { return AbstractCommand.Delimitter; } }

        public event EventHandler<CommandEventArgs<BetTurnEndedCommand>> BetTurnEndedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<BetTurnStartedCommand>> BetTurnStartedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GameEndedCommand>> GameEndedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GameStartedCommand>> GameStartedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerHoleCardsChangedCommand>> PlayerHoleCardsChangedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerJoinedCommand>> PlayerJoinedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerLeftCommand>> PlayerLeftCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerMoneyChangedCommand>> PlayerMoneyChangedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerTurnBeganCommand>> PlayerTurnBeganCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerTurnEndedCommand>> PlayerTurnEndedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<PlayerWonPotCommand>> PlayerWonPotCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<TableClosedCommand>> TableClosedCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<TableInfoCommand>> TableInfoCommandReceived = delegate { };
    }
}
