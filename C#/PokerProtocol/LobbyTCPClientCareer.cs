using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using EricUtility;
using EricUtility.Collections;
using EricUtility.Networking;
using EricUtility.Networking.Commands;
using PokerProtocol.Commands.Lobby;
using PokerWorld.Game;
using PokerProtocol.Commands.Lobby.Training;
using PokerWorld.Data;
using PokerProtocol.Commands.Lobby.Career;

namespace PokerProtocol
{
    public class LobbyTCPClientCareer : LobbyTCPClient
    {

        private UserInfo m_User;

        public UserInfo User { get { return m_User; } }

        public LobbyTCPClientCareer(string serverAddress, int serverPort)
            : base(serverAddress, serverPort)
        {
        }

        public bool CheckUsernameAvailable(string username)
        {
            Send(new CheckUserExistCommand(username));
            StringTokenizer token = ReceiveCommand(CheckUserExistResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;
            CheckUserExistResponse response = new CheckUserExistResponse(token);
            return !response.Exist;
        }

        public bool CheckDisplayNameAvailable(string display)
        {
            Send(new CheckDisplayExistCommand(display));
            StringTokenizer token = ReceiveCommand(CheckDisplayExistResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;
            CheckDisplayExistResponse response = new CheckDisplayExistResponse(token);
            return !response.Exist;
        }

        public bool CreateUser(string username, string password, string email, string displayname)
        {
            Send(new CreateUserCommand(username, password, email, displayname));
            StringTokenizer token = ReceiveCommand(CreateUserResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;
            CreateUserResponse response = new CreateUserResponse(token);
            return response.Success;
        }

        public bool Authenticate(string username, string password)
        {
            Send(new AuthenticateUserCommand(username, password));
            StringTokenizer token = ReceiveCommand(AuthenticateUserResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;
            AuthenticateUserResponse response = new AuthenticateUserResponse(token);
            return response.Success;
        }

        public void RefreshUserInfo(string username)
        {
            Send(new GetUserCommand(username));
            StringTokenizer token = ReceiveCommand(GetUserResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return;
            GetUserResponse response = new GetUserResponse(token);
            m_PlayerName = response.DisplayName;
            m_User = new UserInfo(username, "", response.Email, response.DisplayName, response.Money);
        }
    }
}
