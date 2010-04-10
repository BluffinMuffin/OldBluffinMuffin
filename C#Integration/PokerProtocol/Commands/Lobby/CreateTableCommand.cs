using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands.Lobby.Response;
using PokerWorld.Game;

namespace PokerProtocol.Commands.Lobby
{
    public class CreateTableCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return CreateTableCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyCREATE_TABLE";

        private readonly string m_TableName;
        private readonly int m_BigBlind;
        private readonly int m_MaxPlayers;
        private readonly string m_PlayerName;
        private readonly int m_WaitingTimeAfterPlayerAction;
        private readonly int m_WaitingTimeAfterBoardDealed;
        private readonly int m_WaitingTimeAfterPotWon;
        private readonly TypeBet m_Limit;

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


        public TypeBet Limit
        {
            get { return m_Limit; }
        }

        public CreateTableCommand(StringTokenizer argsToken)
        {
            m_TableName = argsToken.NextToken();
            m_BigBlind = int.Parse(argsToken.NextToken());
            m_MaxPlayers = int.Parse(argsToken.NextToken());
            m_PlayerName = argsToken.NextToken();
            m_WaitingTimeAfterPlayerAction = int.Parse(argsToken.NextToken());
            m_WaitingTimeAfterBoardDealed = int.Parse(argsToken.NextToken());
            m_WaitingTimeAfterPotWon = int.Parse(argsToken.NextToken());
            m_Limit = (TypeBet)int.Parse(argsToken.NextToken());
        }

        public CreateTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit)
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

        public string EncodeResponse( int port )
        {
            return new CreateTableResponse(this, port).Encode();
        }
    }
}
