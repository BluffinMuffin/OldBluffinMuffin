package bluffinmuffin.poker.entities;

import bluffinmuffin.poker.entities.type.GameBetLimitType;

/**
 * @author Eric
 * 
 */
public class TableInfoTraining extends TableInfo
{
    private final int m_startingMoney;
    
    public TableInfoTraining()
    {
        super();
        m_startingMoney = 1500;
    }
    
    public TableInfoTraining(int nbSeats)
    {
        super(nbSeats);
        m_startingMoney = 1500;
    }
    
    public TableInfoTraining(String pName, int pBigBlind, int nbSeats, GameBetLimitType limit, int startingMoney)
    {
        super(pName, pBigBlind, nbSeats, limit);
        m_startingMoney = startingMoney;
    }
    
    public int getStartingMoney()
    {
        return m_startingMoney;
    }
}
