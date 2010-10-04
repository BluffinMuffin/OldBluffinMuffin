package bluffinmuffin.protocol;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameBetLimitType;

/**
 * @author Hocus
 *         This class represents a network table.
 */
public class TupleTableInfoCareer extends TupleTableInfo
{
    public TupleTableInfoCareer(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, GameBetLimitType limit, PossibleActionType possibleAction)
    {
        super(p_noPort, p_tableName, p_bigBlind, p_nbPlayers, p_nbSeats, limit, possibleAction);
    }
    
    public TupleTableInfoCareer(StringTokenizer argsToken)
    {
        super(argsToken);
    }
}
