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
    public partial class CareerRegisterParmsForm : Form
    {
        private string m_ServerAdress;
        private int m_ServerPort;

        public CareerRegisterParmsForm(string serverAddress, int serverPort)
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
            btnRegister.Enabled = !String.IsNullOrEmpty(txtUsername.Text) && !String.IsNullOrEmpty(txtPassword.Text) && !String.IsNullOrEmpty(txtPasswordConfirm.Text) && !String.IsNullOrEmpty(txtEmail.Text) && !String.IsNullOrEmpty(txtEmailConfirm.Text) && !String.IsNullOrEmpty(txtDisplayName.Text);
        }

        private void btnRegister_Click(object sender, EventArgs e)
        {
            Hide();
            CareerRegisterSplashInfo info = new CareerRegisterSplashInfo(m_ServerAdress, m_ServerPort, txtUsername.Text, txtPassword.Text, txtEmail.Text, txtDisplayName.Text);
            if (new StepSplashForm(info).ShowDialog() == DialogResult.OK)
            {
                new LobbyCareerForm(info.Server).ShowDialog();
                Close();
            }
            else
                Show();

        }
    }
}
