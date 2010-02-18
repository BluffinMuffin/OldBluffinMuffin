package newPokerLogic;

import gameLogic.GameCard;

public class PokerTableInfo
{
    private final int m_nbMaxSeats;
    private int m_nbUsedSeats;
    private final PokerPlayerInfo[] m_currentPlayers;
    
    private final int m_smallBlindAmount;
    private final int m_bigBlindAmount;
    
    private final GameCard[] m_currentBoardCards = new GameCard[5];
    
    private final String m_tableName;
    
    public PokerTableInfo()
    {
        this(10);
    }
    
    public PokerTableInfo(int nbSeats)
    {
        this("Anonymous Table", 10, nbSeats);
    }
    
    public PokerTableInfo(String pName, int pBigBlind, int nbSeats)
    {
        m_nbMaxSeats = nbSeats;
        m_nbUsedSeats = 0;
        m_currentPlayers = new PokerPlayerInfo[m_nbMaxSeats];
        m_tableName = pName;
        m_bigBlindAmount = pBigBlind;
        m_smallBlindAmount = pBigBlind / 2;
        setBoardCards(GameCard.NO_CARD, GameCard.NO_CARD, GameCard.NO_CARD, GameCard.NO_CARD, GameCard.NO_CARD);
    }
    
    public void setBoardCards(GameCard c1, GameCard c2, GameCard c3, GameCard c4, GameCard c5)
    {
        m_currentBoardCards[0] = c1;
        m_currentBoardCards[1] = c2;
        m_currentBoardCards[2] = c3;
        m_currentBoardCards[3] = c4;
        m_currentBoardCards[4] = c5;
    }
    
    public int getNbUsedSeats()
    {
        return m_nbUsedSeats;
    }
    
    public void setNbUsedSeats(int nbUsedSeats)
    {
        m_nbUsedSeats = nbUsedSeats;
    }
    
    public int getNbMaxSeats()
    {
        return m_nbMaxSeats;
    }
    
    public PokerPlayerInfo[] getCurrentPlayers()
    {
        return m_currentPlayers;
    }
    
    public int getSmallBlindAmount()
    {
        return m_smallBlindAmount;
    }
    
    public int getBigBlindAmount()
    {
        return m_bigBlindAmount;
    }
    
    public GameCard[] getCurrentBoardCards()
    {
        return m_currentBoardCards;
    }
    
    public String getTableName()
    {
        return m_tableName;
    }
    
    public boolean joinTable(PokerPlayerInfo p)
    {
    	if( m_nbUsedSeats >= m_nbMaxSeats)
    		return false;
    	
    	if(containsPlayer(p))
    		return false;
    	
    	//TODO: ASSIS
    	
    	return true;
    }

	public boolean leaveTable(PokerPlayerInfo p) {
		
    	if(!containsPlayer(p))
    		return false;
    	
    	//TODO: LEAVE
    	
    	return true;
	}
    
    private boolean containsPlayer(PokerPlayerInfo p)
    {
    	//TODO: contains player ??!
    	return false;
    }
}
