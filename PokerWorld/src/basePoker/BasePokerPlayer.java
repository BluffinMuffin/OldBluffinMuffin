package basePoker;

import handEvaluation.HandEvalHoldem;

import java.util.ArrayList;

public abstract class BasePokerPlayer {
	public int m_money;
    public int m_initialMoney;
    public int m_betAmount;
    public boolean m_isCutOff;
    public boolean m_isDealer;
    public boolean m_isSmallBlind;
    public boolean m_isBigBlind;
    public boolean m_isEarlyPos;
    public boolean m_isMidPos;
    public int m_timeRemaining;
    public int m_relativePosition;
    public boolean m_isPlaying;
    public String m_name;
    public int m_noSeat;
    private ArrayList<Card> m_hand;
    
    /**
     * Create a new player named "Anonymous Player" with no money
     */
    public BasePokerPlayer()
    {
        this("Anonymous Player");
    }
    
    /**
     * Create a new player with no money
     * 
     * @param p_name
     *            The name of the player
     */
    public BasePokerPlayer(String p_name)
    {
        this(p_name, 0);
    }
    

    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     */
    public BasePokerPlayer(String p_name, int p_money)
    {
        m_name = p_name;
        m_money = p_money;
        m_isPlaying = false;
        m_isDealer = false;
        m_isSmallBlind = false;
        m_isBigBlind = false;
        m_hand = new ArrayList<Card>();
    }
    public void addMoney(int p_amount)
    {
        m_money += p_amount;
    }
    public boolean canStartGame()
    {
        if ((m_money <= 0) || m_isPlaying)
        {
            return false;
        }
        return true;
    }
    public void endGame()
    {
        m_isPlaying = false;
        m_isSmallBlind = false;
        m_isBigBlind = false;
        m_isDealer = false;
        m_hand.clear();
    }
    public void endTurn()
    {
        m_betAmount = 0;
    }
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof String)
        {
            return this.getName().equals(arg0);
        }
        
        if (arg0 instanceof BasePokerPlayer)
        {
            return this.getName().equals(((BasePokerPlayer) arg0).getName());
        }
        
        return super.equals(arg0);
    }
       public int getBet()
    {
        return m_betAmount;
    }
    public Card[] getHand()
    {
    	Card[] dest = new Card[2];
    	if(m_hand.size()<2)
    		setHand(Card.getInstance().get(-1), Card.getInstance().get(-1));
    	dest[0] = m_hand.get(0);
    	dest[1] = m_hand.get(1);
        return dest;
    }
    public void setHand(Card card1, Card card2)
    {
        m_hand.clear();
        m_hand.add(card1);
        m_hand.add(card2);
    }
    public int getMoney()
    {
        return m_money;
    }
    public String getName()
    {
        return m_name;
    }

    public long handValue(Card[] p_board)
    {
        final ArrayList<Card> hand = new ArrayList<Card>(m_hand);
        for (final Card element : p_board)
        {
            hand.add(element);
        }
        return HandEvalHoldem.get7CardsHandValue(hand);
    }
    public boolean isAllIn()
    {
        return (m_isPlaying && (m_money <= 0));
    }
    public boolean isBigBlind()
    {
        return m_isBigBlind;
    }
    public boolean isDealer()
    {
        return m_isDealer;
    }
    
    public boolean isPlaying()
    {
        return m_isPlaying;
    }
    
    public boolean isSmallBlind()
    {
        return m_isSmallBlind;
    }
    
    public boolean placeBigBlind(int p_bigBlind)
    {
        if (m_money > p_bigBlind)
        {
            m_money -= p_bigBlind;
            m_betAmount = p_bigBlind;
            return true;
        }
        else
        {
            m_betAmount = m_money;
            m_money = 0;
            return true;
        }
    }
    
    public boolean placeSmallBlind(int p_smallBlind)
    {
        if (m_money > p_smallBlind)
        {
            m_money -= p_smallBlind;
            m_betAmount = p_smallBlind;
            return true;
        }
        else
        {
            m_betAmount = m_money;
            m_money = 0;
            return true;
        }
    }
    
    public void setIsBigBlind(boolean p_flag)
    {
        m_isBigBlind = p_flag;
    }
    
    public void setIsDealer(boolean p_flag)
    {
        m_isDealer = p_flag;
    }
    
    public void setIsSmallBlind(boolean p_flag)
    {
        m_isSmallBlind = p_flag;
    }
    
    /**
     * Change the amount of chips of the player
     * 
     * @param p_money
     *            New amount of chips
     */
    public void setMoney(int p_money)
    {
        m_money = p_money;
    }
    
    public void winPot(int p_potValue)
    {
        m_money += p_potValue;
    }
    
    public void startGame()
    {
        m_betAmount = 0;
        m_isPlaying = true;
        m_hand.clear();
    }
    
}
