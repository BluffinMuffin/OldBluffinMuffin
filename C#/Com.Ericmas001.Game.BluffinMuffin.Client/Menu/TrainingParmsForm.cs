using Com.Ericmas001.Game.BluffinMuffin.Client.Splash;
using EricUtility.Windows.Forms;
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
    public partial class TrainingParmsForm : Form
    {
        private string m_ServerAdress;
        private int m_ServerPort;

        public TrainingParmsForm(string serverAddress, int serverPort)
        {
            InitializeComponent();
            this.m_ServerAdress = serverAddress;
            this.m_ServerPort = serverPort;
        }

        private void btnTraining_Click(object sender, EventArgs e)
        {
            Hide();
            TrainingSplashInfo info = new TrainingSplashInfo(txtUsername.Text, m_ServerAdress, m_ServerPort);
            if (new StepSplashForm(info).ShowDialog() == DialogResult.OK)
                new LobbyTrainingForm(info.Server).ShowDialog();
            Show();
        }

        private void txtUsername_TextChanged(object sender, EventArgs e)
        {
            btnTraining.Enabled = !String.IsNullOrEmpty(txtUsername.Text);
        }
    }
}
