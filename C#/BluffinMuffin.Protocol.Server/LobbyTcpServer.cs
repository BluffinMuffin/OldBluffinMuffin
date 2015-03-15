using System;
using System.Collections.Generic;
using System.Net.Sockets;
using BluffinMuffin.Protocol.Commands.Lobby;
using System.IO;
using BluffinMuffin.Protocol.Commands.Lobby.Training;
using BluffinMuffin.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Util;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;
using Com.Ericmas001.Net.Protocol;
using BluffinMuffin.Poker.Persistance;


namespace BluffinMuffin.Protocol.Server
{
    public class LobbyTcpServer : CommandTcpCommunicator<LobbyObserver>
    {
        private string m_PlayerName = "?";
        private readonly IServerLobby m_Lobby;
        readonly Dictionary<int, GameTcpServer> m_Tables = new Dictionary<int, GameTcpServer>();

        public LobbyTcpServer(TcpClient client, IServerLobby lobby)
            : base(client)
        {
            m_Lobby = lobby;
        }
        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += m_CommandObserver_CommandReceived;
            m_CommandObserver.DisconnectCommandReceived += m_CommandObserver_DisconnectCommandReceived;
            m_CommandObserver.ListTableCommandReceived += m_CommandObserver_ListTableCommandReceived;
            m_CommandObserver.JoinTableCommandReceived += m_CommandObserver_JoinTableCommandReceived;
            m_CommandObserver.GameCommandReceived += m_CommandObserver_GameCommandReceived;
            m_CommandObserver.SupportedRulesCommandReceived += m_CommandObserver_SupportedRulesCommandReceived;
            m_CommandObserver.CreateTableCommandReceived += m_CommandObserver_CreateTableCommandReceived;

            //Training
            m_CommandObserver.IdentifyCommandReceived += m_CommandObserver_IdentifyCommandReceived;
            
            //Career
            m_CommandObserver.CheckDisplayExistCommandReceived += m_CommandObserver_CheckDisplayExistCommandReceived;
            m_CommandObserver.CheckUserExistCommandReceived += m_CommandObserver_CheckUserExistCommandReceived;
            m_CommandObserver.CreateUserCommandReceived += m_CommandObserver_CreateUserCommandReceived;
            m_CommandObserver.AuthenticateUserCommandReceived += m_CommandObserver_AuthenticateUserCommandReceived;
            m_CommandObserver.GetUserCommandReceived += m_CommandObserver_GetUserCommandReceived;
        }

        void m_CommandObserver_CreateTableCommandReceived(object sender, CommandEventArgs<CreateTableCommand> e)
        {
            Send(e.Command.EncodeResponse(CreateTable(e.Command)));
        }

        void m_CommandObserver_SupportedRulesCommandReceived(object sender, CommandEventArgs<SupportedRulesCommand> e)
        {
            Send(e.Command.EncodeResponse(RuleFactory.SupportedRules));
        }

        private int CreateTable(CreateTableCommand c)
        {
            var res = m_Lobby.CreateTable(c);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_{3}Received", "> Client '{0}' {3}: {2}:{1}", m_PlayerName, c.Params.TableName, res, c.Params.Lobby.OptionType);
            return res;
        }

        void m_CommandObserver_GetUserCommandReceived(object sender, CommandEventArgs<GetUserCommand> e)
        {
            var u = DataManager.Persistance.Get(e.Command.Username);
            Send(e.Command.EncodeResponse(u.Email,u.DisplayName,u.TotalMoney));
        }

        void m_CommandObserver_CheckDisplayExistCommandReceived(object sender, CommandEventArgs<CheckDisplayExistCommand> e)
        {
            Send(e.Command.EncodeResponse(m_Lobby.NameUsed(e.Command.DisplayName) || DataManager.Persistance.IsDisplayNameExist(e.Command.DisplayName)));
        }

        void m_CommandObserver_AuthenticateUserCommandReceived(object sender, CommandEventArgs<AuthenticateUserCommand> e)
        {
            var u = DataManager.Persistance.Authenticate(e.Command.Username, e.Command.Password);

            if (u != null)
                m_PlayerName = u.DisplayName;

            var ok = (u != null && !m_Lobby.NameUsed(m_PlayerName));
            if( ok )
                m_Lobby.AddName(m_PlayerName);

            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_AuthenticateUserCommandReceived", "> Client authenticate to Career Server as : {0}. Success={1}", m_PlayerName, ok);
            Send(e.Command.EncodeResponse(ok));
        }

