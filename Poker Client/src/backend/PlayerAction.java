package backend;

import utility.TypePlayerAction;

/**
 * @author Hocus
 *         This class represents the action of a player.
 */
public class PlayerAction
{
    /** Action taken. **/
    public TypePlayerAction m_typeAction;
    /** Amount associated to the action. **/
    public Integer m_actionAmount;
    
    /**
     * Create action taken by a player.
     * 
     * @param p_typeAction
     *            - Action taken.
     */
    public PlayerAction(TypePlayerAction p_typeAction)
    {
        m_typeAction = p_typeAction;
        m_actionAmount = null;
    }
    
    /**
     * Create action taken by a player.
     * 
     * @param p_typeAction
     *            - Action taken.
     * @param p_actionAmount
     *            - Amount associated to the action.
     */
    public PlayerAction(TypePlayerAction p_typeAction, int p_actionAmount)
    {
        m_typeAction = p_typeAction;
        m_actionAmount = p_actionAmount;
    }
}
