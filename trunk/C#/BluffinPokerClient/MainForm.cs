using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace BluffinPokerClient
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void MainForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void btnStartTraining_Click(object sender, EventArgs e)
        {
            Hide();
            SplashTrainingConnect cf = new SplashTrainingConnect(txtPlayerName.Text, clstServerName.Text, (int)nudServerPort.Value);
            cf.ShowDialog();
            if (cf.OK)
                new TrainingLobbyForm(cf.Server).Show();
            else
                Show();
        }

        private void btnConnect_Click(object sender, EventArgs e)
        {

            Hide();
            SplashCareerConnect cf = new SplashCareerConnect(clstServerName.Text, (int)nudServerPort.Value, txtUsername.Text, txtPassword.Text);
            cf.ShowDialog();
            if (cf.OK)
                new TrainingLobbyForm(cf.Server).Show();
            else
                Show();
        }

        private void btnRegister_Click(object sender, EventArgs e)
        {
            //TODO: RICK: Validate Password & Email

            Hide();
            SplashCareerRegister cf = new SplashCareerRegister(clstServerName.Text, (int)nudServerPort.Value, txtUser.Text, txtPassword1.Text, txtEmail1.Text, txtDisplayName.Text);
            cf.ShowDialog();
            if (cf.OK)
                new TrainingLobbyForm(cf.Server).Show();
            else
                Show();
        }
    }
}
