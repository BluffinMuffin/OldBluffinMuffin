﻿using System;
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

            JObject jObj = WaitAndReceive(IdentifyResponse.COMMAND_NAME);

            return new IdentifyResponse(jObj).OK;
        }

        public List<TableTraining> ListTables()
        {
            Send(new ListTableCommand(true));

            JObject jObj = WaitAndReceive(ListTableTrainingResponse.COMMAND_NAME);
            return new ListTableTrainingResponse(jObj).Tables;
        }

        public int CreateTable(string p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit, int minPlayersToStart, int startingMoney)
        {
            Send(new CreateTrainingTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_PlayerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit, minPlayersToStart, startingMoney));

            JObject jObj = WaitAndReceive(CreateTrainingTableResponse.COMMAND_NAME);
            return new CreateTrainingTableResponse(jObj).Port;
        }
    }
}