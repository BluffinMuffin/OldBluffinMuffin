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
using PokerProtocol.Entities;
using PokerWorld.Game.Enums;

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

        protected override int GetJoinedSeat(int p_noPort, string player)
        {
            return base.GetJoinedSeat(p_noPort, m_User.Username);
        }

        public bool CheckUsernameAvailable(string username)
        {
            Send(new CheckUserExistCommand(username));

            StringTokenizer token = WaitAndReceive(CheckUserExistResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;

            return !new CheckUserExistResponse(token).Exist;
        }

        public bool CheckDisplayNameAvailable(string display)
        {
            Send(new CheckDisplayExistCommand(display));

            StringTokenizer token = WaitAndReceive(CheckDisplayExistResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;

            return !new CheckDisplayExistResponse(token).Exist;
        }

        public bool CreateUser(string username, string password, string email, string displayname)
        {
            Send(new CreateUserCommand(username, password, email, displayname));

            StringTokenizer token = WaitAndReceive(CreateUserResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;

            return new CreateUserResponse(token).Success;
        }

        public bool Authenticate(string username, string password)
        {
            Send(new AuthenticateUserCommand(username, password));

            StringTokenizer token = WaitAndReceive(AuthenticateUserResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;

            return new AuthenticateUserResponse(token).Success;
        }

        public void RefreshUserInfo(string username)
        {
            Send(new GetUserCommand(username));

            StringTokenizer token = WaitAndReceive(GetUserResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return;

            GetUserResponse response = new GetUserResponse(token);
            m_PlayerName = response.DisplayName;
            m_User = new UserInfo(username, "", response.Email, response.DisplayName, response.Money);
        }

        public int CreateTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit)
        {
            Send(new CreateCareerTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit));

            StringTokenizer token = WaitAndReceive(CreateCareerTableResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                CreateCareerTableResponse response = new CreateCareerTableResponse(token);
                return response.Port;
            }
            else
                return -1;
        }

        public List<TableCareer> ListTables()
        {
            Send(new ListTableCommand(false));

            StringTokenizer token = WaitAndReceive(ListTableCareerResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                ListTableCareerResponse response = new ListTableCareerResponse(token);
                return response.Tables;
            }
            else
                return new List<TableCareer>();
        }
    }
}
