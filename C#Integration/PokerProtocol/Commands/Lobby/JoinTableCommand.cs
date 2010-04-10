using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using PokerProtocol.Commands.Lobby.Response;
using PokerWorld.Game;

namespace PokerProtocol.Commands.Lobby
{
    public class JoinTableCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return JoinTableCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyJOIN_TABLE";

        private readonly string m_TableName;
        private readonly string m_PlayerName;

        public string TableName
        {
            get { return m_TableName; }
        } 

        public string PlayerName
        {
            get { return m_PlayerName; }
        } 

        public JoinTableCommand(StringTokenizer argsToken)
        {
            m_TableName = argsToken.NextToken();
            m_PlayerName = argsToken.NextToken();
        }

        public JoinTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit)
        {
            m_TableName = p_tableName;
            m_PlayerName = p_playerName;
        }

        public override void Encode(StringBuilder sb)
        {
            Append(sb, m_TableName);
            Append(sb, m_PlayerName);
        }

        public string EncodeResponse( int seat )
        {
            return new JoinTableResponse(this, seat).Encode();
        }
    }
}
