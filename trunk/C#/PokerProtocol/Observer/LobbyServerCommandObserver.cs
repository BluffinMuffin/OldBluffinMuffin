using System;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;

namespace PokerProtocol.Observer
{
    public class LobbyServerCommandObserver : CommandObserver
    {
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CreateTableCommand>> CreateTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<ListTableCommand>> ListTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<JoinTableCommand>> JoinTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GameCommand>> GameCommandReceived = delegate { };

        //Training
        public event EventHandler<CommandEventArgs<IdentifyCommand>> IdentifyCommandReceived = delegate { };

        //Career
        public event EventHandler<CommandEventArgs<CreateUserCommand>> CreateUserCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CheckUserExistCommand>> CheckUserExistCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CheckDisplayExistCommand>> CheckDisplayExistCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<AuthenticateUserCommand>> AuthenticateUserCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GetUserCommand>> GetUserCommandReceived = delegate { };
        
        protected override void receiveSomething(string line)
        {
            StringTokenizer token = new StringTokenizer(line, AbstractLobbyCommand.Delimitter);
            string commandName = token.NextToken();
            
            if (commandName == DisconnectCommand.COMMAND_NAME)
                DisconnectCommandReceived(this, new CommandEventArgs<DisconnectCommand>(new DisconnectCommand(token)));
            else if (commandName == CreateTableCommand.COMMAND_NAME)
                CreateTableCommandReceived(this, new CommandEventArgs<CreateTableCommand>(new CreateTableCommand(token)));
            else if (commandName == ListTableCommand.COMMAND_NAME)
                ListTableCommandReceived(this, new CommandEventArgs<ListTableCommand>(new ListTableCommand(token)));
            else if (commandName == JoinTableCommand.COMMAND_NAME)
                JoinTableCommandReceived(this, new CommandEventArgs<JoinTableCommand>(new JoinTableCommand(token)));
            else if (commandName == GameCommand.COMMAND_NAME)
                GameCommandReceived(this, new CommandEventArgs<GameCommand>(new GameCommand(token)));

            //Training
            else if (commandName == IdentifyCommand.COMMAND_NAME)
                IdentifyCommandReceived(this, new CommandEventArgs<IdentifyCommand>(new IdentifyCommand(token)));

            //Career
            else if (commandName == CreateUserCommand.COMMAND_NAME)
                CreateUserCommandReceived(this, new CommandEventArgs<CreateUserCommand>(new CreateUserCommand(token)));
            else if (commandName == CheckUserExistCommand.COMMAND_NAME)
                CheckUserExistCommandReceived(this, new CommandEventArgs<CheckUserExistCommand>(new CheckUserExistCommand(token)));
            else if (commandName == CheckDisplayExistCommand.COMMAND_NAME)
                CheckDisplayExistCommandReceived(this, new CommandEventArgs<CheckDisplayExistCommand>(new CheckDisplayExistCommand(token)));
            else if (commandName == AuthenticateUserCommand.COMMAND_NAME)
                AuthenticateUserCommandReceived(this, new CommandEventArgs<AuthenticateUserCommand>(new AuthenticateUserCommand(token)));
            else if (commandName == GetUserCommand.COMMAND_NAME)
                GetUserCommandReceived(this, new CommandEventArgs<GetUserCommand>(new GetUserCommand(token)));
            
        }
    }
}
