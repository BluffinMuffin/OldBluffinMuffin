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
