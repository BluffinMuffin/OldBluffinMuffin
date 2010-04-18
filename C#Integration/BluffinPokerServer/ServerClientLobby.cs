using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Networking.Commands;
using PokerProtocol.Observer;
using System.Net.Sockets;
using PokerProtocol.Commands.Lobby;

namespace BluffinPokerServer
{
    public class ServerClientLobby : CommandTCPCommunicator<LobbyServerCommandObserver>
    {
        private string m_PlayerName = "?";
        private readonly ServerLobby m_Lobby;

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