        void m_CommandObserver_CreateUserCommandReceived(object sender, CommandEventArgs<CreateUserCommand> e)
        {
            var c = e.Command;
            var ok = !DataManager.Persistance.IsUsernameExist(c.Username) && !DataManager.Persistance.IsDisplayNameExist(e.Command.DisplayName);

            if( ok)
                DataManager.Persistance.Register(new UserInfo(c.Username,c.Password,c.Email,c.DisplayName,7500));

            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_CreateUserCommandReceived", "> Client register to Career Server as : {0}. Success={1}", c.Username, ok);
            Send(e.Command.EncodeResponse(ok));
        }

        void m_CommandObserver_CheckUserExistCommandReceived(object sender, CommandEventArgs<CheckUserExistCommand> e)
        {
            Send(e.Command.EncodeResponse(DataManager.Persistance.IsUsernameExist(e.Command.Username)));
        }

        void m_CommandObserver_GameCommandReceived(object sender, CommandEventArgs<GameCommand> e)
        {
            m_Tables[e.Command.TableId].Incoming(e.Command.DecodedCommand);
        }

        void m_CommandObserver_JoinTableCommandReceived(object sender, CommandEventArgs<JoinTableCommand> e)
        {
            GameTcpServer client;
            var game = m_Lobby.GetGame(e.Command.TableId);
            var table = game.GameTable;

            if (game.Params.Lobby.OptionType == LobbyTypeEnum.Training)
                client = new GameTcpServer(e.Command.TableId, game, m_PlayerName);
            else
                client = new GameTcpServer(e.Command.TableId, game, DataManager.Persistance.Get(e.Command.PlayerName));

            client.LeftTable += client_LeftTable;
            client.SendedSomething += client_SendedSomething;

            if (!game.IsRunning)
            {
                Send(e.Command.EncodeResponse(false));
                return;
            }

            // Verify the player does not already playing on that table.
            if (!table.ContainsPlayer(e.Command.PlayerName))
            {
                var ok = client.JoinGame();
                if (!ok)
                    Send(e.Command.EncodeResponse(false));
                else
                {
                    m_Tables.Add(e.Command.TableId, client);
                    client.Start();

                    LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_JoinTableCommandReceived", "> Client '{0}' joined {2}:{1}", m_PlayerName, table.Params.TableName, e.Command.TableId, client.Player.NoSeat);
                    Send(e.Command.EncodeResponse(true));

                    client.SendTableInfo();
                }
            }
            else
            {
                Send(e.Command.EncodeResponse(false));
            }
        }

        void client_LeftTable(object sender, KeyEventArgs<int> e)
        {
            m_Tables.Remove(e.Key);
        }

        void client_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            var client = (GameTcpServer)sender;
            Send(new GameCommand() { TableId = client.Id, EncodedCommand = e.Key });
        }
        public override void OnReceiveCrashed(Exception e)
        {
            if (e is IOException)
            {
                LogManager.Log(LogLevel.Error, "ServerClientLobby.OnReceiveCrashed", "Server lost connection with {0}", m_PlayerName);
                m_Lobby.RemoveName(m_PlayerName);
                Close();
            }
            else
                base.OnReceiveCrashed(e);
        }

        protected override void Send(string line)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "ServerClientLobby.Send", "Server SEND to {0} [{1}]", m_PlayerName, line);
            base.Send(line);
        }

        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "ServerClientLobby.m_CommandObserver_CommandReceived", "Server RECV from {0} [{1}]", m_PlayerName, e.Str);
        }

        void m_CommandObserver_IdentifyCommandReceived(object sender, CommandEventArgs<IdentifyCommand> e)
        {
            var c = e.Command;
            m_PlayerName = c.Name;
            var ok = !m_Lobby.NameUsed(m_PlayerName) && !DataManager.Persistance.IsDisplayNameExist(m_PlayerName);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_IdentifyCommandReceived", "> Client indentifying training server as : {0}. Success={1}", m_PlayerName, ok);
            Send(c.EncodeResponse(ok));
            if (ok)
                m_Lobby.AddName(m_PlayerName);
        }

        void m_CommandObserver_DisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_DisconnectCommandReceived", "> Client disconnected: {0}", m_PlayerName);
            m_Lobby.RemoveName(m_PlayerName);
            Close();
        }

        void m_CommandObserver_ListTableCommandReceived(object sender, CommandEventArgs<ListTableCommand> e)
        {
            var c = e.Command;
            Send(c.EncodeResponse(m_Lobby.ListTables(c.LobbyTypes)));
        }
    }
}
