package clientStats;

import java.util.StringTokenizer;

import utility.Constants;



/**
 * PreflopPositionStats.java
 * 
 * @author Hocus
 *         Description: All the stats relative to each position in the preflop
 */
public class PreflopPositionStats implements Cloneable
{
    
    // How many hand the player played in this position.
    private int m_nbHands;
    
    private int m_nbPFR;
    private int m_nb3Bet;
    private int m_nbFoldTo3Bet;
    private int m_nbPossible3Bet;
    private int m_nbPossibleVs3Bet;
    
    /**
     * Ctor
     */
    public PreflopPositionStats()
    {
        m_nbHands = 0;
        m_nbPFR = 0;
        m_nb3Bet = 0;
        m_nbFoldTo3Bet = 0;
        m_nbPossible3Bet = 0;
        m_nbPossibleVs3Bet = 0;
    }
    
    /**
     * Clone the object
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    
    /**
     * Return the number of hands the player played in this position
     * 
     * @return number of hands the player played in this position
     */
    public int getNbHands()
    {
        return m_nbHands;
    }
    
    /**
     * Return the probability that the player 3bet
     * 
     * @return probability that the player 3bet
     */
    public double getProb3Bet()
    {
        if (m_nbPossible3Bet == 0)
        {
            return 0;
        }
        
        return m_nb3Bet / (double) m_nbPossible3Bet;
    }
    
    /**
     * Return the probability that the player fold vs a 3bet
     * 
     * @return probability that the player fold vs a 3bet
     */
    public double getProbFoldTo3Bet()
    {
        if (m_nbPossibleVs3Bet == 0)
        {
            return 0;
        }
        
        return m_nbFoldTo3Bet / (double) m_nbPossibleVs3Bet;
    }
    
    /**
     * Return the probability that the player is the preflop raiser
     * 
     * @return probability that the player is the preflop raiser
     */
    public double getProbPfr()
    {
        if (m_nbHands == 0)
        {
            return 0;
        }
        
        return m_nbPFR / (double) m_nbHands;
    }
    
    /**
     * Notify this position that it has 3Bet
     */
    public void is3Bet()
    {
        ++m_nb3Bet;
    }
    
    /**
     * Notify this position that it folded vs a 3Bet
     */
    public void isFoldTo3Bet()
    {
        ++m_nbFoldTo3Bet;
    }
    
    /**
     * Notify this position that it is the pfr
     */
    public void isPFR()
    {
        ++m_nbPFR;
    }
    
    /**
     * Notify this position that it is possible to 3Bet
     */
    public void isPossible3Bet()
    {
        ++m_nbPossible3Bet;
    }
    
    /**
     * Notify this position that it is vs a 3Bet
     */
    public void isVs3Bet()
    {
        ++m_nbPossibleVs3Bet;
    }
    
    /**
     * Marshal all the data of the position in a string
     * 
     * @return all the data for this position
     */
    public String marshal()
    {
        final StringBuilder marshalData = new StringBuilder();
        
        marshalData.append(m_nbHands);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPFR);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nb3Bet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbFoldTo3Bet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossible3Bet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleVs3Bet);
        marshalData.append(Constants.DELIMITER);
        
        return marshalData.toString();
    }
    
    /**
     * Notify this position that a new hand is starting
     */
    public void playNewHand()
    {
        ++m_nbHands;
    }
    
    /**
     * Extract all the data from the player
     * 
     * @param p_playerData
     *            Data of the player
     */
    public void unmarshal(StringTokenizer p_playerData)
    {
        m_nbHands = Integer.parseInt(p_playerData.nextToken());
        m_nbPFR = Integer.parseInt(p_playerData.nextToken());
        m_nb3Bet = Integer.parseInt(p_playerData.nextToken());
        m_nbFoldTo3Bet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossible3Bet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleVs3Bet = Integer.parseInt(p_playerData.nextToken());
    }
}
