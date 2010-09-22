using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateCareerTableCommand : AbstractCreateTableCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyCREATE_TABLE";
        

        public CreateCareerTableCommand(StringTokenizer argsToken) : base(argsToken)
        {
        }

        public CreateCareerTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit)
            : base(p_tableName, p_bigBlind, p_maxPlayers, p_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit)
        {
        }

        public override void Encode(StringBuilder sb)
        {
            base.Encode(sb);

        }

        public string EncodeResponse( int port )
        {
            return new CreateCareerTableResponse(this, port).Encode();
        }
    }
}
