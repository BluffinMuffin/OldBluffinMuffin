package backend;

import basePoker.BasePokerPlayer;
import basePoker.TypePlayerAction;
import basePoker.TypePokerRound;
import backend.agent.TypeSimplifiedAction;
import basePoker.Card;

/**
 * @author Hocus
 *         This class represents a poker player on the client side.
 */
public class Player extends BasePokerPlayer
{
    
    
    public TypeSimplifiedAction m_lastActionsPreflop = null;
    public TypeSimplifiedAction m_lastActionsFlop = null;
    public TypeSimplifiedAction m_lastActionsTurn = null;
    public TypeSimplifiedAction m_lastActionsRiver = null;
    
    public Player(int p_noSeat)
    {
        m_noSeat = p_noSeat;
        m_money = 0;
        m_initialMoney = 0;
        m_betAmount = 0;
        m_name = "";
        m_isCutOff = false;
        m_isDealer = false;
        m_isSmallBlind = false;
        m_isBigBlind = false;
        m_isEarlyPos = false;
        m_isMidPos = false;
        m_timeRemaining = 0;
        m_card1 = Card.getInstance().get(-1);
        m_card2 = Card.getInstance().get(-1);
    }
    
    public Player(int p_noSeat, String p_name, int p_money)
    {
        this(p_noSeat);
        m_money = p_money;
        m_name = p_name;
    }
    
    /**
     * Add the action to the player history.
     * 
     * @param p_action
     *            - Action the player has done.
     * @param p_state
     *            - Moment in a poker game.
     */
    public void did(TypePlayerAction p_action, TypePokerRound p_state)
    {
        switch (p_state)
        {
            case PREFLOP:
                m_lastActionsPreflop = manageLastActions(p_action, m_lastActionsPreflop);
                break;
            case FLOP:
                m_lastActionsFlop = manageLastActions(p_action, m_lastActionsFlop);
                break;
            case TURN:
                m_lastActionsTurn = manageLastActions(p_action, m_lastActionsTurn);
                break;
            case RIVER:
                m_lastActionsRiver = manageLastActions(p_action, m_lastActionsRiver);
                break;
            default:
                break;
        }
    }
    
    /**
     * Indicates if the player is folded or not at a specified moment in the
     * poker game.
     * 
     * @param p_state
     *            - Moment in a poker game.
     * @return
     *         If the player is folded.
     */
    public boolean isFolded(TypePokerRound p_state)
    {
        switch (p_state)
        {
            case PREFLOP:
                return ((m_lastActionsPreflop == null) || (m_lastActionsPreflop != TypeSimplifiedAction.FOLD));
            case FLOP:
                return ((m_lastActionsFlop == null) || (m_lastActionsFlop != TypeSimplifiedAction.FOLD));
            case TURN:
                return ((m_lastActionsTurn == null) || (m_lastActionsTurn != TypeSimplifiedAction.FOLD));
            case RIVER:
                return ((m_lastActionsRiver == null) || (m_lastActionsRiver != TypeSimplifiedAction.FOLD));
            default:
                break;
        }
        
        return false;
    }
    
    /**
     * Add action to the player history.
     * 
     * @param p_currentAction
     *            - Action that player has just done.
     * @param p_lastActions
     *            - What the player has done previously.
     * @return
     *         SimplifiedAction depending the current action and previous
     *         actions.
     */
    private TypeSimplifiedAction manageLastActions(TypePlayerAction p_currentAction, TypeSimplifiedAction p_lastActions)
    {
        if (p_currentAction == TypePlayerAction.FOLD)
        {
            return TypeSimplifiedAction.FOLD;
        }
        else if (p_currentAction == TypePlayerAction.CHECK)
        {
            return TypeSimplifiedAction.CHECK;
        }
        else
        {
            if (p_lastActions == null)
            {
                if (p_currentAction == TypePlayerAction.CALL)
                {
                    return TypeSimplifiedAction.CALL;
                }
                else if (p_currentAction == TypePlayerAction.RAISE)
                {
                    return TypeSimplifiedAction.RAISE;
                }
            }
            else if (p_lastActions == TypeSimplifiedAction.CALL)
            {
                if (p_currentAction == TypePlayerAction.RAISE)
                {
                    return TypeSimplifiedAction.CALL_RAISE;
                }
            }
            else if (p_lastActions == TypeSimplifiedAction.RAISE)
            {
                if (p_currentAction == TypePlayerAction.CALL)
                {
                    return TypeSimplifiedAction.RAISE_CALL;
                }
                else if (p_currentAction == TypePlayerAction.RAISE)
                {
                    return TypeSimplifiedAction.RAISE_RAISE;
                }
            }
        }
        
        return p_lastActions;
    }
    
    @Override
    public String toString()
    {
        return m_noSeat + ": " + m_name;
    }
    
}
