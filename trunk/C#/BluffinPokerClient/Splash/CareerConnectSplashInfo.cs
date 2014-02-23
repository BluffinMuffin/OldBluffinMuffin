using BluffinPokerGUI.Lobby;
using EricUtility.Windows.Forms;
using PokerProtocol;
using System;

namespace BluffinPokerClient.Splash
{
    public class CareerConnectSplashInfo : StepSplashInfo
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

        public override string Title
        {
            get { return "Start Playing ..."; }
        }

        public override Tuple<BoolEmptyHandler, string>[] Steps
        {
            get
            {
                return new Tuple<BoolEmptyHandler, string>[]
                {
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep1ReachingServer, "Reaching the server ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep2CheckUsernameExistence, "Existence of Username ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep3Authenticate, "Authenticating Player ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep4RetrieveUserInfo, "Retrieving User Info ..."),
                };
            }
        }

        public CareerConnectSplashInfo(string serverAddress, int serverPort, string username, string password)
        {
            m_Password = password;
            m_Username = username;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;
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

        public override void Init()
        {
            m_Server = new LobbyTCPClientCareer(m_ServerAddress, m_ServerPort);
        }
    }
}
