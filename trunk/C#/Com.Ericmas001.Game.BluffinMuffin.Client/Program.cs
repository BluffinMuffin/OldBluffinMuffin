using System;
using System.Collections.Generic;
using System.Windows.Forms;
using EricUtility;
using Com.Ericmas001.Game.BluffinMuffin.Client.Menu;

namespace Com.Ericmas001.Game.BluffinMuffin.Client
{
    public static class Program
    {
        /// <summary>
        /// Point d'entrée principal de l'application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            LogManager.MessageLogged += new LogDelegate(LogManager_MessageLogged);
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
    }
}
