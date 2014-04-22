using Com.Ericmas001.Game.Poker.GUI.Lobby;
using Com.Ericmas001.Windows.Forms;
using Com.Ericmas001.Game.Poker.Protocol.Client;
using System;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Splash
{
    public class TrainingSplashInfo : StepSplashInfo
    {
        private string m_PlayerName;
        private readonly string m_ServerAddress;
        private readonly int m_ServerPort;

        private LobbyTcpClientTraining m_Server;

        public LobbyTcpClientTraining Server
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
                return new[]
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
            var isOk = m_Server.Identify(m_PlayerName);
            var retry = true;
            while (!isOk && retry)
            {
                var form2 = new NameUsedForm(m_PlayerName);
                form2.ShowDialog();
                retry = form2.OK;
                m_PlayerName = form2.PlayerName;
                isOk = m_Server.Identify(m_PlayerName);
            }
            return isOk;
        }

        public override void Init()
        {
            m_Server = new LobbyTcpClientTraining(m_ServerAddress, m_ServerPort);
        }
    }
}
