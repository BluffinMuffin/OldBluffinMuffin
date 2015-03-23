using System;
using BluffinMuffin.Protocol.Server.Annotations;
using Com.Ericmas001.Net.Protocol.JSON;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Commands.Lobby.Training;
using BluffinMuffin.Protocol.Commands.Lobby.Career;
using BluffinMuffin.Protocol.Commands;
using Com.Ericmas001.Net.Protocol;

namespace BluffinMuffin.Protocol.Server
{
    public class LobbyObserver : JsonCommandObserver
    {
        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<DisconnectCommand>> DisconnectCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<ListTableCommand>> ListTableCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<JoinTableCommand>> JoinTableCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<GameCommand>> GameCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<SupportedRulesCommand>> SupportedRulesCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<CreateTableCommand>> CreateTableCommandReceived = delegate { };

        //Training
        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<IdentifyCommand>> IdentifyCommandReceived = delegate { };

        //Career
        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<CreateUserCommand>> CreateUserCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<CheckUserExistCommand>> CheckUserExistCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<CheckDisplayExistCommand>> CheckDisplayExistCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<AuthenticateUserCommand>> AuthenticateUserCommandReceived = delegate { };

        [UsedImplicitly]
        public event EventHandler<CommandEventArgs<GetUserCommand>> GetUserCommandReceived = delegate { };
    }
}
