using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using PokerProtocol;
using BluffinPokerGUI.Lobby;
using EricUtility.Windows.Forms;
using EricUtility;

namespace BluffinPokerClient
{
    public partial class SplashCareerConnect : StepSplashForm
    {
        private string m_ServerAddress;
        private int m_ServerPort;
        private string m_Username;
        private string m_Password;

        private LobbyTCPClientCareer m_Server;

        public LobbyTCPClientCareer Server
        {
            get { return m_Server; }
        }

        public SplashCareerConnect(string serverAddress, int serverPort, string username, string password)
        {
            this.DialogResult = DialogResult.Cancel;
            m_Password = password;
            m_Username = username;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;

            InitializeComponent();

            m_Steps = new Tuple<BoolEmptyHandler, StatePictureBox>[]
            {
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep1ReachingServer, spb1ReachingServer),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep2CheckUsernameExistence, spb2CheckExistence),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep3Authenticate, spb3Authenticate),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep4RetrieveUserInfo, spb4RetrieveInfo)
            };

            new Thread(new ThreadStart(Connect)).Start();
        }

        private bool ExecuteStep1ReachingServer()
        {
            return m_Server.Connect();
        }

        private bool ExecuteStep2CheckUsernameExistence()
        {
            m_Server.Start();
            return !m_Server.CheckUsernameAvailable(m_Username);
        }

        private bool ExecuteStep3Authenticate()
        {
            return m_Server.Authenticate(m_Username, m_Password);
        }

        private bool ExecuteStep4RetrieveUserInfo()
        {
            m_Server.RefreshUserInfo(m_Username);
            return true;
        }

        private void Connect()
        {
            m_Server = new LobbyTCPClientCareer(m_ServerAddress, m_ServerPort);

            if (ExecuteSteps())
            {
                this.DialogResult = DialogResult.OK;
                Quit();
            }
            else
                Error();
        }

        private void Error()
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new EmptyHandler(Error), new object[] { });
                return;
            }
            btnCancel.Enabled = true;
        }

        private void Quit()
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new EmptyHandler(Quit), new object[] { });
                return;
            }
            Close();
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            Quit();
        }
    }
}
