using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace BluffinPokerClient
{
    public static class Program
    {
        private static WelcomeForm m_WForm;
        public static WelcomeForm WForm { get { return m_WForm; } }
        /// <summary>
        /// Point d'entrée principal de l'application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            m_WForm = new WelcomeForm();
            Application.Run(m_WForm);
        }
    }
}
