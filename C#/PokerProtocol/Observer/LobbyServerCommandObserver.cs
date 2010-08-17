using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Lobby;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Observer
{
    public class LobbyServerCommandObserver : CommandObserver
    {
        public event EventHandler<CommandEventArgs<IdentifyCommand>> IdentifyCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CreateTableCommand>> CreateTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<ListTableCommand>> ListTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<JoinTableCommand>> JoinTableCommandReceived = delegate { };

        protected override void receiveSomething(string line)
        {
            StringTokenizer token = new StringTokenizer(line, AbstractCommand.Delimitter);
            string commandName = token.NextToken();
            if( commandName == IdentifyCommand.COMMAND_NAME )
                IdentifyCommandReceived(this, new CommandEventArgs<IdentifyCommand>(new IdentifyCommand(token)));
            else if (commandName == DisconnectCommand.COMMAND_NAME)
                DisconnectCommandReceived(this, new CommandEventArgs<DisconnectCommand>(new DisconnectCommand(token)));
            else if (commandName == CreateTableCommand.COMMAND_NAME)
                CreateTableCommandReceived(this, new CommandEventArgs<CreateTableCommand>(new CreateTableCommand(token)));
            else if (commandName == ListTableCommand.COMMAND_NAME)
                ListTableCommandReceived(this, new CommandEventArgs<ListTableCommand>(new ListTableCommand(token)));
            else if (commandName == JoinTableCommand.COMMAND_NAME)
                JoinTableCommandReceived(this, new CommandEventArgs<JoinTableCommand>(new JoinTableCommand(token)));
        }
    }
}
