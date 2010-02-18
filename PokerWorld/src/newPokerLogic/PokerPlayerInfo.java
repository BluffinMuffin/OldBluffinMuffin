package newPokerLogic;

import gameLogic.GameCard;

public abstract class PokerPlayerInfo
{
    private int m_currentSafeMoneyAmount;
    private int m_currentTablePosition;
    private final GameCard[] m_currentHand = new GameCard[2];
    private String m_playerName;
    
    /**
     * Create a new player named "Anonymous Player" with no money
     */
    public PokerPlayerInfo()
    {
        m_playerName = "Anonymous Player";
        m_currentTablePosition = -1;
        m_currentSafeMoneyAmount = 0;
        setHand(GameCard.NO_CARD, GameCard.NO_CARD);
    }
    
    /**
     * Create a new player with no money
     * 
     * @param p_name
     *            The name of the player
     */
    public PokerPlayerInfo(String p_name)
    {
        this();
        m_playerName = p_name;
    }
    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     */
    public PokerPlayerInfo(String p_name, int p_money)
    {
        this(p_name);
        m_currentSafeMoneyAmount = p_money;
    }
    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     */
    public PokerPlayerInfo(int p_noSeat, String p_name, int p_money)
    {
        this(p_name, p_money);
        m_currentTablePosition = p_noSeat;
    }
    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     */
    public PokerPlayerInfo(int p_noSeat)
    {
        this();
        m_currentTablePosition = p_noSeat;
    }
    
    public void setHand(GameCard card1, GameCard card2)
    {
        m_currentHand[0] = card1;
        m_currentHand[1] = card2;
    }
    
    public int getCurrentSafeMoneyAmount()
    {
        return m_currentSafeMoneyAmount;
    }
    
    public int getCurrentTablePosition()
    {
        return m_currentTablePosition;
    }
    
    public GameCard[] getCurrentHand()
    {
        return m_currentHand;
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
}
