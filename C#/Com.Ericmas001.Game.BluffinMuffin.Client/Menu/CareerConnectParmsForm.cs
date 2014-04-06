using Com.Ericmas001.Game.BluffinMuffin.Client.Splash;
using Com.Ericmas001.Windows.Forms;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Menu
{
    public partial class CareerConnectParmsForm : Form
    {
        private string m_ServerAdress;
        private int m_ServerPort;

        public CareerConnectParmsForm(string serverAddress, int serverPort)
        {
            InitializeComponent();
            this.m_ServerAdress = serverAddress;
            this.m_ServerPort = serverPort;
        }

        private void btnPlay_Click(object sender, EventArgs e)
        {
            Hide();
            CareerConnectSplashInfo info = new CareerConnectSplashInfo(m_ServerAdress, m_ServerPort, txtUsername.Text, txtPassword.Text);
            if (new StepSplashForm(info).ShowDialog() == DialogResult.OK)
            {
                txtPassword.Text = "";
                new LobbyCareerForm(info.Server).ShowDialog();
            }
            Show();
        }

        private void txtPassword_TextChanged(object sender, EventArgs e)
        {
            btnPlay.Enabled = !String.IsNullOrEmpty(txtUsername.Text) && !String.IsNullOrEmpty(txtPassword.Text);
        }
    }
}
