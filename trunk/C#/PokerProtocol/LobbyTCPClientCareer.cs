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
using Newtonsoft.Json.Linq;

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

            JObject jObj = WaitAndReceive(CheckUserExistResponse.COMMAND_NAME);

            return !new CheckUserExistResponse(jObj).Exist;
        }

        public bool CheckDisplayNameAvailable(string display)
        {
            Send(new CheckDisplayExistCommand(display));

            JObject jObj = WaitAndReceive(CheckDisplayExistResponse.COMMAND_NAME);

            return !new CheckDisplayExistResponse(jObj).Exist;
        }

        public bool CreateUser(string username, string password, string email, string displayname)
        {
            Send(new CreateUserCommand(username, password, email, displayname));

            JObject jObj = WaitAndReceive(CreateUserResponse.COMMAND_NAME);

            return new CreateUserResponse(jObj).Success;
        }

        public bool Authenticate(string username, string password)
        {
            Send(new AuthenticateUserCommand(username, password));

            JObject jObj = WaitAndReceive(AuthenticateUserResponse.COMMAND_NAME);

            return new AuthenticateUserResponse(jObj).Success;
        }

        public void RefreshUserInfo(string username)
        {
            Send(new GetUserCommand(username));

            JObject jObj = WaitAndReceive(GetUserResponse.COMMAND_NAME);

            GetUserResponse response = new GetUserResponse(jObj);
            m_PlayerName = response.DisplayName;
            m_User = new UserInfo(username, "", response.Email, response.DisplayName, response.Money);
        }

        public int CreateTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit, int minPlayersToStart)
        {
            Send(new CreateCareerTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit, minPlayersToStart));

            JObject jObj = WaitAndReceive(CreateCareerTableResponse.COMMAND_NAME);
            return new CreateCareerTableResponse(jObj).Port;
        }

        public List<TableCareer> ListTables()
        {
            Send(new ListTableCommand(false));

            JObject jObj = WaitAndReceive(ListTableCareerResponse.COMMAND_NAME);
            return new ListTableCareerResponse(jObj).Tables;
        }
    }
}
