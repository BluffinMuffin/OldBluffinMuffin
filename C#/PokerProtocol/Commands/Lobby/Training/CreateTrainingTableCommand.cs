using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;

namespace PokerProtocol.Commands.Lobby.Training
{
    public class CreateTrainingTableCommand : AbstractCreateTableCommand
    {
        public static string COMMAND_NAME = "lobbyTRAINING_CREATE_TABLE";

        private int m_StartingMoney;

        public int StartingMoney { get { return m_StartingMoney; } }

        public CreateTrainingTableCommand(StringTokenizer argsToken) : base(argsToken)
        {
            m_StartingMoney = int.Parse(argsToken.NextToken());
        }

        public CreateTrainingTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit, int minPlayersToStart, int startingMoney)
            : base(p_tableName, p_bigBlind, p_maxPlayers, p_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit, minPlayersToStart)
        {
            m_StartingMoney = startingMoney;
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);
            Append(sb, m_StartingMoney);
        }

        public string EncodeResponse( int port )
        {
            return new CreateTrainingTableResponse(this, port).Encode();
        }
    }
}
