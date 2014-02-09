using System;
using EricUtility;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby;
using PokerProtocol.Commands.Lobby.Training;
using PokerProtocol.Commands.Lobby.Career;
using System.Reflection;

namespace PokerProtocol.Observer
{
    public class LobbyServerCommandObserver : CommandObserver
    {
        protected override char Delimitter { get { return AbstractLobbyCommand.Delimitter; } }

        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<ListTableCommand>> ListTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<JoinTableCommand>> JoinTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GameCommand>> GameCommandReceived = delegate { };

        //Training
        public event EventHandler<CommandEventArgs<CreateTrainingTableCommand>> CreateTrainingTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<IdentifyCommand>> IdentifyCommandReceived = delegate { };

        //Career
        public event EventHandler<CommandEventArgs<CreateCareerTableCommand>> CreateCareerTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CreateUserCommand>> CreateUserCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CheckUserExistCommand>> CheckUserExistCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CheckDisplayExistCommand>> CheckDisplayExistCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<AuthenticateUserCommand>> AuthenticateUserCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GetUserCommand>> GetUserCommandReceived = delegate { };

    }
}
