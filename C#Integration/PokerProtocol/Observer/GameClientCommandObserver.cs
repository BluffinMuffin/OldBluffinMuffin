using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Observer
{
    public class GameClientCommandObserver : CommandObserver
    {
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

        protected override void receiveSomething(string line)
        {
            StringTokenizer token = new StringTokenizer(line, AbstractCommand.Delimitter);
            string commandName = token.NextToken();
            if (commandName == BetTurnEndedCommand.COMMAND_NAME)
                BetTurnEndedCommandReceived(this, new CommandEventArgs<BetTurnEndedCommand>(new BetTurnEndedCommand(token)));
            else if (commandName == BetTurnStartedCommand.COMMAND_NAME)
                BetTurnStartedCommandReceived(this, new CommandEventArgs<BetTurnStartedCommand>(new BetTurnStartedCommand(token)));
            else if (commandName == GameEndedCommand.COMMAND_NAME)
                GameEndedCommandReceived(this, new CommandEventArgs<GameEndedCommand>(new GameEndedCommand(token)));
            else if (commandName == GameStartedCommand.COMMAND_NAME)
                GameStartedCommandReceived(this, new CommandEventArgs<GameStartedCommand>(new GameStartedCommand(token)));
            else if (commandName == PlayerHoleCardsChangedCommand.COMMAND_NAME)
                PlayerHoleCardsChangedCommandReceived(this, new CommandEventArgs<PlayerHoleCardsChangedCommand>(new PlayerHoleCardsChangedCommand(token)));
            else if (commandName == PlayerJoinedCommand.COMMAND_NAME)
                PlayerJoinedCommandReceived(this, new CommandEventArgs<PlayerJoinedCommand>(new PlayerJoinedCommand(token)));
            else if (commandName == PlayerLeftCommand.COMMAND_NAME)
                PlayerLeftCommandReceived(this, new CommandEventArgs<PlayerLeftCommand>(new PlayerLeftCommand(token)));
            else if (commandName == PlayerMoneyChangedCommand.COMMAND_NAME)
                PlayerMoneyChangedCommandReceived(this, new CommandEventArgs<PlayerMoneyChangedCommand>(new PlayerMoneyChangedCommand(token)));
            else if (commandName == PlayerTurnBeganCommand.COMMAND_NAME)
                PlayerTurnBeganCommandReceived(this, new CommandEventArgs<PlayerTurnBeganCommand>(new PlayerTurnBeganCommand(token)));
            else if (commandName == PlayerTurnEndedCommand.COMMAND_NAME)
                PlayerTurnEndedCommandReceived(this, new CommandEventArgs<PlayerTurnEndedCommand>(new PlayerTurnEndedCommand(token)));
            else if (commandName == PlayerWonPotCommand.COMMAND_NAME)
                PlayerWonPotCommandReceived(this, new CommandEventArgs<PlayerWonPotCommand>(new PlayerWonPotCommand(token)));
            else if (commandName == TableClosedCommand.COMMAND_NAME)
                TableClosedCommandReceived(this, new CommandEventArgs<TableClosedCommand>(new TableClosedCommand(token)));
            else if (commandName == TableInfoCommand.COMMAND_NAME)
                TableInfoCommandReceived(this, new CommandEventArgs<TableInfoCommand>(new TableInfoCommand(token)));
        }
    }
}
