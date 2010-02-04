package pokerAI;

import java.util.ArrayList;

import pokerLogic.PokerPlayerAction;
import pokerLogic.TypePlayerAction;


/**
 * @author Hocus
 *         This interface represents a poker agent that can
 *         interact in the game (can take actions).
 */
public interface IPokerAgentActionner extends IPokerAgent
{
    /**
     * Request an action from the agent.
     * 
     * @return the action taken by the agent.
     */
    public PokerPlayerAction getAction();
    
    /**
     * Happens when it is to the client to make a move.
     * 
     * @param p_actionsAllowed
     *            contains the list of actions allowed.
     * @param p_callAmount
     *            is the amount for calling.
     * @param p_minRaiseAmount
     *            is the minimum raise allowed.
     * @param p_maxRaiseAmount
     *            is the maximum raise allowed.
     */
    public void takeAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount);
}
