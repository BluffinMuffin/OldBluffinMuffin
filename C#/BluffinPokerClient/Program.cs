using System;
using System.Collections.Generic;
using System.Windows.Forms;

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
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            m_WForm = new MainForm();
            Application.Run(m_WForm);
        }
    }
}
