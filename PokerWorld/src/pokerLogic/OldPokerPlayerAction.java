package pokerLogic;

/**
 * @author Hocus
 *         This class represent the action of a player.
 *         It consist of a type (Call, Check ...) and the amount associated to the action.
 */
public class OldPokerPlayerAction
{
    
    private OldTypePlayerAction m_typeAction;
    private int m_amountAmount;
    
    /**
     * Create an action of the type UNKNOWN with an amount of 0
     */
    public OldPokerPlayerAction()
    {
        this(OldTypePlayerAction.UNKNOWN, 0);
    }
    
    /**
     * Create a new atcion
     * 
     * @param p_type
     *            Type of the action
     * @param p_amount
     *            Amount of the action
     */
    public OldPokerPlayerAction(OldTypePlayerAction p_type, int p_amount)
    {
        m_typeAction = p_type;
        m_amountAmount = p_amount;
    }
    
    public OldPokerPlayerAction(OldTypePlayerAction p_type)
    {
        m_typeAction = p_type;
        m_amountAmount = 0;
    }
    
    /**
     * Return the amount of the action
     * 
     * @return
     *         The amount of the action
     */
    public int getAmount()
    {
        return m_amountAmount;
    }
    
    /**
     * Return the type of the action
     * 
     * @return
     *         Type of the action
     */
    public OldTypePlayerAction getType()
    {
        return m_typeAction;
    }
    
    /**
     * Change the amount
     * 
     * @param p_amount
     *            The new amount
     */
    public void setAmount(int p_amount)
    {
        m_amountAmount = p_amount;
    }
    
    /**
     * Change the type of the action
     * 
     * @param p_type
     *            The new type
     */
    public void setType(OldTypePlayerAction p_type)
    {
        m_typeAction = p_type;
    }
    
    /**
     * Create a string representing this action
     */
    @Override
    public String toString()
    {
        String result = "is eating 42 gummy bears";
        
        switch (m_typeAction)
        {
            case CALL:
                result = "call $" + m_amountAmount;
                break;
            case CHECK:
                result = "checks";
                break;
            case FOLD:
                result = "folds";
                break;
            case RAISE:
                result = "raises to $" + m_amountAmount;
                break;
            case BIG_BLIND:
                result = "post the big blind";
                break;
            case SMALL_BLIND:
                result = "post the small blind";
                break;
        }
        
        return result;
    }
}
