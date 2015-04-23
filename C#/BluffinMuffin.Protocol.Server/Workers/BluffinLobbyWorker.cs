using System;
using System.Collections.Generic;
using System.Linq;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Poker.Persistance;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.Lobby;
using BluffinMuffin.Protocol.Lobby.Career;
using BluffinMuffin.Protocol.Lobby.Training;
using BluffinMuffin.Protocol.Server.DataTypes;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Protocol.Server.Workers
{
    public class BluffinLobbyWorker
    {
        private readonly KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>[] m_Methods;

        private IBluffinServer Server { get; set; }
        private IBluffinLobby Lobby { get; set; }
        public BluffinLobbyWorker(IBluffinServer server, IBluffinLobby lobby)
        {
            Server = server;
            Lobby = lobby;
            m_Methods = new[]
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
                new KeyValuePair<Type, Action<AbstractBluffinCommand, IBluffinClient>>(typeof(GetUserCommand), OnGetUserCommandReceived)
                
            };
        }

        public void Start()
        {
            foreach (CommandEntry entry in Server.LobbyCommands.GetConsumingEnumerable())
            {
                CommandEntry e = entry;
                m_Methods.Where(x => e.Command.GetType().IsSubclassOf(x.Key) || x.Key == e.Command.GetType()).ToList().ForEach(x => x.Value(e.Command, e.Client));
            }
        }

        private void OnCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "BluffinLobbyWorker.OnCommandReceived", "LobbyWorker RECV from {0} [{1}]", client.PlayerName, command.Encode());
            LogManager.Log(LogLevel.MessageVeryLow, "BluffinLobbyWorker.OnCommandReceived", "-------------------------------------------");
        }

        void OnIdentifyCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (IdentifyCommand)command;
            client.PlayerName = c.Name;
            var ok = !Lobby.IsNameUsed(c.Name) && !DataManager.Persistance.IsDisplayNameExist(c.Name);
            LogManager.Log(LogLevel.Message, "BluffinLobbyWorker.OnIdentifyCommandReceived", "> Client indentifying training server as : {0}. Success={1}", c.Name, ok);
            client.SendCommand(c.Response(ok));
            if (ok)
                Lobby.AddName(c.Name);
        }

        void OnDisconnectCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            LogManager.Log(LogLevel.Message, "BluffinLobbyWorker.OnDisconnectCommandReceived", "> Client disconnected: {0}", client.PlayerName);
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
            var c = (GetUserCommand)command;
            var u = DataManager.Persistance.Get(c.Username);
            client.SendCommand(c.Response(u == null ? String.Empty : u.Email, u == null ? String.Empty : u.DisplayName, u == null ? -1 : u.TotalMoney));
        }

        private void OnAuthenticateUserCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (AuthenticateUserCommand)command;
            var u = DataManager.Persistance.Authenticate(c.Username, c.Password);

            if (u != null)
                client.PlayerName = u.DisplayName;

            var ok = (u != null && !Lobby.IsNameUsed(client.PlayerName));
            if (ok)
                Lobby.AddName(client.PlayerName);

            LogManager.Log(LogLevel.Message, "BluffinLobbyWorker.OnAuthenticateUserCommandReceived", "> Client authenticate to Career Server as : {0}. Success={1}", client.PlayerName, ok);
            client.SendCommand(c.Response(ok));
        }

        private void OnCreateUserCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (CreateUserCommand)command;
            var ok = !DataManager.Persistance.IsUsernameExist(c.Username) && !DataManager.Persistance.IsDisplayNameExist(c.DisplayName);

            if (ok)
                DataManager.Persistance.Register(new UserInfo(c.Username, c.Password, c.Email, c.DisplayName, 7500));

            LogManager.Log(LogLevel.Message, "BluffinLobbyWorker.OnCreateUserCommandReceived", "> Client register to Career Server as : {0}. Success={1}", c.Username, ok);
            client.SendCommand(c.Response(ok));
        }

        private void OnCheckUserExistCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (CheckUserExistCommand)command;
            client.SendCommand(c.Response(DataManager.Persistance.IsUsernameExist(c.Username)));
        }

        private void OnCheckDisplayExistCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (CheckDisplayExistCommand)command;
            client.SendCommand(c.Response(Lobby.IsNameUsed(c.DisplayName) || DataManager.Persistance.IsDisplayNameExist(c.DisplayName)));
        }

        private void OnCreateTableCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (CreateTableCommand) command;
            client.SendCommand(c.Response(CreateTable(c,client)));
        }

        private int CreateTable(CreateTableCommand c, IBluffinClient client)
        {
            var res = Lobby.CreateTable(c);
            LogManager.Log(LogLevel.Message, "BluffinLobbyWorker.CreateTable_{3}", "> Client '{0}' {3}: {2}:{1}", client.PlayerName, c.Params.TableName, res, c.Params.Lobby.OptionType);
            return res;
        }

        private void OnJoinTableCommandReceived(AbstractBluffinCommand command, IBluffinClient client)
        {
            var c = (JoinTableCommand)command;
            var game = Lobby.GetGame(c.TableId);
            var table = game.GameTable;
            if (!game.IsRunning || table.ContainsPlayer(c.PlayerName))
            {
                client.SendCommand(c.Response(false));
                return;
            }
            var rp = new RemotePlayer(game, new PlayerInfo(c.PlayerName, 0), client, c.TableId);
            if (!rp.JoinGame())
            {
                client.SendCommand(c.Response(false));
                return;
            }

            client.AddPlayer(rp);

            LogManager.Log(LogLevel.Message, "BluffinLobbyWorker.OnJoinTableCommandReceived", "> Client '{0}' joined {2}:{1}", client.PlayerName, table.Params.TableName, c.TableId, rp.Player.NoSeat);
            client.SendCommand(c.Response(true));

            rp.SendTableInfo();
        }
    }
}
