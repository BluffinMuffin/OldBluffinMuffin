using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Poker.Persistance;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Lobby;
using BluffinMuffin.Protocol.Commands.Lobby.Career;
using BluffinMuffin.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Protocol.Server
{
    public class BluffinLobbyWorker
    {
        private KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>[] Methods;

        public IBluffinServer Server { get; private set; }
        public IBluffinLobby Lobby { get; private set; }
        public BluffinLobbyWorker(IBluffinServer server, IBluffinLobby lobby)
        {
            Server = server;
            Lobby = lobby;
            Methods = new[]
            {
                //General
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(AbstractBluffinCommand), OnCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(DisconnectCommand), OnDisconnectCommandReceived), 
                
                //Lobby
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(ListTableCommand), OnListTableCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(SupportedRulesCommand), OnSupportedRulesCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(JoinTableCommand), OnJoinTableCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(CreateTableCommand), OnCreateTableCommandReceived), 
                
                //Lobby Training
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(IdentifyCommand), OnIdentifyCommandReceived), 

                //Lobby Career
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(CheckDisplayExistCommand), OnCheckDisplayExistCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(CheckUserExistCommand), OnCheckUserExistCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(CreateUserCommand), OnCreateUserCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(AuthenticateUserCommand), OnAuthenticateUserCommandReceived), 
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(GetUserCommand), OnGetUserCommandReceived), 
                
            };
        }

        public void Start()
        {
            foreach (CommandEntry<AbstractBluffinCommand> entry in Server.LobbyCommands.GetConsumingEnumerable())
                Methods.Where(x => entry.Command.GetType().IsSubclassOf(x.Key) || x.Key == entry.Command.GetType()).ToList().ForEach(x => x.Value(entry.Command, entry.Client));
        }

        private void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "ServerClientLobby.m_CommandObserver_CommandReceived", "Server RECV from {0} [{1}]", client.PlayerName, command.Encode());
        }

        void OnIdentifyCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (IdentifyCommand)command;
            client.PlayerName = c.Name;
            var ok = !Lobby.IsNameUsed(c.Name) && !DataManager.Persistance.IsDisplayNameExist(c.Name);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_IdentifyCommandReceived", "> Client indentifying training server as : {0}. Success={1}", c.Name, ok);
            client.SendCommand(c.Response(ok));
            if (ok)
                Lobby.AddName(c.Name);
        }

        void OnDisconnectCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (DisconnectCommand)command;
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_DisconnectCommandReceived", "> Client disconnected: {0}", client.PlayerName);
            Lobby.RemoveName(client.PlayerName);
        }

        void OnListTableCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (ListTableCommand)command;
            client.SendCommand(c.Response(Lobby.ListTables(c.LobbyTypes)));
        }

        private void OnSupportedRulesCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (SupportedRulesCommand)command;
            client.SendCommand(c.Response(RuleFactory.SupportedRules));
        }


        private void OnGetUserCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            throw new NotImplementedException();
        }

        private void OnAuthenticateUserCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            throw new NotImplementedException();
        }

        private void OnCreateUserCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            throw new NotImplementedException();
        }

        private void OnCheckUserExistCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            throw new NotImplementedException();
        }

        private void OnCheckDisplayExistCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            throw new NotImplementedException();
        }

        private void OnCreateTableCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (CreateTableCommand) command;
            client.SendCommand(c.Response(CreateTable(c,client)));
        }

        private int CreateTable(CreateTableCommand c, IBluffinClient client)
        {
            var res = Lobby.CreateTable(c);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_{3}Received", "> Client '{0}' {3}: {2}:{1}", client.PlayerName, c.Params.TableName, res, c.Params.Lobby.OptionType);
            return res;
        }

        private void OnJoinTableCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (JoinTableCommand)command;
            client.SendCommand(c.Response(false));
        }
    }
}
