using BluffinMuffin.Client.Splash;
using Com.Ericmas001.Windows.Forms;
using System;
using System.Windows.Forms;

namespace BluffinMuffin.Client.Menu
{
    public partial class CareerRegisterParmsForm : Form
    {
        private readonly string m_ServerAdress;
        private readonly int m_ServerPort;

        public CareerRegisterParmsForm(string serverAddress, int serverPort)
        {
            InitializeComponent();
            m_ServerAdress = serverAddress;
            m_ServerPort = serverPort;
        }

        private void txtPassword_TextChanged(object sender, EventArgs e)
        {
            btnRegister.Enabled = !String.IsNullOrEmpty(txtUsername.Text) && !String.IsNullOrEmpty(txtPassword.Text) && !String.IsNullOrEmpty(txtPasswordConfirm.Text) && !String.IsNullOrEmpty(txtEmail.Text) && !String.IsNullOrEmpty(txtEmailConfirm.Text) && !String.IsNullOrEmpty(txtDisplayName.Text);
        }

        private void btnRegister_Click(object sender, EventArgs e)
        {
            Hide();
            var info = new CareerRegisterSplashInfo(m_ServerAdress, m_ServerPort, txtUsername.Text, txtPassword.Text, txtEmail.Text, txtDisplayName.Text);
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
