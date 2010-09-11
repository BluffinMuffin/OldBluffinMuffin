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
    public partial class SplashCareerConnect : Form
    {
        private delegate void EmptyHandler();
        private string m_ServerAddress;
        private int m_ServerPort;
        private string m_Username;
        private string m_Password;

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

        public SplashCareerConnect(string serverAddress, int serverPort, string username, string password)
        {
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
                // Existence of Username ...
                if (!m_Server.CheckUsernameAvailable(m_Username))
                {
                    spbStep2.Etat = StatePictureBoxStates.Ok;
                    spbStep3.Etat = StatePictureBoxStates.Waiting;
                    // Authenticating Player ...
                    if (m_Server.Authenticate(m_Username, m_Password))
                    {
                        spbStep3.Etat = StatePictureBoxStates.Ok;
                        spbStep4.Etat = StatePictureBoxStates.Waiting;

                        // Retrieving User Info ...
                        m_Server.RefreshUserInfo(m_Username);
                        spbStep4.Etat = StatePictureBoxStates.Ok;

                        // Done !
                        m_OK = true;
                        Quit();
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
