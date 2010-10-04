using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;

namespace PokerProtocol
{
    public class TupleTableInfoCareer : TupleTableInfo
    {
        public TupleTableInfoCareer(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, TypeBet limit, PossibleActionType possibleAction)
        : base( p_noPort,  p_tableName,  p_bigBlind,  p_nbPlayers,  p_nbSeats,  limit,  possibleAction)
        {
        }

        public TupleTableInfoCareer(StringTokenizer argsToken)
            :base(argsToken)
        {
        }
    }
}
