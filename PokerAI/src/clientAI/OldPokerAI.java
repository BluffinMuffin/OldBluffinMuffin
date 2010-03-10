package clientAI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.OldPokerTableInfo;
import pokerLogic.OldTypePlayerAction;
import utility.IClosingListener;
import clientGame.ClientPokerTableInfo;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;
import clientGameTools.IClientPoker;
import clientGameTools.IClientPokerActionner;

/**
 * @author Hocus
 *         This class represents an artificial poker player.
 *         All it does is check/fold when it is asked to take an action. <br>
 * <br>
 *         For more interesting artificial poker player see derivates.
 * @see OldPokerBasic
 * @see OldPokerGeneticAI
 * @see OldPokerGeneticBasic
 * @see OldPokerSVM
 * 
 */
public class OldPokerAI implements IClientPokerActionner
{
    private final List<IClosingListener<IClientPoker>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<IClientPoker>>());
    private ClientPokerObserver m_pokerObserver;
    /** Indicates the agent is still running. **/
    private boolean m_isRunning = true;
    
    /** Represents the poker table the agent is playing on. **/
    protected ClientPokerTableInfo m_table = null;
    /** Is the next action the agent has decided to take. **/
    private final OldPokerPlayerAction m_playerAction = new OldPokerPlayerAction(OldTypePlayerAction.NOTHING);
    /** Indicates if new infos are available to the agent. **/
    Boolean m_newInfos = false;
    
    /** Mutex to change m_newInfos. **/
    Object m_mutexNewInfos = new Object();
    /** Array containing all allowed actions to take the decision. **/
    protected ArrayList<OldTypePlayerAction> m_actionsAllowed = new ArrayList<OldTypePlayerAction>();
    /** Amount to call. **/
    protected int m_callAmount;
    /** Minimum amount the agent must raise to. **/
    protected int m_minRaiseAmount;
    /** Maximum amount the agent can raise to. **/
    protected int m_maxRaiseAmount;
    
    public OldPokerAI()
    {
    }
    
    /**
     * This methods will be call once the agent made its decision.
     * 
     * @param p_actionTaken
     *            - Action the agent decided to take.
     */
    private void actionTaken(OldPokerPlayerAction p_actionTaken)
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
    public void addClosingListener(IClosingListener<IClientPoker> p_listener)
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
    protected OldPokerPlayerAction analyze(ArrayList<OldTypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        if (p_actionsAllowed.contains(OldTypePlayerAction.CHECK))
        {
            return new OldPokerPlayerAction(OldTypePlayerAction.CHECK);
        }
        
        return new OldPokerPlayerAction(OldTypePlayerAction.FOLD);
    }
    
    /**
     * Take the action of disconnecting.
     */
    public void disconnect()
    {
        actionTaken(new OldPokerPlayerAction(OldTypePlayerAction.DISCONNECT));
    }
    
    @Override
    public OldPokerPlayerAction getAction()
    {
        final OldPokerPlayerAction actionTaken = new OldPokerPlayerAction(OldTypePlayerAction.NOTHING);
        
        synchronized (m_playerAction)
        {
            try
            {
                // Be sure, the agent has not take its action at this point.
                if (m_playerAction.getType() == OldTypePlayerAction.NOTHING)
                {
                    m_playerAction.wait();
                }
                
                // Look the decision taken and prepare to return it.
                actionTaken.setType(m_playerAction.getType());
                actionTaken.setAmount(m_playerAction.getAmount());
                m_playerAction.setType(OldTypePlayerAction.NOTHING);
            }
            catch (final InterruptedException e)
            {
            }
        }
        
        return actionTaken;
    }
    
    @Override
    public void removeClosingListener(IClosingListener<IClientPoker> p_listener)
    {
        m_closingListeners.remove(p_listener);
    }
    
    public void run()
    {
        while (m_isRunning)
        {
            OldPokerPlayerAction action = null;
            
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
    public void setTable(OldPokerTableInfo p_table)
    {
        m_table = (ClientPokerTableInfo) p_table;
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
    public void takeAction(ArrayList<OldTypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount)
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
    
    public void setPokerObserver(ClientPokerObserver observer)
    {
        m_pokerObserver = observer;
        initializePokerObserver();
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new ClientPokerAdapter()
        {
            
            @Override
            public void boardChanged(ArrayList<Integer> boardCardIndices)
            {
                synchronized (m_mutexNewInfos)
                {
                    m_newInfos = true;
                    m_mutexNewInfos.notify();
                }
            }
            
            @Override
            public void playerCardChanged(OldPokerPlayerInfo player)
            {
                if (player == m_table.m_localPlayer)
                {
                    synchronized (m_mutexNewInfos)
                    {
                        m_newInfos = true;
                        m_mutexNewInfos.notify();
                    }
                }
            }
            
            @Override
            public void tableClosed()
            {
                disconnect();
            }
            
        });
        
    }
}
