using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using Com.Ericmas001;
using Com.Ericmas001.Collections;
using Com.Ericmas001.Net;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Training;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public class LobbyTCPClientCareer : LobbyTCPClient
    {

        private UserInfo m_User;

        public UserInfo User { get { return m_User; } }

        public LobbyTCPClientCareer(string serverAddress, int serverPort)
            : base(serverAddress, serverPort)
        {
        }

        protected override int GetJoinedSeat(int p_noPort, string player)
        {
            return base.GetJoinedSeat(p_noPort, m_User.Username);
        }

        public bool CheckUsernameAvailable(string username)
        {
            Send(new CheckUserExistCommand()
            {
                Username = username,
            });

            return !WaitAndReceive<CheckUserExistResponse>().Exist;
        }

        public bool CheckDisplayNameAvailable(string display)
        {
            Send(new CheckDisplayExistCommand()
            {
                DisplayName = display,
            });

            return !WaitAndReceive<CheckDisplayExistResponse>().Exist;
        }

        public bool CreateUser(string username, string password, string email, string displayname)
        {
            Send(new CreateUserCommand()
            {
                Username = username,
                Password = password,
                Email = email,
                DisplayName = displayname,
            });

            return WaitAndReceive<CreateUserResponse>().Success;
        }

        public bool Authenticate(string username, string password)
        {
            Send(new AuthenticateUserCommand()
            {
                Username = username,
                Password = password,
            });

            return WaitAndReceive<AuthenticateUserResponse>().Success;
        }

        public void RefreshUserInfo(string username)
        {
            Send(new GetUserCommand()
            {
                Username = username,
            });

            GetUserResponse response = WaitAndReceive<GetUserResponse>();
            m_PlayerName = response.DisplayName;
            m_User = new UserInfo(username, "", response.Email, response.DisplayName, response.Money);
        }
    }
}
