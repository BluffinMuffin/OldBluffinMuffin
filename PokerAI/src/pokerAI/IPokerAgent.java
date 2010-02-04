package pokerAI;

import pokerLogic.PokerTableInfo;
import utility.IClosingListener;

/**
 * @author Hocus
 *         This interface represents a poker agent.
 */
public interface IPokerAgent extends Runnable
{
    /**
     * Add a closing listener to the agent.
     * 
     * @param p_listener
     *            - Will be notified when agent is stopping.
     */
    public void addClosingListener(IClosingListener<IPokerAgent> p_listener);
    
    /**
     * Remove a closing listener from the agent.
     * 
     * @param p_listener
     *            - Listener to be removed.
     */
    public void removeClosingListener(IClosingListener<IPokerAgent> p_listener);
    
    /**
     * Set the table that the agent is listening to.
     * 
     * @param p_table
     *            - Reference to the Poker table.
     */
    public void setTable(PokerTableInfo p_table);
    
    /**
     * Start the agent.
     */
    public void start();
    
    /**
     * Stop the agent.
     */
    public void stop();
}
