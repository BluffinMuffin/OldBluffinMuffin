using System;
using System.Collections.Generic;
using System.Windows.Forms;
using EricUtility;

namespace BluffinPokerClient
{
    public static class Program
    {
        private static MainForm m_WForm;
        public static MainForm WForm { get { return m_WForm; } }
        /// <summary>
        /// Point d'entrée principal de l'application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            LogManager.MessageLogged += new LogDelegate(LogManager_MessageLogged);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            m_WForm = new MainForm();
            Application.Run(m_WForm);
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
