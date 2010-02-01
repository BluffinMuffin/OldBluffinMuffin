package backend.agent;

import utility.TypePlayerAction;

public class PlayerAction
{
    public TypePlayerAction m_typeAction;
    public Integer m_actionAmount;
    
    public PlayerAction(TypePlayerAction p_typeAction)
    {
        m_typeAction = p_typeAction;
        m_actionAmount = null;
    }
    
    public PlayerAction(TypePlayerAction p_typeAction, int p_actionAmount)
    {
        m_typeAction = p_typeAction;
        m_actionAmount = p_actionAmount;
    }
}
