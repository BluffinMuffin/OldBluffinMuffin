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
        public string TableName { get; set; }
        public int BigBlind { get; set; }
        public int MaxPlayers { get; set; }
        public int MinPlayersToStart { get; set; }
        public string PlayerName { get; set; }
        public int WaitingTimeAfterPlayerAction { get; set; }
        public int WaitingTimeAfterBoardDealed { get; set; }
        public int WaitingTimeAfterPotWon { get; set; }
        public LimitTypeEnum Limit { get; set; }

        public AbstractCreateTableCommand()
        {
        }

        public AbstractCreateTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, LimitTypeEnum limit, int minPlayersToStart)
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
