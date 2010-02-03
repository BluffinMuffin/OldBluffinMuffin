package stats;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

import miscUtil.Constants;
import miscUtil.IClosingListener;

import basePoker.PokerPlayerInfo;
import basePoker.PokerTableInfo;
import basePoker.TypePlayerAction;
import basePoker.TypePokerRound;
import basePokerAI.IPokerAgent;
import basePokerAI.IPokerAgentListener;
import backend.ClientPokerPlayerInfo;
import backend.ClientPokerTableInfo;

/**
 * StatsAgent.java
 * 
 * @author Hocus
 *         Description: This class log all kind of statistics related to a game
 *         of poker
 */
public class StatsAgent implements IPokerAgentListener
{
    
    // Playing table
    protected PokerTableInfo m_table;
    
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
    public void addClosingListener(miscUtil.IClosingListener<IPokerAgent> pListener)
    {
        
    }
    
    /**
     * Happens when a betting turn ends.
     * 
     * @param p_potIndices
     *            contains all indices of pots that have been modified.
     */
    public void betTurnEnded(ArrayList<Integer> pPotIndices, TypePokerRound pGameStat)
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
    
    /**
     * Happens when cards on the board changes.
     * 
     * @param p_boardCardIndices
     *            contains all indices of the board cards that have been
     *            modified.
     */
    public void boardChanged(ArrayList<Integer> p_boardCardIndices)
    {
        
    }
    
    /**
     * Happens when a game ends.
     */
    public void gameEnded()
    {
        synchronized (m_table)
        {
            for (final PokerPlayerInfo player : m_table.m_players.values())
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
    
    /**
     * Happens when a game starts.
     * 
     * @param p_oldDealer
     *            is the previous dealer.
     * @param p_oldSmallBlind
     *            is the previous player with the small blind.
     * @param p_oldBigBlind
     *            is the previous player with the big blind.
     */
    public void gameStarted(PokerPlayerInfo p_oldDealer, PokerPlayerInfo p_oldSmallBlind, PokerPlayerInfo p_oldBigBlind)
    {
        synchronized (m_table)
        {
            for (final PokerPlayerInfo player : m_table.m_players.values())
            {
                if (!m_overallStats.containsKey(player.m_name))
                {
                    m_gameStats.put(player.m_name, new PlayerStats((ClientPokerPlayerInfo)player, this));
                }
                else
                {
                    try
                    {
                        m_gameStats.put(player.m_name, (PlayerStats) m_overallStats.get(player.m_name).clone());
                        m_gameStats.get(player.m_name).setPlayer((ClientPokerPlayerInfo)player);
                    }
                    catch (final CloneNotSupportedException e)
                    {
                        // TODO Auto-generated catch block
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
    
    /**
     * Happens when the cards of a player changes.
     * 
     * @param p_player
     *            is the player for whom his cards have been changed.
     */
    public void playerCardChanged(PokerPlayerInfo p_player)
    {
        
    }
    
    /**
     * Happens when a player joined the table.
     * 
     * @param p_player
     *            is the player that has joined the table.
     */
    public void playerJoined(PokerPlayerInfo p_player)
    {
    }
    
    /**
     * Happens when a player left the table.
     * 
     * @param p_player
     *            is the player that has left the table.
     */
    public void playerLeft(PokerPlayerInfo p_player)
    {
        
    }
    
    /**
     * Happens when the money amount of a player changes.
     * 
     * @param p_player
     *            is the player for whom his money has been changed.
     * @param p_oldMoneyAmount
     *            is the previous money amount he had.
     */
    public void playerMoneyChanged(PokerPlayerInfo p_player, int p_oldMoneyAmount)
    {
        
    }
    
    /**
     * Happens when the turn of a player begins.
     * 
     * @param p_oldCurrentPlayer
     *            is the previous player that had played.
     */
    public void playerTurnBegan(PokerPlayerInfo p_oldCurrentPlayer)
    {
        
    }
    
    /**
     * Happens when the turn of a player ends.
     * 
     * @param p_player
     *            is the player for whom his turn ended.
     * @param p_action
     *            is the action taken by the player.
     * @param p_actionAmount
     *            is the amount related to the action taken.
     */
    public void playerTurnEnded(PokerPlayerInfo p_player, TypePlayerAction p_action, int p_actionAmount)
    {
        final PlayerStats player = m_gameStats.get(p_player.m_name);
        if (m_gatheringStats)
        {
            switch (p_action)
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
    
    /**
     * Happens when a player wins and receives his share.
     * 
     * @param p_player
     *            is the player that won.
     * @param p_potAmountWon
     *            is the share that the player won.
     * @param p_potIndex
     *            is the index of the pot that player won.
     */
    public void potWon(PokerPlayerInfo p_player, int p_potAmountWon, int p_potIndex)
    {
        
    }
    
    @Override
    public void removeClosingListener(miscUtil.IClosingListener<IPokerAgent> pListener)
    {
        
    }
    
    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setTable(PokerTableInfo pTable)
    {
        m_table = pTable;
        
    }
    
    @Override
    public void start()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void stop()
    {
        
    }
    
    /**
     * Happens when the table closes.
     */
    public void tableClosed()
    {
        
    }
    
    /**
     * Happens when all infos of a table need to be updated.
     */
    public void tableInfos()
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
    
    /**
     * Happens when waiting for other players to join.
     */
    public void waitingForPlayers()
    {
        
    }
    
}
