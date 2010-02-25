package pokerClientGameStats;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

import pokerClientGame.ClientPokerPlayerInfo;
import pokerClientGameTools.ClientPokerAdapter;
import pokerClientGameTools.ClientPokerObserver;
import pokerClientGameTools.IClientPoker;
import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.OldPokerTableInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import utility.Constants;

/**
 * StatsAgent.java
 * 
 * @author Hocus
 *         Description: This class log all kind of statistics related to a game
 *         of poker
 */
public class StatsAgent implements IClientPoker
{
    private ClientPokerObserver m_pokerObserver;
    // Playing table
    protected OldPokerTableInfo m_table;
    
    // Stats overall on every player we played against
    public TreeMap<String, PlayerStats> m_overallStats = new TreeMap<String, PlayerStats>();
    
    // Stats for this round
    private final TreeMap<String, PlayerStats> m_gameStats = new TreeMap<String, PlayerStats>();
    
    // Preflop=0 Flop=1 etc..
    int m_turnType;
    
    boolean m_gatheringStats = false;
    
    // Has everyone limped before us?
    boolean m_isUnopenedPot;
    
    // Did someone raised?
    boolean m_isStreetRaised;
    
    // The last person who raised on the last street
    PlayerStats m_lastStreetRaiser;
    
    // What is the current amount of Bet (1 2 3 ...)
    int m_nbBet;
    
    // Is there a CBet going on?
    boolean m_ongoingCBet;
    
    @Override
    public void addClosingListener(utility.IClosingListener<IClientPoker> pListener)
    {
        
    }
    
    /**
     * Crunch all the data from a player into a string
     * 
     * @return ArrayList of string containing the data from the players
     */
    public ArrayList<String> marshal()
    {
        final ArrayList<String> marshalData = new ArrayList<String>();
        for (final PlayerStats player : m_overallStats.values())
        {
            marshalData.add(player.marshal());
        }
        
        return marshalData;
    }
    
    @Override
    public void removeClosingListener(utility.IClosingListener<IClientPoker> pListener)
    {
        
    }
    
    @Override
    public void run()
    {
        
    }
    
    @Override
    public void setTable(OldPokerTableInfo pTable)
    {
        m_table = pTable;
        
    }
    
    @Override
    public void start()
    {
        
    }
    
    @Override
    public void stop()
    {
        
    }
    
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
    public void takeAction(ArrayList<TypePlayerAction> p_actionsAllowed, int p_callAmount, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        
    }
    
    /**
     * Unmarshal all the playerData into actual playerStats
     * 
     * @param p_playerData
     *            Data from the players
     */
    public void unmarshal(ArrayList<String> p_playerData)
    {
        for (final String playerData : p_playerData)
        {
            final StringTokenizer playerToken = new StringTokenizer(playerData, Constants.DELIMITER);
            final String playerName = playerToken.nextToken();
            PlayerStats player = m_overallStats.get(playerName);
            
            if (player == null)
            {
                player = new PlayerStats(this);
            }
            
            player.unmarshal(playerName, playerToken);
            m_overallStats.put(playerName, player);
        }
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
            public void betTurnEnded(ArrayList<Integer> potIndices, TypePokerRound round)
            {
                // Notify the lastStreeRaiser that he actually was the last one.
                if (m_lastStreetRaiser != null)
                {
                    m_lastStreetRaiser.lastRaiser();
                }
                
                ++m_turnType;
                m_isStreetRaised = false;
                m_nbBet = 0;
            }
            
            @Override
            public void gameEnded()
            {
                synchronized (m_table)
                {
                    for (final OldPokerPlayerInfo player : m_table.getPlayers())
                    {
                        // Notify everyone that a turn ended.
                        if (player.m_isPlaying)
                        {
                            m_gameStats.get(player.m_name).GameEnded();
                        }
                    }
                }
                
                synchronized (m_overallStats)
                {
                    for (final PlayerStats player : m_gameStats.values())
                    {
                        m_overallStats.put(player.getName(), player);
                    }
                    m_gameStats.clear();
                }
            }
            
            @Override
            public void gameStarted(OldPokerPlayerInfo oldDealer, OldPokerPlayerInfo oldSmallBlind, OldPokerPlayerInfo oldBigBlind)
            {
                synchronized (m_table)
                {
                    for (final OldPokerPlayerInfo player : m_table.getPlayers())
                    {
                        if (!m_overallStats.containsKey(player.m_name))
                        {
                            m_gameStats.put(player.m_name, new PlayerStats((ClientPokerPlayerInfo) player, StatsAgent.this));
                        }
                        else
                        {
                            try
                            {
                                m_gameStats.put(player.m_name, (PlayerStats) m_overallStats.get(player.m_name).clone());
                                m_gameStats.get(player.m_name).setPlayer((ClientPokerPlayerInfo) player);
                            }
                            catch (final CloneNotSupportedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        
                        // Notify everyone that a new hand is starting.
                        if (player.m_isPlaying)
                        {
                            m_gameStats.get(player.m_name).playNewHand();
                        }
                    }
                }
                m_gatheringStats = true;
                
                m_turnType = 0; // Preflop
                m_isUnopenedPot = true;
                m_isStreetRaised = false;
                m_ongoingCBet = true;
                
                // BB is the first Bet.
                m_nbBet = 1;
            }
            
            @Override
            public void playerTurnEnded(OldPokerPlayerInfo p_player, TypePlayerAction action, int actionAmount)
            {
                final PlayerStats player = m_gameStats.get(p_player.m_name);
                if (m_gatheringStats)
                {
                    switch (action)
                    {
                        case FOLD:
                            player.fold();
                            break;
                        case CALL:
                            player.call();
                            break;
                        case CHECK:
                            player.check();
                            break;
                        case RAISE:
                            player.raise();
                            if (m_isUnopenedPot)
                            {
                                m_isUnopenedPot = false;
                            }
                            if (!m_isStreetRaised)
                            {
                                // If there was a CBet and we just broke it ^^
                                if ((m_lastStreetRaiser != player) && (m_turnType != 0) && m_ongoingCBet)
                                {
                                    m_ongoingCBet = false;
                                }
                                m_isStreetRaised = true;
                            }
                            m_lastStreetRaiser = player;
                            ++m_nbBet;
                            break;
                        case SMALL_BLIND:
                            break;
                        case BIG_BLIND:
                            break;
                    }
                }
            }
            
        });
        
    }
    
}
