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
    public partial class SplashTrainingConnect : StepSplashForm
    {
        private string m_PlayerName;
        private string m_ServerAddress;
        private int m_ServerPort;

        private LobbyTCPClientTraining m_Server;

        public LobbyTCPClientTraining Server
        {
            get { return m_Server; }
        }
        
        public SplashTrainingConnect(string playerName,string serverAddress,int serverPort)
        {
            m_PlayerName = playerName;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;

            InitializeComponent();

            m_Steps = new Tuple<BoolEmptyHandler, StatePictureBox>[]
            {
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep1ReachingServer, spb1ReachingServer),
                new Tuple<BoolEmptyHandler, StatePictureBox>(ExecuteStep2Identifying, spb2IdentifyingPlayer)
            };

            new Thread(new ThreadStart(Connect)).Start();
        }

        private bool ExecuteStep1ReachingServer()
        {
            return m_Server.Connect();
        }

        private bool ExecuteStep2Identifying()
        {
            m_Server.Start();
            bool isOk = m_Server.Identify(m_PlayerName);
            bool retry = true;
            while (!isOk && retry)
            {
                NameUsedForm form2 = new NameUsedForm(m_PlayerName);
                form2.ShowDialog();
                retry = form2.OK;
                m_PlayerName = form2.PlayerName;
                isOk = m_Server.Identify(m_PlayerName);
            }
            return isOk;
        }

        private void Connect()
        {
            m_Server = new LobbyTCPClientTraining(m_ServerAddress, m_ServerPort);

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
