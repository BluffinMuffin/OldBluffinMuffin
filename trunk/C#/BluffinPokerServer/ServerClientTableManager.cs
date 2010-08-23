using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Networking.Commands;
using PokerProtocol.Observer;
using System.Net.Sockets;
using PokerProtocol.Commands.Lobby;
using EricUtility;
using PokerProtocol;
using PokerWorld.Game;

namespace BluffinPokerServer
{
    public class ServerClientTableManager : CommandTCPCommunicator<LobbyServerCommandObserver>
    {
        private string m_PlayerName = "?";
        private readonly ServerTableManager m_Lobby;

        public ServerClientTableManager(TcpClient client, ServerTableManager lobby)
            : base(client)
        {
            m_Lobby = lobby;
        }
        protected override void Run()
        {
            try
            {
                StringTokenizer token = new StringTokenizer(Receive(), AbstractLobbyCommand.Delimitter);
                String commandName = token.NextToken();
                // Expect client's authentification.
                if (!commandName.Equals(IdentifyCommand.COMMAND_NAME, StringComparison.InvariantCultureIgnoreCase))
                {
                    Console.WriteLine("TableManager ::: Authentification expected!!!");
                    return;
                }
                token = new StringTokenizer(Receive(), AbstractLobbyCommand.Delimitter);
                commandName = token.NextToken();
                // Expect client's authentification.
                if (!commandName.Equals(JoinTableCommand.COMMAND_NAME, StringComparison.InvariantCultureIgnoreCase))
                {
                    Console.WriteLine("TableManager ::: Join table expected!!!");
                    return;
                }
            }
            catch
            {
            }
        }
        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += new EventHandler<StringEventArgs>(m_CommandObserver_CommandReceived);
            m_CommandObserver.IdentifyCommandReceived += new EventHandler<CommandEventArgs<IdentifyCommand>>(m_CommandObserver_IdentifyCommandReceived);
            m_CommandObserver.JoinTableCommandReceived += new EventHandler<CommandEventArgs<JoinTableCommand>>(m_CommandObserver_JoinTableCommandReceived);
        }
        protected override void Send(string line)
        {
            Console.WriteLine("TableManager SEND to " + m_PlayerName + " [" + line + "]");
            base.Send(line);
        }

        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            Console.WriteLine("TableManager RECV from " + m_PlayerName + " [" + e.Str + "]");
        }

        void m_CommandObserver_IdentifyCommandReceived(object sender, CommandEventArgs<IdentifyCommand> e)
        {
            IdentifyCommand c = e.Command;
            m_PlayerName = c.Name;
            Send(c.EncodeResponse(true));
        }

        void m_CommandObserver_JoinTableCommandReceived(object sender, CommandEventArgs<JoinTableCommand> e)
        {
            const int ERROR = -1;
            JoinTableCommand c = e.Command;
            try
            {
                if (!m_Lobby.Game.IsRunning)
                {
                    Send(c.EncodeResponse(ERROR));
                    return;
                }

                GameTCPServer client = new GameTCPServer(m_Lobby.Game, m_PlayerName, 1500, m_Socket);
                PokerGame game = m_Lobby.Game;
                TableInfo t = game.Table;
                if (!t.ContainsPlayer(m_PlayerName))
                {
                    bool ok = client.JoinGame();
                    if (ok)
                    {
                        client.SetIsConnected();
                        client.Start();
                        Send(c.EncodeResponse(client.Player.NoSeat));
                        client.SitIn();
                    }
                    else
                        Send(c.EncodeResponse(ERROR));
                }
                else
                    Send(c.EncodeResponse(ERROR));
            }
            catch
            {
                Send(c.EncodeResponse(ERROR));
            }
        }

    }
}
