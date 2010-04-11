using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using BluffinPokerGUI.Lobby;

namespace AppTest
{
    static class Program
    {
        /// <summary>
        /// Point d'entrée principal de l'application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new AddTableForm("Patate", 5));
            Application.Run(new NameUsedForm("Patate"));
        }
    }
}
