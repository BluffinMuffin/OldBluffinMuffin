using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Lobby;

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
            switch (commandName)
            {
                case IdentifyCommand.COMMAND_NAME: IdentifyCommandReceived(this, new CommandEventArgs<IdentifyCommand>(new IdentifyCommand(token))); break;
                case DisconnectCommand.COMMAND_NAME: IdentifyCommandReceived(this, new CommandEventArgs<DisconnectCommand>(new DisconnectCommand(token))); break;
                case CreateTableCommand.COMMAND_NAME: IdentifyCommandReceived(this, new CommandEventArgs<CreateTableCommand>(new CreateTableCommand(token))); break;
                case ListTableCommand.COMMAND_NAME: IdentifyCommandReceived(this, new CommandEventArgs<ListTableCommand>(new ListTableCommand(token))); break;
                case JoinTableCommand.COMMAND_NAME: IdentifyCommandReceived(this, new CommandEventArgs<JoinTableCommand>(new JoinTableCommand(token))); break;
            }
        }
    }
}
