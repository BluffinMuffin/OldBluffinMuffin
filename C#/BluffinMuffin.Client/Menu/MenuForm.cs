using System;
using System.Diagnostics;
using System.Windows.Forms;

namespace BluffinMuffin.Client.Menu
{
    public partial class MenuForm : Form
    {
        public MenuForm()
        {
            InitializeComponent();
        }

        private void btnQuickMode_Click(object sender, EventArgs e)
        {
            Hide();
            new QuickModeParmsForm(clstServerName.Text, (int)nudServerPort.Value).ShowDialog();
            Show();
        }

        private void btnRegisteredModeConnect_Click(object sender, EventArgs e)
        {
            Hide();
            new RegisteredModeConnectParmsForm(clstServerName.Text, (int)nudServerPort.Value).ShowDialog();
            Show();
        }

        private void btnRegisteredModeRegister_Click(object sender, EventArgs e)
        {
            Hide();
            new RegisteredModeRegisterParmsForm(clstServerName.Text, (int)nudServerPort.Value).ShowDialog();
            Show();
        }

        private void MenuForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
            Process.GetCurrentProcess().Kill();
            // If after that i'm still in memory, cry :)
        }
    }
}
