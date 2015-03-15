using System;
using System.Collections.Generic;
using System.Windows.Forms;
using Com.Ericmas001.Util;
using BluffinMuffin.Client.Menu;
using System.IO;
using System.Reflection;

namespace BluffinMuffin.Client
{
    public static class Program
    {
        static StreamWriter m_SwNormal;
        static StreamWriter m_SwDebug;
        static StreamWriter m_SwVerbose;
        /// <summary>
        /// Point d'entrée principal de l'application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            LogManager.MessageLogged += LogManager_MessageLogged;
            if ((args.Length % 2) == 0)
            {

                var map = new Dictionary<string, string>();
                for (var i = 0; i < args.Length; i += 2)
                    map.Add(args[i].ToLower(), args[i + 1]);
                if (map.ContainsKey("-log"))
                {
                    var uri = new Uri(Assembly.GetExecutingAssembly().CodeBase);
                    var path = Path.GetDirectoryName(uri.LocalPath + uri.Fragment) + "\\log";
                    if (!Directory.Exists(path))
                        Directory.CreateDirectory(path);
                    var logName = DateTime.Now.ToString("yyyy-MM-dd.HH-mm-ss");
                    var logType = map["-log"];
                    if (logType == "normal" || logType == "debug" || logType == "verbose")
                    {
                        m_SwNormal = File.CreateText(path + "\\client." + logName + ".normal.txt");
                        m_SwNormal.AutoFlush = true;
                        LogManager.MessageLogged += LogManager_MessageLoggedToFileNormal;
                        if (logType == "debug" || logType == "verbose")
                        {
                            m_SwDebug = File.CreateText(path + "\\client." + logName + ".debug.txt");
                            m_SwDebug.AutoFlush = true;
                            LogManager.MessageLogged += LogManager_MessageLoggedToFileDebug;
                            if (logType == "verbose")
                            {
                                m_SwVerbose = File.CreateText(path + "\\client." + logName + ".verbose.txt");
                                m_SwVerbose.AutoFlush = true;
                                LogManager.MessageLogged += LogManager_MessageLoggedToFileVerbose;
                            }
                        }
                    }

                }
            }
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MenuForm());
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
            LogManager.LogInFile(m_SwNormal, from, message, level, LogLevel.Message);
        }

        static void LogManager_MessageLoggedToFileDebug(string from, string message, int level)
        {
            LogManager.LogInFile(m_SwDebug, from, message, level, LogLevel.MessageLow);
        }

        static void LogManager_MessageLoggedToFileVerbose(string from, string message, int level)
        {
            LogManager.LogInFile(m_SwVerbose, from, message, level, LogLevel.MessageVeryLow);
        }
    }
}
