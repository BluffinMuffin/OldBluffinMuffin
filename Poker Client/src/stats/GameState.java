package stats;

import java.util.StringTokenizer;

import miscUtil.Constants;

import backend.Player;

/**
 * GameState.java
 * 
 * @author Hocus
 *         Description: Statistics that are related to all the streets
 *         together(preflop/flop...)
 */
public class GameState implements Cloneable
{
    protected final int TWO_BET = 2;
    protected final int THREE_BET = 3;
    protected final int FOUR_BET = 4;
    
    private int m_nbFoldTotal;
    private int m_nbBet;
    private int m_nbPossibleBet;
    private int m_nbCBet;
    private int m_nbFoldToCBet;
    private int m_nbRaiseToCBet;
    private int m_nbPossibleVsCBet;
    private int m_nbPossibleCBet;
    protected StatsAgent m_agent;
    protected Player m_myself;
    protected PlayerStats m_myStats;
    
    /**
     * Ctor
     * 
     * @param p_statsAgent
     *            Handle on agentStats
     * @param p_myself
     *            Handle on Player
     * @param p_stats
     *            Handle on playerStats
     */
    public GameState(StatsAgent p_statsAgent, Player p_myself, PlayerStats p_stats)
    {
        this(p_statsAgent, p_stats);
        m_myself = p_myself;
    }
    
    /**
     * Ctor
     * 
     * @param p_statsAgent
     *            Handle on agentStats
     * @param p_stats
     *            Handle on playerStats
     */
    public GameState(StatsAgent p_statsAgent, PlayerStats p_stats)
    {
        m_agent = p_statsAgent;
        m_myStats = p_stats;
        
        m_nbFoldTotal = 0;
        m_nbBet = 0;
        m_nbPossibleBet = 0;
        m_nbCBet = 0;
        m_nbPossibleCBet = 0;
        m_nbFoldToCBet = 0;
        m_nbRaiseToCBet = 0;
        m_nbPossibleVsCBet = 0;
    }
    
    /**
     * Action is call
     */
    public void call()
    {
        // No one raised the street
        if (!m_agent.m_isStreetRaised)
        {
            ++m_nbPossibleBet;
        }
        
        // We are the last street raiser, the street have not been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser == m_myStats) && !m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            ++m_nbPossibleCBet;
        }
        
        // We are not the last street raiser, the street have been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser != m_myStats) && m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            ++m_nbPossibleVsCBet;
        }
    }
    
    /**
     * Action is check
     */
    public void check()
    {
        // No one raised the street
        if (!m_agent.m_isStreetRaised)
        {
            ++m_nbPossibleBet;
        }
        
        // We are the last street raiser, the street have not been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser == m_myStats) && !m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            ++m_nbPossibleCBet;
        }
        
        // It's not possible to check VS a CBet. Call/Raise.
        
    }
    
    /**
     * Clone the object
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        final GameState clone = (GameState) super.clone();
        m_myStats = null;
        return clone;
        
    }
    
    /**
     * Action is fold
     */
    public void fold()
    {
        if (Constants.DEBUG_STATS)
        {
            System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold.");
        }
        ++m_nbFoldTotal;
        
        // We are the last street raiser, the street have not been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser == m_myStats) && !m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            ++m_nbPossibleCBet;
        }
        
        // We are not the last street raiser, the street have been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser != m_myStats) && m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold Vs a CBet.");
            }
            ++m_nbFoldToCBet;
            ++m_nbPossibleVsCBet;
        }
    }
    
    /**
     * Return the number of time the player fold
     * 
     * @return number of time the player fold
     */
    public int getNbFoldTotal()
    {
        return m_nbFoldTotal;
    }
    
    /**
     * Return the probability that the player bet
     * 
     * @return probability that the player bet
     */
    public double getProbBet()
    {
        if (m_nbPossibleBet == 0)
        {
            return 0;
        }
        
        return m_nbBet / (double) m_nbPossibleBet;
    }
    
    /**
     * Return the probability that the player Cbet
     * 
     * @return probability that the player Cbet
     */
    public double getProbCBet()
    {
        if (m_nbPossibleCBet == 0)
        {
            return 0;
        }
        
        return m_nbCBet / (double) m_nbPossibleCBet;
    }
    
    /**
     * Return the probability that the player fold vs a Cbet
     * 
     * @return probability that the player fold vs a Cbet
     */
    public double getProbFoldToCBet()
    {
        if (m_nbPossibleVsCBet == 0)
        {
            return 0;
        }
        
        return m_nbFoldToCBet / (double) m_nbPossibleVsCBet;
    }
    
    /**
     * Return the probability that the player raise vs a Cbet
     * 
     * @return probability that the player raise vs a Cbet
     */
    public double getProbRaiseToCBet()
    {
        if (m_nbPossibleVsCBet == 0)
        {
            return 0;
        }
        
        return m_nbRaiseToCBet / (double) m_nbPossibleVsCBet;
    }
    
    /**
     * Marshal all the data of the street in a string
     * 
     * @return all the data for this street
     */
    public String marshal()
    {
        final StringBuilder marshalData = new StringBuilder();
        
        marshalData.append(m_nbFoldTotal);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbCBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbFoldToCBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbRaiseToCBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleVsCBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleCBet);
        marshalData.append(Constants.DELIMITER);
        
        return marshalData.toString();
    }
    
    /**
     * Action is raise
     */
    public void raise()
    {
        // No one raised the street
        if (!m_agent.m_isStreetRaised)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Bet.");
            }
            ++m_nbBet;
            ++m_nbPossibleBet;
        }
        
        // We are the last street raiser, the street have not been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser == m_myStats) && !m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a CBet.");
            }
            ++m_nbCBet;
            ++m_nbPossibleCBet;
        }
        
        // We are not the last street raiser, the street have been raised
        // and there is a CBet going on.
        if ((m_agent.m_lastStreetRaiser != m_myStats) && m_agent.m_isStreetRaised && m_agent.m_ongoingCBet && (m_agent.m_turnType != 0))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Raise VS a CBet.");
            }
            ++m_nbRaiseToCBet;
            ++m_nbPossibleVsCBet;
        }
        
    }
    
    /**
     * Set the player
     * 
     * @param p_myself
     *            Player
     */
    public void setPlayer(Player p_myself)
    {
        m_myself = p_myself;
    }
    
    /**
     * Set the playerStats
     * 
     * @param p_playerStats
     *            PlayerStats
     */
    public void setPlayerStats(PlayerStats p_playerStats)
    {
        m_myStats = p_playerStats;
    }
    
    /**
     * Extract all the data from the player
     * 
     * @param p_playerData
     *            Data of the player
     */
    public void unmarshal(StringTokenizer p_playerData)
    {
        m_nbFoldTotal = Integer.parseInt(p_playerData.nextToken());
        m_nbBet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleBet = Integer.parseInt(p_playerData.nextToken());
        m_nbCBet = Integer.parseInt(p_playerData.nextToken());
        m_nbFoldToCBet = Integer.parseInt(p_playerData.nextToken());
        m_nbRaiseToCBet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleVsCBet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleCBet = Integer.parseInt(p_playerData.nextToken());
    }
}
