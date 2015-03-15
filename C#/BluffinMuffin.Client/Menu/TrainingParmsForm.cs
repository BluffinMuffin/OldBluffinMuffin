using BluffinMuffin.Client.Splash;
using Com.Ericmas001.Windows.Forms;
using System;
using System.Windows.Forms;

namespace BluffinMuffin.Client.Menu
{
    public partial class TrainingParmsForm : Form
    {
        private readonly string m_ServerAdress;
        private readonly int m_ServerPort;

        public TrainingParmsForm(string serverAddress, int serverPort)
        {
            InitializeComponent();
            m_ServerAdress = serverAddress;
            m_ServerPort = serverPort;
        }

        private void btnTraining_Click(object sender, EventArgs e)
        {
            Hide();
            var info = new TrainingSplashInfo(txtUsername.Text, m_ServerAdress, m_ServerPort);
            if (new StepSplashForm(info).ShowDialog() == DialogResult.OK)
            {
                new LobbyTrainingForm(info.Server).ShowDialog();
                Close();
            }
            else
                Show();
        }

        private void txtUsername_TextChanged(object sender, EventArgs e)
        {
            btnTraining.Enabled = !String.IsNullOrEmpty(txtUsername.Text);
        }
    }
}
