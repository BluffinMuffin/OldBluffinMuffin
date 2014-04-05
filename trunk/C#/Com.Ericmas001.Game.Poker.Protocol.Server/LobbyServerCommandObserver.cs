using System;
using EricUtility;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using System.Reflection;
using Com.Ericmas001.Game.Poker.Protocol.Commands;

namespace Com.Ericmas001.Game.Poker.Protocol.Server
{
    public class LobbyServerCommandObserver : JsonCommandObserver
    {
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<ListTableCommand>> ListTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<JoinTableCommand>> JoinTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GameCommand>> GameCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<SupportedRulesCommand>> SupportedRulesCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CreateTableCommand>> CreateTableCommandReceived = delegate { };

        //Training
        //public event EventHandler<CommandEventArgs<CreateTrainingTableCommand>> CreateTrainingTableCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<IdentifyCommand>> IdentifyCommandReceived = delegate { };

        //Career
        public event EventHandler<CommandEventArgs<CreateUserCommand>> CreateUserCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CheckUserExistCommand>> CheckUserExistCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<CheckDisplayExistCommand>> CheckDisplayExistCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<AuthenticateUserCommand>> AuthenticateUserCommandReceived = delegate { };
        public event EventHandler<CommandEventArgs<GetUserCommand>> GetUserCommandReceived = delegate { };
    }
}
