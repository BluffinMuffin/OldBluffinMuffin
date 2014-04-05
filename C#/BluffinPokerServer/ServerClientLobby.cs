using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Networking.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Server;
using System.Net.Sockets;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using System.IO;
using PokerWorld.Game;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Persistance;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using EricUtility;
using System.Web;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using PokerWorld.Game.Rules;


namespace BluffinPokerServer
{
    public class ServerClientLobby : CommandTCPCommunicator<LobbyServerCommandObserver>
    {
        private string m_PlayerName = "?";
        private readonly ServerLobby m_Lobby;
        Dictionary<int, GameServer> m_Tables = new Dictionary<int, GameServer>();

        public ServerClientLobby(TcpClient client, ServerLobby lobby)
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
            Send(e.Command.EncodeResponse());
        }

        private int CreateTable(CreateTableCommand c)
        {
            int res = m_Lobby.CreateTable(c);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_{3}Received", "> Client '{0}' {3}: {2}:{1}", m_PlayerName, c.GameRules.TableName, res, c.GameRules.CurrentLobby.LobbyType);
            return res;
        }

        void m_CommandObserver_GetUserCommandReceived(object sender, CommandEventArgs<GetUserCommand> e)
        {
            UserInfo u = DataManager.Persistance.Get(e.Command.Username);
            Send(e.Command.EncodeResponse(u.Email,u.DisplayName,u.TotalMoney));
        }

        void m_CommandObserver_CheckDisplayExistCommandReceived(object sender, CommandEventArgs<CheckDisplayExistCommand> e)
        {
            Send(e.Command.EncodeResponse(m_Lobby.NameUsed(e.Command.DisplayName) || DataManager.Persistance.IsDisplayNameExist(e.Command.DisplayName)));
        }

        void m_CommandObserver_AuthenticateUserCommandReceived(object sender, CommandEventArgs<Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career.AuthenticateUserCommand> e)
        {
            UserInfo u = DataManager.Persistance.Authenticate(e.Command.Username, e.Command.Password);

            if (u != null)
                m_PlayerName = u.DisplayName;

            bool ok = (u != null && !m_Lobby.NameUsed(m_PlayerName));
            if( ok )
                m_Lobby.AddName(m_PlayerName);

            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_AuthenticateUserCommandReceived", "> Client authenticate to Career Server as : {0}. Success={1}", m_PlayerName, ok);
            Send(e.Command.EncodeResponse(ok));
        }

        void m_CommandObserver_CreateUserCommandReceived(object sender, CommandEventArgs<Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career.CreateUserCommand> e)
        {
            CreateUserCommand c = e.Command;
            bool ok = !DataManager.Persistance.IsUsernameExist(c.Username) && !DataManager.Persistance.IsDisplayNameExist(e.Command.DisplayName);

            if( ok)
                DataManager.Persistance.Register(new UserInfo(c.Username,c.Password,c.Email,c.DisplayName,7500));

            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_CreateUserCommandReceived", "> Client register to Career Server as : {0}. Success={1}", c.Username, ok);
            Send(e.Command.EncodeResponse(ok));
        }

        void m_CommandObserver_CheckUserExistCommandReceived(object sender, CommandEventArgs<Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career.CheckUserExistCommand> e)
        {
            Send(e.Command.EncodeResponse(DataManager.Persistance.IsUsernameExist(e.Command.Username)));
        }

        void m_CommandObserver_GameCommandReceived(object sender, CommandEventArgs<GameCommand> e)
        {
            m_Tables[e.Command.TableID].Incoming(e.Command.DecodedCommand);
        }

        void m_CommandObserver_JoinTableCommandReceived(object sender, CommandEventArgs<JoinTableCommand> e)
        {
            GameServer client = null;
            PokerGame game = m_Lobby.GetGame(e.Command.TableID);
            PokerTable table = game.Table;

            if (game.Rules.CurrentLobby.LobbyType == LobbyTypeEnum.Training)
                client = new GameServer(e.Command.TableID, game, m_PlayerName, ((LobbyOptionsTraining)game.Rules.CurrentLobby).StartingAmount);
            else
                client = new GameServer(e.Command.TableID, game, DataManager.Persistance.Get(e.Command.PlayerName));

            client.LeftTable += new EventHandler<EricUtility.KeyEventArgs<int>>(client_LeftTable);
            client.SendedSomething += new EventHandler<EricUtility.KeyEventArgs<string>>(client_SendedSomething);

            if (!game.IsRunning)
            {
                Send(e.Command.EncodeErrorResponse());
                return;
            }

            // Verify the player does not already playing on that table.
            if (!table.ContainsPlayer(e.Command.PlayerName))
            {
                bool ok = client.JoinGame();
                if (!ok)
                    Send(e.Command.EncodeErrorResponse());
                else
                {
                    m_Tables.Add(e.Command.TableID, client);
                    client.Start();

                    LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_JoinTableCommandReceived", "> Client '{0}' seated ({3}) at table: {2}:{1}", m_PlayerName, table.Rules.TableName, e.Command.TableID, client.Player.NoSeat);
                    Send(e.Command.EncodeResponse(client.Player.NoSeat));

                    client.SitIn();
                }
            }
            else
            {
                Send(e.Command.EncodeErrorResponse());
            }
        }

        void client_LeftTable(object sender, EricUtility.KeyEventArgs<int> e)
        {
            m_Tables.Remove(e.Key);
        }

        void client_SendedSomething(object sender, EricUtility.KeyEventArgs<string> e)
        {
            GameServer client = (GameServer)sender;
            Send(new GameCommand() { TableID = client.ID, EncodedCommand = e.Key });
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
            IdentifyCommand c = e.Command;
            m_PlayerName = c.Name;
            bool ok = !m_Lobby.NameUsed(m_PlayerName) && !DataManager.Persistance.IsDisplayNameExist(m_PlayerName);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_IdentifyCommandReceived", "> Client indentifying training server as : {0}. Success={1}", m_PlayerName, ok);
            Send(c.EncodeResponse(ok));
            if (ok)
                m_Lobby.AddName(m_PlayerName);
        }

        void m_CommandObserver_DisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_DisconnectCommandReceived", "> Client disconnected: {0}", m_PlayerName);
            DisconnectCommand c = e.Command;
            m_Lobby.RemoveName(m_PlayerName);
            Close();
        }

        void m_CommandObserver_ListTableCommandReceived(object sender, CommandEventArgs<ListTableCommand> e)
        {
            ListTableCommand c = e.Command;
            Send(c.EncodeResponse(m_Lobby.ListTables(c.LobbyTypes)));
        }
    }
}
