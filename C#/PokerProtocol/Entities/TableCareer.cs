using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;
using PokerProtocol.Entities.Enums;

namespace PokerProtocol.Entities
{
    public class TableCareer : Table
    {
        public TableCareer(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, TypeBet limit, EnumActions possibleAction)
        : base( p_noPort,  p_tableName,  p_bigBlind,  p_nbPlayers,  p_nbSeats,  limit,  possibleAction)
        {
        }

        public TableCareer(StringTokenizer argsToken)
            :base(argsToken)
        {
        }
    }
}
