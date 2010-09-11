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

namespace BluffinPokerClient
{
    public partial class SplashCareerRegister : Form
    {
        private delegate void EmptyHandler();
        private string m_ServerAddress;
        private int m_ServerPort;
        private string m_Username;
        private string m_Password;
        private string m_Email;
        private string m_DisplayName;

        private LobbyTCPClientCareer m_Server;
        private bool m_OK = false;

        public LobbyTCPClientCareer Server
        {
            get { return m_Server; }
        }

        public bool OK
        {
            get { return m_OK; }
        }

        public SplashCareerRegister(string serverAddress, int serverPort, string username, string password, string email, string displayname)
        {
            m_DisplayName = displayname;
            m_Email = email;
            m_Password = password;
            m_Username = username;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;

            InitializeComponent();

            new Thread(new ThreadStart(Connect)).Start();
        }

        private void Connect()
        {
            m_Server = new LobbyTCPClientCareer(m_ServerAddress, m_ServerPort);
            // Reaching the server ...
            if (m_Server.Connect())
            {
                spbStep1.Etat = StatePictureBoxStates.Ok;
                spbStep2.Etat = StatePictureBoxStates.Waiting;
                m_Server.Start();

                // Availability of Username ...
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
                if (step2OK)
                {
                    spbStep2.Etat = StatePictureBoxStates.Ok;
                    spbStep3.Etat = StatePictureBoxStates.Waiting;

                    // Availability of Display Name ...
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
                    if (step3OK)
                    {
                        spbStep3.Etat = StatePictureBoxStates.Ok;
                        spbStep4.Etat = StatePictureBoxStates.Waiting;

                        // Creating User  ...
                        if (m_Server.CreateUser(m_Username, m_Password, m_Email, m_DisplayName))
                        {
                            spbStep4.Etat = StatePictureBoxStates.Ok;
                            spbStep5.Etat = StatePictureBoxStates.Waiting;

                            // Authenticating Player ...
                            if (m_Server.Authenticate(m_Username, m_Password))
                            {
                                spbStep5.Etat = StatePictureBoxStates.Ok;
                                spbStep6.Etat = StatePictureBoxStates.Waiting;

                                // Retrieving User Info ...
                                m_Server.RefreshUserInfo(m_Username);
                                spbStep6.Etat = StatePictureBoxStates.Ok;

                                // Done !
                                m_OK = true;
                                Quit();
                            }
                            else
                            {
                                spbStep5.Etat = StatePictureBoxStates.Bad;
                                Error();
                            }
                        }
                        else
                        {
                            spbStep4.Etat = StatePictureBoxStates.Bad;
                            Error();
                        }
                    }
                    else
                    {
                        spbStep3.Etat = StatePictureBoxStates.Bad;
                        Error();
                    }
                }
                else
                {
                    spbStep2.Etat = StatePictureBoxStates.Bad;
                    Error();
                }
            }
            else
            {
                spbStep1.Etat = StatePictureBoxStates.Bad;
                Error();
            }
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
            m_OK = false;
            Quit();
        }
    }
}
