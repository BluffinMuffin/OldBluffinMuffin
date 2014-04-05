using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;

namespace BluffinPokerServer
{
    class Program
    {
        static void Main(string[] args)
        {
            LogManager.MessageLogged += new LogDelegate(LogManager_MessageLogged);
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
                    LogManager.Log(LogLevel.Message, "BluffinPokerServer", "Server started on port {0}", port);
                }
                catch
                {
                    LogManager.Log(LogLevel.Error, "Program.Main", "Can't start server !!");
                }
            }
            else
                LogManager.Log(LogLevel.Error, "Program.Main", "Incorrect number of application arguments");
        }

        static void LogManager_MessageLogged(string from, string message, int level)
        {
            // ATTENTION: This must contain "LogLevel.Message" for RELEASE
            //                              "LogLevel.MessageLow" for DEBUGGING
            //                              "LogLevel.MessageVeryLow" for XTREM DEBUGGING
            LogManager.LogInConsole(from, message, level, LogLevel.MessageVeryLow);
        }
    }
}
