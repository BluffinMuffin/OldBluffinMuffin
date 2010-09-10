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
            m_CommandObserver.IdentifyCommandReceived += new EventHandler<CommandEventArgs<IdentifyCommand>>(m_CommandObserver_IdentifyCommandReceived);
            m_CommandObserver.DisconnectCommandReceived += new EventHandler<CommandEventArgs<DisconnectCommand>>(m_CommandObserver_DisconnectCommandReceived);
            m_CommandObserver.CreateTableCommandReceived += new EventHandler<CommandEventArgs<CreateTableCommand>>(m_CommandObserver_CreateTableCommandReceived);
            m_CommandObserver.ListTableCommandReceived += new EventHandler<CommandEventArgs<ListTableCommand>>(m_CommandObserver_ListTableCommandReceived);
            m_CommandObserver.JoinTableCommandReceived += new EventHandler<CommandEventArgs<JoinTableCommand>>(m_CommandObserver_JoinTableCommandReceived);
            m_CommandObserver.GameCommandReceived += new EventHandler<CommandEventArgs<GameCommand>>(m_CommandObserver_GameCommandReceived);
        }

        void m_CommandObserver_GameCommandReceived(object sender, CommandEventArgs<GameCommand> e)
        {
            m_Tables[e.Command.TableID].Incoming(e.Command.Command);
        }

        void m_CommandObserver_JoinTableCommandReceived(object sender, CommandEventArgs<JoinTableCommand> e)
        {
            GameServer client = new GameServer(e.Command.TableID, m_Lobby.GetGame(e.Command.TableID), m_PlayerName, 1500);
            client.SendedSomething += new EventHandler<EricUtility.KeyEventArgs<string>>(client_SendedSomething);
            PokerGame game = client.Game;
            TableInfo table = game.Table;
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
                    {
                        Send(e.Command.EncodeErrorResponse());
                    }
                    else
                    {
                        m_Tables.Add(e.Command.TableID, client);
                        client.Start();
                        Send(e.Command.EncodeResponse(client.Player.NoSeat));
                        client.SitIn();
                    }
                }
                else
                {
                    Send(e.Command.EncodeErrorResponse());
                }
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
                Console.WriteLine("Server lost connection with " + m_PlayerName);
                m_Lobby.RemoveName(m_PlayerName);
                Close();
            }
            else
                base.OnReceiveCrashed(e);
        }

        protected override void Send(string line)
        {
            Console.WriteLine("Server SEND to " + m_PlayerName + " [" + line + "]");
            base.Send(line);
        }

        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            Console.WriteLine("Server RECV from " + m_PlayerName + " [" + e.Str + "]");
        }

        void m_CommandObserver_IdentifyCommandReceived(object sender, CommandEventArgs<IdentifyCommand> e)
        {
            IdentifyCommand c = e.Command;
            m_PlayerName = c.Name;
            bool ok = !m_Lobby.NameUsed(m_PlayerName);
            Send(c.EncodeResponse(ok));
            if (ok)
                m_Lobby.AddName(m_PlayerName);
        }

        void m_CommandObserver_DisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            DisconnectCommand c = e.Command;
            m_Lobby.RemoveName(m_PlayerName);
            Close();
        }

        void m_CommandObserver_CreateTableCommandReceived(object sender, CommandEventArgs<CreateTableCommand> e)
        {
            CreateTableCommand c = e.Command;
            Send(c.EncodeResponse(m_Lobby.CreateTable(c)));
        }

        void m_CommandObserver_ListTableCommandReceived(object sender, CommandEventArgs<ListTableCommand> e)
        {
            ListTableCommand c = e.Command;
            Send(c.EncodeResponse(m_Lobby.ListTables()));
        }
    }
}
