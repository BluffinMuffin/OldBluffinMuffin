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

namespace BluffinPokerClient
{
    public partial class TrainingConnectForm : Form
    {
        private delegate void EmptyHandler();
        private string m_PlayerName;
        private string m_ServerAddress;
        private int m_ServerPort;

        private LobbyTCPClient m_Server;
        private bool m_OK = false;

        public LobbyTCPClient Server
        {
            get { return m_Server; }
        }

        public bool OK
        {
            get { return m_OK; }
        }
        
        public TrainingConnectForm(string playerName,string serverAddress,int serverPort)
        {
            m_PlayerName = playerName;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;

            InitializeComponent();

            new Thread(new ThreadStart(Connect)).Start();
        }

        private void Connect()
        {
            m_Server = new LobbyTCPClient(m_ServerAddress, m_ServerPort);
            if (m_Server.Connect())
            {
                spbStep1.Etat = StatePictureBoxStates.Ok;
                spbStep2.Etat = StatePictureBoxStates.Waiting;
                m_Server.Start();
                bool isOk = m_Server.Identify(m_PlayerName);
                bool retry = true;
                while (!isOk & retry)
                {
                    NameUsedForm form2 = new NameUsedForm(m_PlayerName);
                    form2.ShowDialog();
                    m_PlayerName = form2.PlayerName;
                    isOk = m_Server.Identify(m_PlayerName);
                }
                if (isOk)
                {
                    spbStep2.Etat = StatePictureBoxStates.Ok;
                    m_OK = true;
                    Quit();
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
