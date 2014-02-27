using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby
{
    public abstract class AbstractCreateTableCommand : AbstractLobbyCommand
    {
        public string TableName { get; private set; }
        public int BigBlind { get; private set; }
        public int MaxPlayers { get; private set; }
        public int MinPlayersToStart { get; private set; }
        public string PlayerName { get; private set; }
        public int WaitingTimeAfterPlayerAction { get; private set; }
        public int WaitingTimeAfterBoardDealed { get; private set; }
        public int WaitingTimeAfterPotWon { get; private set; }
        public BetEnum Limit { get; private set; }

        public AbstractCreateTableCommand(JObject obj)
        {
            TableName = (string)obj["TableName"];
            BigBlind = (int)obj["BigBlind"];
            MaxPlayers = (int)obj["MaxPlayers"];
            MinPlayersToStart = (int)obj["MinPlayersToStart"];
            PlayerName = (string)obj["PlayerName"];
            WaitingTimeAfterPlayerAction = (int)obj["WaitingTimeAfterPlayerAction"];
            WaitingTimeAfterBoardDealed = (int)obj["WaitingTimeAfterBoardDealed"];
            WaitingTimeAfterPotWon = (int)obj["WaitingTimeAfterPotWon"];
            Limit = (BetEnum)(int)obj["Limit"];
        }

        public AbstractCreateTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit, int minPlayersToStart)
        {
            TableName = p_tableName;
            BigBlind = p_bigBlind;
            MaxPlayers = p_maxPlayers;
            PlayerName = p_playerName;
            WaitingTimeAfterPlayerAction = wtaPlayerAction;
            WaitingTimeAfterBoardDealed = wtaBoardDealed;
            WaitingTimeAfterPotWon = wtaPotWon;
            Limit = limit;
            MinPlayersToStart = minPlayersToStart;
        }
    }
}
