package clientGameTools;

import java.util.ArrayList;

import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldTypePlayerAction;


/**
 * @author Hocus
 *         This interface represents a poker agent that can
 *         interact in the game (can take actions).
 */
public interface IClientPokerActionner extends IClientPoker
{
    /**
     * Request an action from the agent.
     * 
     * @return the action taken by the agent.
     */
    public OldPokerPlayerAction getAction();
    
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
    public void takeAction(ArrayList<OldTypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount);
}
