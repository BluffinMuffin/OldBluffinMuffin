using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Lobby
{
    public abstract class AbstractCreateTableCommand : AbstractLobbyCommand
    {
        protected readonly string m_TableName;
        protected readonly int m_BigBlind;
        protected readonly int m_MaxPlayers;
        protected readonly string m_PlayerName;
        protected readonly int m_WaitingTimeAfterPlayerAction;
        protected readonly int m_WaitingTimeAfterBoardDealed;
        protected readonly int m_WaitingTimeAfterPotWon;
        protected readonly BetEnum m_Limit;

        public string TableName
        {
            get { return m_TableName; }
        } 


        public int BigBlind
        {
            get { return m_BigBlind; }
        } 


        public int MaxPlayers
        {
            get { return m_MaxPlayers; }
        } 


        public string PlayerName
        {
            get { return m_PlayerName; }
        } 


        public int WaitingTimeAfterPlayerAction
        {
            get { return m_WaitingTimeAfterPlayerAction; }
        } 


        public int WaitingTimeAfterBoardDealed
        {
            get { return m_WaitingTimeAfterBoardDealed; }
        }


        public int WaitingTimeAfterPotWon
        {
            get { return m_WaitingTimeAfterPotWon; }
        } 


        public BetEnum Limit
        {
            get { return m_Limit; }
        }

        public AbstractCreateTableCommand(StringTokenizer argsToken)
        {
            m_TableName = argsToken.NextToken();
            m_BigBlind = int.Parse(argsToken.NextToken());
            m_MaxPlayers = int.Parse(argsToken.NextToken());
            m_PlayerName = argsToken.NextToken();
            m_WaitingTimeAfterPlayerAction = int.Parse(argsToken.NextToken());
            m_WaitingTimeAfterBoardDealed = int.Parse(argsToken.NextToken());
            m_WaitingTimeAfterPotWon = int.Parse(argsToken.NextToken());
            m_Limit = (BetEnum)int.Parse(argsToken.NextToken());
        }

        public AbstractCreateTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit)
        {
            m_TableName = p_tableName;
            m_BigBlind = p_bigBlind;
            m_MaxPlayers = p_maxPlayers;
            m_PlayerName = p_playerName;
            m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
            m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
            m_WaitingTimeAfterPotWon = wtaPotWon;
            m_Limit = limit;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_TableName);
            Append(sb, m_BigBlind);
            Append(sb, m_MaxPlayers);
            Append(sb, m_PlayerName);
            Append(sb, m_WaitingTimeAfterPlayerAction);
            Append(sb, m_WaitingTimeAfterBoardDealed);
            Append(sb, m_WaitingTimeAfterPotWon);
            Append(sb, (int)m_Limit);
        }
    }
}
