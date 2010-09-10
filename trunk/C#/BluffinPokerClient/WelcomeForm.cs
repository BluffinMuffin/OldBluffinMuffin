using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace BluffinPokerClient
{
    public partial class WelcomeForm : Form
    {
        public WelcomeForm()
        {
            InitializeComponent();
        }

        private void btnStartTraining_Click(object sender, EventArgs e)
        {
            Hide();
            TrainingConnectForm cf = new TrainingConnectForm(txtPlayerName.Text, clstServerName.Text, (int)nudServerPort.Value);
            cf.ShowDialog();
            if (cf.OK)
                new LobbyForm(cf.Server).Show();
            else
                Show();
        }
    }
}
