using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Networking.Commands;
using PokerProtocol.Observer;
using System.Net.Sockets;
using PokerProtocol.Commands.Lobby;
using System.IO;
using PokerProtocol;
using PokerWorld.Game;
using PokerProtocol.Commands.Lobby.Training;
using PokerWorld.Data;
using PokerProtocol.Commands.Lobby.Career;
using EricUtility;


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
            m_CommandObserver.CommandReceived += new EventHandler<StringEventArgs>(m_CommandObserver_CommandReceived);
            m_CommandObserver.DisconnectCommandReceived += new EventHandler<CommandEventArgs<DisconnectTextCommand>>(m_CommandObserver_DisconnectCommandReceived);
            m_CommandObserver.ListTableCommandReceived += new EventHandler<CommandEventArgs<ListTableCommand>>(m_CommandObserver_ListTableCommandReceived);
            m_CommandObserver.JoinTableCommandReceived += new EventHandler<CommandEventArgs<JoinTableCommand>>(m_CommandObserver_JoinTableCommandReceived);
            m_CommandObserver.GameCommandReceived += new EventHandler<CommandEventArgs<GameCommand>>(m_CommandObserver_GameCommandReceived);

            //Training
            m_CommandObserver.CreateTrainingTableCommandReceived += new EventHandler<CommandEventArgs<CreateTrainingTableCommand>>(m_CommandObserver_CreateTrainingTableCommandReceived);
            m_CommandObserver.IdentifyCommandReceived += new EventHandler<CommandEventArgs<IdentifyCommand>>(m_CommandObserver_IdentifyCommandReceived);
            
            //Career
            m_CommandObserver.CreateCareerTableCommandReceived += new EventHandler<CommandEventArgs<CreateCareerTableCommand>>(m_CommandObserver_CreateCareerTableCommandReceived);
            m_CommandObserver.CheckDisplayExistCommandReceived += new EventHandler<CommandEventArgs<CheckDisplayExistCommand>>(m_CommandObserver_CheckDisplayExistCommandReceived);
            m_CommandObserver.CheckUserExistCommandReceived += new EventHandler<CommandEventArgs<PokerProtocol.Commands.Lobby.Career.CheckUserExistCommand>>(m_CommandObserver_CheckUserExistCommandReceived);
            m_CommandObserver.CreateUserCommandReceived += new EventHandler<CommandEventArgs<PokerProtocol.Commands.Lobby.Career.CreateUserCommand>>(m_CommandObserver_CreateUserCommandReceived);
            m_CommandObserver.AuthenticateUserCommandReceived += new EventHandler<CommandEventArgs<PokerProtocol.Commands.Lobby.Career.AuthenticateUserCommand>>(m_CommandObserver_AuthenticateUserCommandReceived);
            m_CommandObserver.GetUserCommandReceived += new EventHandler<CommandEventArgs<GetUserCommand>>(m_CommandObserver_GetUserCommandReceived);
        }

        void m_CommandObserver_CreateCareerTableCommandReceived(object sender, CommandEventArgs<CreateCareerTableCommand> e)
        {
            Send(e.Command.EncodeResponse(CreateTable(e.Command)));
        }

        void m_CommandObserver_CreateTrainingTableCommandReceived(object sender, CommandEventArgs<CreateTrainingTableCommand> e)
        {
            Send(e.Command.EncodeResponse(CreateTable(e.Command)));
        }

        private int CreateTable(AbstractCreateTableCommand c)
        {
            int res = m_Lobby.CreateTable(c);
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_{3}Received", "> Client '{0}' {3}: {2}:{1}", m_PlayerName, c.TableName, res, c.GetType().Name);
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

        void m_CommandObserver_AuthenticateUserCommandReceived(object sender, CommandEventArgs<PokerProtocol.Commands.Lobby.Career.AuthenticateUserCommand> e)
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

        void m_CommandObserver_CreateUserCommandReceived(object sender, CommandEventArgs<PokerProtocol.Commands.Lobby.Career.CreateUserCommand> e)
        {
            CreateUserCommand c = e.Command;
            bool ok = !DataManager.Persistance.IsUsernameExist(c.Username) && !DataManager.Persistance.IsDisplayNameExist(e.Command.DisplayName);

            if( ok)
                DataManager.Persistance.Register(new UserInfo(c.Username,c.Password,c.Email,c.DisplayName,7500));

            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_CreateUserCommandReceived", "> Client register to Career Server as : {0}. Success={1}", c.Username, ok);
            Send(e.Command.EncodeResponse(ok));
        }

        void m_CommandObserver_CheckUserExistCommandReceived(object sender, CommandEventArgs<PokerProtocol.Commands.Lobby.Career.CheckUserExistCommand> e)
        {
            Send(e.Command.EncodeResponse(DataManager.Persistance.IsUsernameExist(e.Command.Username)));
        }

        void m_CommandObserver_GameCommandReceived(object sender, CommandEventArgs<GameCommand> e)
        {
            m_Tables[e.Command.TableID].Incoming(e.Command.Command);
        }

        void m_CommandObserver_JoinTableCommandReceived(object sender, CommandEventArgs<JoinTableCommand> e)
        {
            GameServer client = null;
            PokerGame game = m_Lobby.GetGame(e.Command.TableID);
            TableInfo table = game.Table;

            if (game is PokerGameTraining)
                client = new GameServer(e.Command.TableID, game, m_PlayerName, ((PokerGameTraining)game).TrainingTable.StartingMoney);
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

                    LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_JoinTableCommandReceived", "> Client '{0}' seated ({3}) at table: {2}:{1}", m_PlayerName, table.Name, e.Command.TableID, client.Player.NoSeat);
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
            Send(new GameCommand(client.ID,e.Key));
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

        void m_CommandObserver_DisconnectCommandReceived(object sender, CommandEventArgs<DisconnectTextCommand> e)
        {
            LogManager.Log(LogLevel.Message, "ServerClientLobby.m_CommandObserver_DisconnectCommandReceived", "> Client disconnected: {0}", m_PlayerName);
            DisconnectTextCommand c = e.Command;
            m_Lobby.RemoveName(m_PlayerName);
            Close();
        }

        void m_CommandObserver_ListTableCommandReceived(object sender, CommandEventArgs<ListTableCommand> e)
        {
            ListTableCommand c = e.Command;
            if( c.Training )
                Send(c.EncodeTrainingResponse(m_Lobby.ListTrainingTables()));
            else
                Send(c.EncodeCareerResponse(m_Lobby.ListCareerTables()));
        }
    }
}
