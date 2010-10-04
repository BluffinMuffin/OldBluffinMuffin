package bluffinmuffin.poker.entities;

import bluffinmuffin.poker.entities.type.GameBetLimitType;

/**
 * @author Eric
 * 
 */
public class TableInfoCareer extends TableInfo
{
    public TableInfoCareer()
    {
        super();
    }
    
    public TableInfoCareer(int nbSeats)
    {
        super(nbSeats);
    }
    
    public TableInfoCareer(String pName, int pBigBlind, int nbSeats, GameBetLimitType limit)
    {
        super(pName, pBigBlind, nbSeats, limit);
    }
}
