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
    public class LobbyTCPClientTraining : LobbyTCPClient
    {
        public LobbyTCPClientTraining(string serverAddress, int serverPort)
            : base(serverAddress, serverPort)
        {
        }

        public bool Identify(string name)
        {
            m_PlayerName = name;

            Send(new IdentifyCommand(m_PlayerName));

            StringTokenizer token = WaitAndReceive(IdentifyResponse.COMMAND_NAME);
            if (!token.HasMoreTokens())
                return false;

            return new IdentifyResponse(token).OK;
        }

        public List<TableTraining> ListTables()
        {
            Send(new ListTableCommand(true));

            StringTokenizer token = WaitAndReceive(ListTableTrainingResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                ListTableTrainingResponse response = new ListTableTrainingResponse(token);
                return response.Tables;
            }
            else
                return new List<TableTraining>();
        }

        public int CreateTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit, int minPlayersToStart, int startingMoney)
        {
            Send(new CreateTrainingTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit, minPlayersToStart, startingMoney));

            StringTokenizer token = WaitAndReceive(CreateTrainingTableResponse.COMMAND_NAME);
            if (token.HasMoreTokens())
            {
                CreateTrainingTableResponse response = new CreateTrainingTableResponse(token);
                return response.Port;
            }
            else
                return -1;
        }
    }
}
