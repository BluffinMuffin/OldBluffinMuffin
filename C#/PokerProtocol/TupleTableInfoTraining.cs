using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;

namespace PokerProtocol
{
    public class TupleTableInfoTraining : TupleTableInfo
    {
        public TupleTableInfoTraining(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, TypeBet limit, PossibleActionType possibleAction)
        : base( p_noPort,  p_tableName,  p_bigBlind,  p_nbPlayers,  p_nbSeats,  limit,  possibleAction)
        {
        }

        public TupleTableInfoTraining(StringTokenizer argsToken)
            :base(argsToken)
        {
        }
    }
}
