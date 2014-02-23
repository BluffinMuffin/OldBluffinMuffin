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
    public partial class SplashCareerRegister : StepSplashForm
    {
        private string m_ServerAddress;
        private int m_ServerPort;
        private string m_Username;
        private string m_Password;
        private string m_Email;
        private string m_DisplayName;

        private LobbyTCPClientCareer m_Server;

        public LobbyTCPClientCareer Server
        {
            get { return m_Server; }
        }

        public SplashCareerRegister(string serverAddress, int serverPort, string username, string password, string email, string displayname)
        {
            this.DialogResult = DialogResult.Cancel;
            m_DisplayName = displayname;
            m_Email = email;
            m_Password = password;
            m_Username = username;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;

            InitializeComponent();

            m_Steps = new Tuple<BoolEmptyHandler, StatePictureBox>[]
            {
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep1ReachingServer, spb1ReachingServer),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep2CheckUsernameAvailability, spb2AvailabilityUser),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep3CheckDisplaynameAvailability, spb3AvailabilityDisplay),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep4CreatingUser, spb4Creating),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep5Authenticate, spb5Authenticate),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep6RetrieveUserInfo, spb6RetrieveInfo)
            };

            new Thread(new ThreadStart(Connect)).Start();
        }

        private bool ExecuteStep1ReachingServer()
        {
            return m_Server.Connect();
        }

        private bool ExecuteStep2CheckUsernameAvailability()
        {
            m_Server.Start();
            bool step2OK = m_Server.CheckUsernameAvailable(m_Username);
            bool step2Retry = true;
            while (!step2OK && step2Retry)
            {
                NameUsedForm form2 = new NameUsedForm(m_Username);
                form2.ShowDialog();
                step2Retry = form2.OK;
                m_Username = form2.PlayerName;
                step2OK = m_Server.CheckUsernameAvailable(m_Username);
            }
            return step2OK;
        }
        private bool ExecuteStep3CheckDisplaynameAvailability()
        {
            bool step3OK = m_Server.CheckDisplayNameAvailable(m_DisplayName);
            bool step3Retry = true;
            while (!step3OK && step3Retry)
            {
                NameUsedForm form3 = new NameUsedForm(m_DisplayName);
                form3.ShowDialog();
                step3Retry = form3.OK;
                m_DisplayName = form3.PlayerName;
                step3OK = m_Server.CheckDisplayNameAvailable(m_DisplayName);
            }
            return step3OK;
        }
        private bool ExecuteStep4CreatingUser()
        {
           return m_Server.CreateUser(m_Username, m_Password, m_Email, m_DisplayName);
        }

        private bool ExecuteStep5Authenticate()
        {
            return m_Server.Authenticate(m_Username, m_Password);
        }

        private bool ExecuteStep6RetrieveUserInfo()
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
                try
                {
                    this.Invoke(new EmptyHandler(Quit), new object[] { });
                }
                catch
                {

                }
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
