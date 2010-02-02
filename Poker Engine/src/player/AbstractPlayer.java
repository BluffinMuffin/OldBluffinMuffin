package player;

import java.util.ArrayList;

import tools.PokerUtil;
import backend.HoldEmTable;
import basePoker.Card;
import basePoker.PokerPlayerAction;
import basePoker.TypePlayerAction;
import handEvaluation.HandEvalHoldem;

/**
 * @author HOCUS
 *         Abstract class implementing the basic behaviour of a player.
 *         To make a working player, we only need to override the getActionFromUser method.
 */
public abstract class AbstractPlayer implements IPlayer
{
    protected String m_name;
    protected int m_money;
    protected int m_position;
    protected int m_currentBet;
    protected boolean m_isPlaying;
    protected boolean m_isFolded;
    protected boolean m_isDealer;
    protected boolean m_isSmallBlind;
    protected boolean m_isBigBlind;
    protected boolean m_cardShowed;
    protected boolean m_sitOutNextHand;
    protected ArrayList<Card> m_hand;
    protected HoldEmTable m_table;
    
    /**
     * Create a new player named "Anonymous Player" with no money
     */
    public AbstractPlayer()
    {
        this("Anonymous Player");
    }
    
    /**
     * Create a new player with no money
     * 
     * @param p_name
     *            The name of the player
     */
    public AbstractPlayer(String p_name)
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
    public AbstractPlayer(String p_name, int p_money)
    {
        m_name = p_name;
        m_money = p_money;
        m_isPlaying = false;
        m_isFolded = false;
        m_isDealer = false;
        m_isSmallBlind = false;
        m_isBigBlind = false;
        m_sitOutNextHand = false;
        m_hand = new ArrayList<Card>();
    }
    
    @Override
    public void addMoney(int p_amount)
    {
        m_money += p_amount;
    }
    
    @Override
    public boolean canStartGame()
    {
        if ((m_money <= 0) || m_isPlaying)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public void endGame()
    {
        m_isPlaying = false;
        m_isSmallBlind = false;
        m_isBigBlind = false;
        m_isDealer = false;
        m_hand.clear();
    }
    
    @Override
    public void endTurn()
    {
        m_currentBet = 0;
    }
    
    @Override
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof String)
        {
            return this.getName().equals(arg0);
        }
        
        if (arg0 instanceof IPlayer)
        {
            return this.getName().equals(((IPlayer) arg0).getName());
        }
        
