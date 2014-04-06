using Com.Ericmas001.Game.BluffinMuffin.GUI.Lobby;
using EricUtility.Windows.Forms;
using Com.Ericmas001.Game.Poker.Protocol.Client;
using System;

namespace BluffinPokerClient.Splash
{
    public class TrainingSplashInfo : StepSplashInfo
    {
        private string m_PlayerName;
        private string m_ServerAddress;
        private int m_ServerPort;

        private LobbyTCPClientTraining m_Server;

        public LobbyTCPClientTraining Server
        {
            get { return m_Server; }
        }

        public override string Title
        {
            get { return "Start Training ..."; }
        }

        public override Tuple<BoolEmptyHandler, string>[] Steps
        {
            get
            {
                return new Tuple<BoolEmptyHandler, string>[]
                {
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep1ReachingServer, "Reaching the server ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep2Identifying, "Identifying the Player ...")
                };
            }
        }

        public TrainingSplashInfo(string playerName, string serverAddress, int serverPort)
        {
            m_PlayerName = playerName;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;
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

        public override void Init()
        {
            m_Server = new LobbyTCPClientTraining(m_ServerAddress, m_ServerPort);
        }
    }
}
