using Com.Ericmas001.Game.Poker.GUI.Lobby;
using Com.Ericmas001.Windows.Forms;
using Com.Ericmas001.Game.Poker.Protocol.Client;
using System;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Splash
{
    public class CareerRegisterSplashInfo : StepSplashInfo
    {
        private readonly string m_ServerAddress;
        private readonly int m_ServerPort;
        private string m_Username;
        private readonly string m_Password;
        private readonly string m_Email;
        private string m_DisplayName;

        private LobbyTcpClientCareer m_Server;

        public LobbyTcpClientCareer Server
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
                return new[]
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
            var step2Ok = m_Server.CheckUsernameAvailable(m_Username);
            var step2Retry = true;
            while (!step2Ok && step2Retry)
            {
                var form2 = new NameUsedForm(m_Username);
                form2.ShowDialog();
                step2Retry = form2.OK;
                m_Username = form2.PlayerName;
                step2Ok = m_Server.CheckUsernameAvailable(m_Username);
            }
            return step2Ok;
        }
        private bool ExecuteStep3CheckDisplaynameAvailability()
        {
            var step3Ok = m_Server.CheckDisplayNameAvailable(m_DisplayName);
            var step3Retry = true;
            while (!step3Ok && step3Retry)
            {
                var form3 = new NameUsedForm(m_DisplayName);
                form3.ShowDialog();
                step3Retry = form3.OK;
                m_DisplayName = form3.PlayerName;
                step3Ok = m_Server.CheckDisplayNameAvailable(m_DisplayName);
            }
            return step3Ok;
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
            m_Server = new LobbyTcpClientCareer(m_ServerAddress, m_ServerPort);
        }
    }
}
