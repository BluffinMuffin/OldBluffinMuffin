using Com.Ericmas001.Game.Poker.GUI.Lobby;
using EricUtility.Windows.Forms;
using Com.Ericmas001.Game.Poker.Protocol.Client;
using System;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Splash
{
    public class CareerRegisterSplashInfo : StepSplashInfo
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

        public override string Title
        {
            get { return "Create Player and Start Playing ..."; }
        }

        public override Tuple<BoolEmptyHandler, string>[] Steps
        {
            get
            {
                return new Tuple<BoolEmptyHandler, string>[]
                {
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep1ReachingServer, "Reaching the server ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep2CheckUsernameAvailability, "Availability of Username ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep3CheckDisplaynameAvailability, "Availability of Display Name ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep4CreatingUser, "Creating User  ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep5Authenticate, "Authenticating Player ..."),
                    new Tuple<BoolEmptyHandler, string>(ExecuteStep6RetrieveUserInfo, "Retrieving User Info ...")
                };
            }
        }

        public CareerRegisterSplashInfo(string serverAddress, int serverPort, string username, string password, string email, string displayname)
        {
            m_DisplayName = displayname;
            m_Email = email;
            m_Password = password;
            m_Username = username;
            m_ServerAddress = serverAddress;
            m_ServerPort = serverPort;
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

        public override void Init()
        {
            m_Server = new LobbyTCPClientCareer(m_ServerAddress, m_ServerPort);
        }
    }
}
