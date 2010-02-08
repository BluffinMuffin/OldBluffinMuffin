package serverGame;

import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;

/**
 * @author HOCUS
 *         Abstract class implementing the basic behaviour of a player.
 *         To make a working player, we only need to override the getActionFromUser method.
 */
public abstract class ServerPokerPlayerInfo extends PokerPlayerInfo
{
    protected int m_position;
    protected boolean m_isFolded;
    protected boolean m_cardShowed;
    protected boolean m_sitOutNextHand;
    protected ServerTableCommunicator m_table;
    
    /**
     * Create a new player named "Anonymous Player" with no money
     */
    public ServerPokerPlayerInfo()
    {
        this("Anonymous Player");
    }
    
    /**
     * Create a new player with no money
     * 
     * @param p_name
     *            The name of the player
     */
    public ServerPokerPlayerInfo(String p_name)
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
    public ServerPokerPlayerInfo(String p_name, int p_money)
    {
        super(p_name, p_money);
        m_isPlaying = false;
        m_isFolded = false;
    }
    
    public ServerTableCommunicator getTable()
    {
        return m_table;
    }
    
    public int getTablePosition()
    {
        return m_position;
    }
    
    public boolean isFolded()
    {
        return m_isFolded;
    }
    
    public boolean isShowingCard()
    {
        return m_cardShowed;
    }
    
    public void setIsFolded(boolean p_flag)
    {
        m_isFolded = p_flag;
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
    
    public void setTable(ServerTableCommunicator p_table)
    {
        m_table = p_table;
    }
    
    public void setTablePosition(int p_position)
    {
        m_position = p_position;
    }
    
    public void showCards()
    {
        m_cardShowed = true;
    }
    
    public boolean sitOutNextHand()
    {
        return m_sitOutNextHand;
    }
    
    @Override
    public void startGame()
    {
        super.startGame();
        m_isFolded = false;
        m_cardShowed = false;
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
    
    public PokerPlayerAction takeAction(int p_betOnTable, int p_minimumRaise, int p_maximumRaise)
    {
        if (this.isAllIn())
        {
            return new PokerPlayerAction(TypePlayerAction.NOTHING, 0);
        }
        
        final boolean canCheck = p_betOnTable <= m_betAmount;
        int callOf = p_betOnTable - m_betAmount;
        if (callOf > m_money)
        {
            callOf = m_money;
        }
        final boolean canRaise = p_minimumRaise <= m_betAmount + m_money;
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
            m_betAmount += action.getAmount();
        }
        else if (action.getType() == TypePlayerAction.RAISE)
        {
            m_money -= (action.getAmount() - m_betAmount);
            m_betAmount = action.getAmount();
        }
        
        return action;
    }
    
}