package backend.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import miscUtil.IClosingListener;

import basePoker.BasePokerPlayer;
import basePoker.BasePokerTable;
import basePoker.TypePlayerAction;
import basePoker.TypePokerRound;
import basePokerAI.IPokerAgent;
import basePokerAI.IPokerAgentActionner;
import basePokerAI.IPokerAgentListener;
import backend.Player;
import basePoker.PokerPlayerAction;
import backend.Table;

/**
 * @author Hocus
 *         This class represents an artificial poker player.
 *         All it does is check/fold when it is asked to take an action. <br>
 * <br>
 *         For more interesting artificial poker player see derivates.
 * @see PokerBasic
 * @see PokerGeneticAI
 * @see PokerGeneticBasic
 * @see PokerSVM
 * 
 */
public class PokerAI implements IPokerAgentListener, IPokerAgentActionner
{
    private final List<IClosingListener<IPokerAgent>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<IPokerAgent>>());
    
    /** Indicates the agent is still running. **/
    private boolean m_isRunning = true;
    
    /** Represents the poker table the agent is playing on. **/
    protected BasePokerTable m_table = null;
    /** Is the next action the agent has decided to take. **/
    private final PokerPlayerAction m_playerAction = new PokerPlayerAction(TypePlayerAction.NOTHING);
    /** Indicates if new infos are available to the agent. **/
    Boolean m_newInfos = false;
    
    /** Mutex to change m_newInfos. **/
    Object m_mutexNewInfos = new Object();
    /** Array containing all allowed actions to take the decision. **/
    protected ArrayList<TypePlayerAction> m_actionsAllowed = new ArrayList<TypePlayerAction>();
    /** Amount to call. **/
    protected int m_callAmount;
    /** Minimum amount the agent must raise to. **/
    protected int m_minRaiseAmount;
    /** Maximum amount the agent can raise to. **/
    protected int m_maxRaiseAmount;
    
    public PokerAI()
    {
    }
    
    /**
     * This methods will be call once the agent made its decision.
     * 
     * @param p_actionTaken
     *            - Action the agent decided to take.
     */
    private void actionTaken(PokerPlayerAction p_actionTaken)
    {
        System.out.println("Taking action: " + p_actionTaken.getType().toString());
        synchronized (m_playerAction)
        {
            m_playerAction.setType(p_actionTaken.getType());
            m_playerAction.setAmount(p_actionTaken.getAmount());
            m_playerAction.notify();
        }
    }
    
    @Override
    public void addClosingListener(IClosingListener<IPokerAgent> p_listener)
    {
        m_closingListeners.add(p_listener);
    }
    
    /**
     * Analyze available informations on the poker table and
     * chose the action to do.
     * 
     * @param p_actionsAllowed
     *            - Array containing allowed actions the agent can do.
     * @param p_minRaiseAmount
     *            - Minimum amount the agent must raise to.
     * @param p_maxRaiseAmount
     *            - Maximum amount the agent can raise to.
     * @return
     *         Action the agent choose.
     */
    protected PokerPlayerAction analyze(ArrayList<TypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        if (p_actionsAllowed.contains(TypePlayerAction.CHECK))
        {
            return new PokerPlayerAction(TypePlayerAction.CHECK);
        }
        
        return new PokerPlayerAction(TypePlayerAction.FOLD);
    }
    
    @Override
    public void betTurnEnded(ArrayList<Integer> p_potIndices, TypePokerRound p_gameState)
    {
    }
    
    @Override
    public void boardChanged(ArrayList<Integer> p_boardCardIndices)
    {
        synchronized (m_mutexNewInfos)
        {
            m_newInfos = true;
            m_mutexNewInfos.notify();
        }
    }
    
    /**
     * Take the action of disconnecting.
     */
    public void disconnect()
    {
        actionTaken(new PokerPlayerAction(TypePlayerAction.DISCONNECT));
    }
    
    @Override
    public void gameEnded()
    {
    }
    
    @Override
    public void gameStarted(BasePokerPlayer p_oldDealer, BasePokerPlayer p_oldSmallBlind, BasePokerPlayer p_oldBigBlind)
    {
    }
    
    @Override
    public PokerPlayerAction getAction()
    {
        final PokerPlayerAction actionTaken = new PokerPlayerAction(TypePlayerAction.NOTHING);
        
        synchronized (m_playerAction)
        {
            try
            {
                // Be sure, the agent has not take its action at this point.
                if (m_playerAction.getType() == TypePlayerAction.NOTHING)
                {
                    m_playerAction.wait();
                }
                
                // Look the decision taken and prepare to return it.
                actionTaken.setType(m_playerAction.getType());
                actionTaken.setAmount(m_playerAction.getAmount());
                m_playerAction.setType(TypePlayerAction.NOTHING);
            }
            catch (final InterruptedException e)
            {
            }
        }
        
        return actionTaken;
    }
    
    @Override
    public void playerCardChanged(BasePokerPlayer p_player)
    {
        if (p_player == m_table.m_localPlayer)
        {
            synchronized (m_mutexNewInfos)
            {
                m_newInfos = true;
                m_mutexNewInfos.notify();
            }
        }
    }
    
    @Override
    public void playerJoined(BasePokerPlayer p_player)
    {
    }
    
    @Override
    public void playerLeft(BasePokerPlayer p_player)
    {
    }
    
    @Override
    public void playerMoneyChanged(BasePokerPlayer p_player, int p_oldMoneyAmount)
    {
    }
    
    @Override
    public void playerTurnBegan(BasePokerPlayer p_oldCurrentPlayer)
    {
    }
    
    @Override
    public void playerTurnEnded(BasePokerPlayer p_player, TypePlayerAction p_action, int p_actionAmount)
    {
    }
    
    @Override
    public void potWon(BasePokerPlayer p_player, int p_potAmountWon, int p_potIndex)
    {
    }
    
    @Override
    public void removeClosingListener(IClosingListener<IPokerAgent> p_listener)
    {
        m_closingListeners.remove(p_listener);
    }
    
    public void run()
    {
        while (m_isRunning)
        {
            PokerPlayerAction action = null;
            
            try
            {
                synchronized (m_mutexNewInfos)
                {
                    // Wait for new infos to arrive.
                    if (!m_newInfos)
                    {
                        System.out.println("Waiting for new infos");
                        m_mutexNewInfos.wait();
                    }
                    
                    // If m_actionsAllowed is empty, it means that the agent
                    // has already chosen its action.
                    if (!m_actionsAllowed.isEmpty())
                    {
                        // Take the decision.
                        action = analyze(m_actionsAllowed, m_minRaiseAmount, m_maxRaiseAmount);
                        m_actionsAllowed.clear();
                    }
                    
                    // New infos have been processed.
                    m_newInfos = false;
                }
                
                if (action != null)
                {
                    actionTaken(action);
                }
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void setTable(BasePokerTable p_table)
    {
        m_table = p_table;
    }
    
    @Override
    public void start()
    {
        final Thread t = new Thread(this);
        t.setName(Thread.currentThread().getName() + " [Brain]");
        t.start();
    }
    
    @Override
    public void stop()
    {
        m_isRunning = false;
    }
    
    @Override
    public void tableClosed()
    {
        disconnect();
    }
    
    @Override
    public void tableInfos()
    {
    }
    
    @Override
    public void takeAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        synchronized (m_mutexNewInfos)
        {
            m_actionsAllowed = p_actionsAllowed;
            m_minRaiseAmount = p_minRaiseAmount;
            m_maxRaiseAmount = p_maxRaiseAmount;
            m_callAmount = p_callAmount;
            
            m_newInfos = true;
            m_mutexNewInfos.notify();
        }
    }
    
    @Override
    public String toString()
    {
        return "PokerAI";
    }
    
    @Override
    public void waitingForPlayers()
    {
    }
}
