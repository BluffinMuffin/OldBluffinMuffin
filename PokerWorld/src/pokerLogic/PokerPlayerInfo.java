package pokerLogic;

import java.util.ArrayList;

import newPokerLogic.GameCard;
import newPokerLogic.GameCardSet;
import newPokerLogic.HandEvaluator;

public abstract class PokerPlayerInfo
{
    private int m_money;
    
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
    private int m_noSeat;
    private final ArrayList<GameCard> m_hand;
    
    /**
     * Create a new player named "Anonymous Player" with no money
     */
    public PokerPlayerInfo()
    {
        m_name = "Anonymous Player";
        m_money = 0;
        m_initialMoney = 0;
        m_betAmount = 0;
        m_isPlaying = false;
        m_isCutOff = false;
        m_isDealer = false;
        m_isSmallBlind = false;
        m_isBigBlind = false;
        m_isEarlyPos = false;
        m_isMidPos = false;
        m_timeRemaining = 0;
        m_hand = new ArrayList<GameCard>();
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
        m_name = p_name;
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
        m_money = p_money;
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
        setNoSeat(p_noSeat);
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
        setNoSeat(p_noSeat);
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
    
    @Override
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof String)
        {
            return this.getName().equals(arg0);
        }
        
        if (arg0 instanceof PokerPlayerInfo)
        {
            return this.getName().equals(((PokerPlayerInfo) arg0).getName());
        }
        
        return super.equals(arg0);
    }
    
    public int getBet()
    {
        return m_betAmount;
    }
    
    public GameCard[] getHand()
    {
        final GameCard[] dest = new GameCard[2];
        if (m_hand.size() < 2)
        {
            setHand(GameCard.NO_CARD, GameCard.NO_CARD);
        }
        dest[0] = m_hand.get(0);
        dest[1] = m_hand.get(1);
        return dest;
    }
    
    public void setHand(GameCard card1, GameCard card2)
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
    
    public long handValue(GameCard[] p_board)
    {
        final ArrayList<GameCard> hand = new ArrayList<GameCard>(m_hand);
        for (final GameCard element : p_board)
        {
            hand.add(element);
        }
        
        return HandEvaluator.hand7Eval(HandEvaluator.encode(new GameCardSet(hand)));
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
    
    public void setNoSeat(int m_noSeat)
    {
        this.m_noSeat = m_noSeat;
    }
    
    public int getNoSeat()
    {
        return m_noSeat;
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
