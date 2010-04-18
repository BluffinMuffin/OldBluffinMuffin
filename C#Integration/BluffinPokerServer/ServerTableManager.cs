using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using PokerWorld.Game;
using System.Threading;
using PokerProtocol.Commands.Lobby;
using PokerProtocol;

namespace BluffinPokerServer
{
    public class ServerTableManager
    {
        private readonly int m_NoPort;
        private readonly PokerGame m_Game;
        private readonly TcpListener m_SocketServer;

        public PokerGame Game
        {
            get { return m_Game; }
        } 

        public ServerTableManager(PokerGame game, int port)
        {
            m_NoPort = port;
            m_Game = game;
            m_SocketServer = new TcpListener(port);
        }
        public void Start()
        {
            m_SocketServer.Start();
            new Thread(new ThreadStart(Run)).Start();
        }

        private void Run()
        {
            while (true)
            {
                try
                {
                    TcpClient client = m_SocketServer.AcceptTcpClient();
                    ServerClientTableManager lobby = new ServerClientTableManager(client, this);
                    lobby.Start();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }
    }
}
