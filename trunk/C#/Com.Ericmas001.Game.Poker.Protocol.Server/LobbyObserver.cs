using System;
using Com.Ericmas001.Game.Poker.DataTypes.Annotations;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Net.Protocol;

namespace Com.Ericmas001.Game.Poker.Protocol.Server
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
