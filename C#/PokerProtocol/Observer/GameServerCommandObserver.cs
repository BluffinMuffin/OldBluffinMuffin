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
    public class GameServerCommandObserver : CommandObserver
    {
        public event EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>> PlayMoneyCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };

        protected override void receiveSomething(string line)
        {
            StringTokenizer token = new StringTokenizer(line, AbstractCommand.Delimitter);
            string commandName = token.NextToken();
            if (commandName == PlayerPlayMoneyCommand.COMMAND_NAME)
                PlayMoneyCommandReceived(this, new CommandEventArgs<PlayerPlayMoneyCommand>(new PlayerPlayMoneyCommand(token)));
            else if (commandName == DisconnectCommand.COMMAND_NAME)
                DisconnectCommandReceived(this, new CommandEventArgs<DisconnectCommand>(new DisconnectCommand(token)));
        }
    }
}
