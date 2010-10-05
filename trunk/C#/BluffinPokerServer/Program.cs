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
            if ((args.Length % 2) == 0)
            {
                try
                {
                    LogManager.MessageLogged += new LogDelegate(LogManager_MessageLogged);

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
            ConsoleColor c = Console.ForegroundColor;

            if (level >= (int)LogLevel.Error)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                message = "ERROR: " + message;
            }
            else if (level >= (int)LogLevel.ErrorLow)
            {
                Console.ForegroundColor = ConsoleColor.DarkRed;
            }
            else if (level >= (int)LogLevel.Warning)
            {
                Console.ForegroundColor = ConsoleColor.Yellow;
                message = "WARNING: " + message;
            }
            else if (level >= (int)LogLevel.WarningLow)
            {
                Console.ForegroundColor = ConsoleColor.DarkYellow;
            }
            else if (level >= (int)LogLevel.MessageVeryHigh)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                message = "IMPORTANT: " + message;
            }
            else if (level >= (int)LogLevel.Message)
            {
                Console.ForegroundColor = ConsoleColor.White;
            }
            else if (level >= (int)LogLevel.MessageLow)
            {
                Console.ForegroundColor = ConsoleColor.Cyan;
                message = "DEBUG: " + message;
            }
            else
            {
                Console.ForegroundColor = ConsoleColor.DarkCyan;
                message = "DEBUG: " + message;
            }

            // ATTENTION: This if must contain "LogLevel.Message" for RELEASE
            //                                 "LogLevel.MessageLow" for DEBUGGING
            //                                 "LogLevel.MessageVeryLow" for XTREM DEBUGGING
            if (level >= (int)LogLevel.Message)
                Console.WriteLine(message);



            Console.ForegroundColor = c;
        }
    }
}
