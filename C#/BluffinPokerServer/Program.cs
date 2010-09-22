using System;
using System.Collections.Generic;
using System.Text;

namespace BluffinPokerServer
{
    class Program
    {
        static void Main(string[] args)
        {
            if ((args.Length % 2) == 0)
            {
                try
                {
                    Dictionary<string, string> map = new Dictionary<string, string>();
                    for (int i = 0; i < args.Length; i += 2)
                        map.Add(args[i].ToLower(), args[i + 1]);
                    int port = 4242;
                    if (map.ContainsKey("-p"))
                        port = int.Parse(map["-p"]);
                    ServerLobby server = new ServerLobby(port);
                    server.Start();
                    Console.WriteLine("Server started on port " + port);
                }
                catch
                {
                    Console.WriteLine("ERROR: Can't start server !!");
                }
            }
            else
                Console.WriteLine("ERROR: incorrect args");
        }
    }
}
