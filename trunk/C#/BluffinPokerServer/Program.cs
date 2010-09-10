using System;
using System.Collections.Generic;
using System.Text;

namespace BluffinPokerServer
{
    class Program
    {
        static void Main(string[] args)
        {
            ServerLobby server = new ServerLobby(4242);
            server.Start();
            Console.WriteLine("Server started");
        }
    }
}