        return super.equals(arg0);
    }
    
    /**
     * This method is called when an decision is needed
     * 
     * @param p_canCheck
     *            If the player can check
     * @param p_canFold
     *            if the player can fold
     * @param p_canCall
     *            if the player can call
     * @param p_callOf
     *            The amount needed to call
     * @param p_canRaise
     *            if the player can raise
     * @param p_minimumRaise
     *            the minimum raise amount
     * @param p_maximumRaise
     *            the maximum raise amount
     * @return
     *         The action taken
     */
    abstract protected PokerPlayerAction getActionFromUser(boolean p_canCheck, boolean p_canFold, boolean p_canCall, int p_callOf, boolean p_canRaise, int p_minimumRaise, int p_maximumRaise);
    
    @Override
    public int getBet()
    {
        return m_currentBet;
    }
    
    @Override
    public ArrayList<Card> getHand()
    {
        return m_hand;
    }
    
    @Override
    public int getMoney()
    {
        return m_money;
    }
    
    @Override
    public String getName()
    {
        return m_name;
    }
    
    @Override
    public HoldEmTable getTable()
    {
        return m_table;
    }
    
    @Override
    public int getTablePosition()
    {
        return m_position;
    }
    
    @Override
    public long handValue(Card[] p_board)
    {
        final ArrayList<Card> hand = new ArrayList<Card>(m_hand);
        for (final Card element : p_board)
        {
            hand.add(element);
        }
        return HandEvalHoldem.get7CardsHandValue(hand);
    }
    
    @Override
    public boolean isAllIn()
    {
        return (m_isPlaying && (m_money <= 0));
    }
    
    @Override
    public boolean isBigBlind()
    {
        return m_isBigBlind;
    }
    
    @Override
    public boolean isDealer()
    {
        return m_isDealer;
    }
    
    @Override
    public boolean isFolded()
    {
        return m_isFolded;
    }
    
    @Override
    public boolean isPlaying()
    {
        return m_isPlaying;
    }
    
    @Override
    public boolean isShowingCard()
    {
        return m_cardShowed;
    }
    
    @Override
    public boolean isSmallBlind()
    {
        return m_isSmallBlind;
    }
    
    @Override
    public boolean placeBigBlind(int p_bigBlind)
    {
        if (m_money > p_bigBlind)
        {
            m_money -= p_bigBlind;
            m_currentBet = p_bigBlind;
            return true;
        }
        else
        {
            m_currentBet = m_money;
            m_money = 0;
            return true;
        }
    }
    
    @Override
    public boolean placeSmallBlind(int p_smallBlind)
    {
        if (m_money > p_smallBlind)
        {
            m_money -= p_smallBlind;
            m_currentBet = p_smallBlind;
            return true;
        }
        else
        {
            m_currentBet = m_money;
            m_money = 0;
            return true;
        }
    }
    
    @Override
    public void receiveCard(Card p_card)
    {
        m_hand.add(p_card);
    }
    
    @Override
    public void setIsBigBlind(boolean p_flag)
    {
        m_isBigBlind = p_flag;
    }
    
    @Override
    public void setIsDealer(boolean p_flag)
    {
        m_isDealer = p_flag;
    }
    
    @Override
    public void setIsFolded(boolean p_flag)
    {
        m_isFolded = p_flag;
    }
    
    @Override
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
    
    /**
     * Make this player sit out at next hand if the flag is true.
     * 
     * @param p_flag
     *            if the player is sitting out next hand
     */
    public void setSitOut(boolean p_flag)
    {
        m_sitOutNextHand = p_flag;
    }
    
    @Override
    public void setTable(HoldEmTable p_table)
    {
        m_table = p_table;
    }
    
    @Override
    public void setTablePosition(int p_position)
    {
        m_position = p_position;
    }
    
    @Override
    public void showCards()
    {
        m_cardShowed = true;
    }
    
    @Override
    public boolean sitOutNextHand()
    {
        return m_sitOutNextHand;
    }
    
    @Override
    public void startGame()
    {
        m_currentBet = 0;
        m_isPlaying = true;
        m_isFolded = false;
        m_cardShowed = false;
        m_hand.clear();
    }
    
    @Override
    public PokerPlayerAction takeAction(int p_betOnTable, int p_minimumRaise, int p_maximumRaise)
    {
        if (this.isAllIn())
        {
            return new PokerPlayerAction(TypePlayerAction.NOTHING, 0);
        }
        
        final boolean canCheck = p_betOnTable <= m_currentBet;
        int callOf = p_betOnTable - m_currentBet;
        if (callOf > m_money)
        {
            callOf = m_money;
        }
        final boolean canRaise = p_minimumRaise <= m_currentBet + m_money;
        final int minimumRaise = (canRaise) ? p_minimumRaise : 0;
        final int maximumRaise = (canRaise) ? p_maximumRaise : 0;
        
        final PokerPlayerAction action = getActionFromUser(canCheck, !canCheck, !canCheck, callOf, canRaise, minimumRaise, maximumRaise);
        
        if (action.getType() == TypePlayerAction.FOLD)
        {
            m_isFolded = true;
        }
        else if (action.getType() == TypePlayerAction.CALL)
        {
            m_money -= action.getAmount();
            m_currentBet += action.getAmount();
        }
        else if (action.getType() == TypePlayerAction.RAISE)
        {
            m_money -= (action.getAmount() - m_currentBet);
            m_currentBet = action.getAmount();
        }
        
        return action;
    }
    
    @Override
    public void winPot(int p_potValue)
    {
        m_money += p_potValue;
    }
    
}