package basePoker;


/**
 * @author Hocus
 *         This class represent the action of a player.
 *         It consist of a type (Call, Check ...) and the amount associated to the action.
 */
public class PokerPlayerAction
{
    
    private TypePlayerAction m_typeAction;
    private int m_amountAmount;
    
    /**
     * Create an action of the type UNKNOWN with an amount of 0
     */
    public PokerPlayerAction()
    {
        this(TypePlayerAction.UNKNOWN, 0);
    }
    
    /**
     * Create a new atcion
     * 
     * @param p_type
     *            Type of the action
     * @param p_amount
     *            Amount of the action
     */
    public PokerPlayerAction(TypePlayerAction p_type, int p_amount)
    {
        m_typeAction = p_type;
        m_amountAmount = p_amount;
    }
    
    public PokerPlayerAction(TypePlayerAction p_type) {
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
    public TypePlayerAction getType()
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
    public void setType(TypePlayerAction p_type)
    {
        m_typeAction = p_type;
    }
    
    /**
     * Create a string representing this action
     */
    @Override
    public String toString()
    {
        String result = "";
        
        switch (m_typeAction)
        {
            case CALL:
                result = "Call " + m_amountAmount;
                break;
            case CHECK:
                result = "Check";
                break;
            case FOLD:
                result = "Fold";
                break;
            case RAISE:
                result = "Raise to " + m_amountAmount;
                break;
            case NOTHING:
                result = "Do nothing";
                break;
            case UNKNOWN:
                result = "Unknow Action";
                break;
        }
        
        return result;
    }
}
