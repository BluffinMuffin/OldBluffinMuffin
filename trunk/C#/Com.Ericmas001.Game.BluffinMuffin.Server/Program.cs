using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001.Util;
using System.IO;
using System.Reflection;
using System.Runtime.InteropServices;

namespace Com.Ericmas001.Game.BluffinMuffin.Server
{
    class Program
    {
        static StreamWriter swNormal;
        static StreamWriter swDebug;
        static StreamWriter swVerbose;
        static void Main(string[] args)
        {
            LogManager.MessageLogged += LogManager_MessageLogged;
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
                    if(map.ContainsKey("-log"))
                    {
                    Uri uri = new Uri(Assembly.GetExecutingAssembly().CodeBase);
                    string path = Path.GetDirectoryName(uri.LocalPath + uri.Fragment) + "\\log";
                        if (!Directory.Exists(path))
                            Directory.CreateDirectory(path);
                        string logName = DateTime.Now.ToString("yyyy-MM-dd.HH-mm-ss");
                        string logType = map["-log"];
                        if(logType == "normal" || logType == "debug" || logType == "verbose")
                        {
                            swNormal = File.CreateText(path + "\\server." + logName + ".normal.txt");
                            swNormal.AutoFlush = true;
                            LogManager.MessageLogged += LogManager_MessageLoggedToFileNormal;
                            if (logType == "debug" || logType == "verbose")
                            {
                                swDebug = File.CreateText(path + "\\server." + logName + ".debug.txt");
                                swDebug.AutoFlush = true;
                                LogManager.MessageLogged += LogManager_MessageLoggedToFileDebug;
                                if (logType == "verbose")
                                {
                                    swVerbose = File.CreateText(path + "\\server." + logName + ".verbose.txt");
                                    swVerbose.AutoFlush = true;
                                    LogManager.MessageLogged += LogManager_MessageLoggedToFileVerbose;
                                }
                            }
                        }

                    }
                    BluffinServerLobby server = new BluffinServerLobby(port);
                    server.Start();
                    LogManager.Log(LogLevel.Message, "BluffinMuffin.Server", "Server started on port {0}", port);
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
            LogManager.LogInConsole(from, message, level, LogLevel.Message);
        }

        static void LogManager_MessageLoggedToFileNormal(string from, string message, int level)
        {
            LogManager.LogInFile(swNormal, from, message, level, LogLevel.Message);
        }

        static void LogManager_MessageLoggedToFileDebug(string from, string message, int level)
        {
            LogManager.LogInFile(swDebug, from, message, level, LogLevel.MessageLow);
        }

        static void LogManager_MessageLoggedToFileVerbose(string from, string message, int level)
        {
            LogManager.LogInFile(swVerbose, from, message, level, LogLevel.MessageVeryLow);
        }
    }
}
