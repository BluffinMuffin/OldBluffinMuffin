package pokerClientGameStats;

import java.util.StringTokenizer;

import pokerClientGame.ClientPokerPlayerInfo;
import utility.Constants;

/**
 * PreflopStats.java
 * 
 * @author Hocus
 *         Description: Keep all the stats related to the preflop phase
 */
public class PreflopStats extends GameState implements Cloneable
{
    
    private PreflopPositionStats m_dealer;
    private PreflopPositionStats m_smallBlind;
    private PreflopPositionStats m_bigBlind;
    private PreflopPositionStats m_earlyPos;
    private PreflopPositionStats m_middlePos;
    private PreflopPositionStats m_cutOff;
    
    // Only the first time VPIP is count.
    private boolean m_isVPIP;
    
    private int m_nbVPIP;
    private int m_nbFourBet;
    private int m_nbPossibleFourBet;
    private int m_nbFoldToFourBet;
    private int m_nbPossibleVsFourBet;
    
    // When we are CO
    private int m_nbStealCO;
    private int m_nbPossibleStealCO;
    
    // When we are D
    private int m_nbStealD;
    private int m_nbPossibleStealD;
    
    // When we are SB
    private int m_nbStealSB;
    private int m_nbPossibleStealSB;
    private int m_FoldVsStealSB;
    private int m_3BetVsStealSB;
    private int m_nbPossibleVsStealSB;
    
    // When we are BB
    private int m_FoldVsStealBB;
    private int m_3BetVsStealBB;
    private int m_nbPossibleVsStealBB;
    
    private static int g_cptBangs = 0;
    
    /**
     * Ctor
     * 
     * @param p_statsAgent
     *            Handle on the statsAgent
     * @param p_myself
     *            Handle on the player
     * @param p_stats
     *            Handle on the playerStats
     */
    public PreflopStats(StatsAgent p_statsAgent, ClientPokerPlayerInfo p_myself, PlayerStats p_stats)
    {
        super(p_statsAgent, p_myself, p_stats);
        
        m_dealer = new PreflopPositionStats();
        m_bigBlind = new PreflopPositionStats();
        m_smallBlind = new PreflopPositionStats();
        m_earlyPos = new PreflopPositionStats();
        m_middlePos = new PreflopPositionStats();
        m_cutOff = new PreflopPositionStats();
        
        m_nbVPIP = 0;
        m_isVPIP = false;
        m_nbFourBet = 0;
        m_nbPossibleFourBet = 0;
        m_nbFoldToFourBet = 0;
        m_nbPossibleVsFourBet = 0;
        
        m_nbStealCO = 0;
        m_nbPossibleStealCO = 0;
        
        m_nbStealD = 0;
        m_nbPossibleStealD = 0;
        
        m_nbStealSB = 0;
        m_nbPossibleStealSB = 0;
        m_FoldVsStealSB = 0;
        m_3BetVsStealSB = 0;
        m_nbPossibleVsStealSB = 0;
        
        m_FoldVsStealBB = 0;
        m_3BetVsStealBB = 0;
        m_nbPossibleVsStealBB = 0;
    }
    
    /**
     * Ctor
     * 
     * @param p_statsAgent
     *            Handle on the statsAgent
     * @param p_stats
     *            Handle on the playerStats
     */
    public PreflopStats(StatsAgent p_statsAgent, PlayerStats p_stats)
    {
        super(p_statsAgent, p_stats);
        
        m_dealer = new PreflopPositionStats();
        m_bigBlind = new PreflopPositionStats();
        m_smallBlind = new PreflopPositionStats();
        m_earlyPos = new PreflopPositionStats();
        m_middlePos = new PreflopPositionStats();
        m_cutOff = new PreflopPositionStats();
        
        m_nbVPIP = 0;
        m_isVPIP = false;
        m_nbFourBet = 0;
        m_nbPossibleFourBet = 0;
        m_nbFoldToFourBet = 0;
        m_nbPossibleVsFourBet = 0;
        
        m_nbStealCO = 0;
        m_nbPossibleStealCO = 0;
        
        m_nbStealD = 0;
        m_nbPossibleStealD = 0;
        
        m_nbStealSB = 0;
        m_nbPossibleStealSB = 0;
        m_FoldVsStealSB = 0;
        m_3BetVsStealSB = 0;
        m_nbPossibleVsStealSB = 0;
        
        m_FoldVsStealBB = 0;
        m_3BetVsStealBB = 0;
        m_nbPossibleVsStealBB = 0;
    }
    
    /**
     * Action is call
     */
    @Override
    public void call()
    {
        super.call();
        
        // Volontary put in pot
        if (!m_isVPIP)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a VPIP.");
            }
            m_isVPIP = true;
            ++m_nbVPIP;
        }
        
        // Possible Steal
        if (!m_myself.m_isBigBlind && m_agent.m_isUnopenedPot)
        {
            if (m_myself.m_isSmallBlind)
            {
                ++m_nbPossibleStealSB;
            }
            if (m_myself.m_isDealer)
            {
                ++m_nbPossibleStealD;
            }
            else if (m_myself.m_isCutOff)
            {
                ++m_nbPossibleStealCO;
            }
        }
        
        // Possible 4Bet/ VS3Bet
        if (m_agent.m_nbBet == THREE_BET)
        {
            ++m_nbPossibleFourBet;
            if (m_myself.m_isBigBlind)
            {
                m_bigBlind.isVs3Bet();
            }
            else if (m_myself.m_isSmallBlind)
            {
                m_smallBlind.isVs3Bet();
            }
            else if (m_myself.m_isEarlyPos)
            {
                m_earlyPos.isVs3Bet();
            }
            else if (m_myself.m_isMidPos)
            {
                m_middlePos.isVs3Bet();
            }
            else if (m_myself.m_isCutOff)
            {
                m_cutOff.isVs3Bet();
            }
            else if (m_myself.m_isDealer)
            {
                m_dealer.isVs3Bet();
            }
        }
        // Possible 3Bet
        else if (m_agent.m_nbBet == TWO_BET)
        {
            if (m_myself.m_isBigBlind)
            {
                m_bigBlind.isPossible3Bet();
            }
            else if (m_myself.m_isSmallBlind)
            {
                m_smallBlind.isPossible3Bet();
            }
            else if (m_myself.m_isEarlyPos)
            {
                m_earlyPos.isPossible3Bet();
            }
            else if (m_myself.m_isMidPos)
            {
                m_middlePos.isPossible3Bet();
            }
            else if (m_myself.m_isCutOff)
            {
                m_cutOff.isPossible3Bet();
            }
            else if (m_myself.m_isDealer)
            {
                m_dealer.isPossible3Bet();
            }
        }
        // VS4Bet
        else if (m_agent.m_nbBet == FOUR_BET)
        {
            ++m_nbPossibleVsFourBet;
        }
        
        // VSSteal
        if ((m_myself.m_isSmallBlind && !m_myself.m_isDealer) && (m_agent.m_nbBet == TWO_BET) && (m_agent.m_lastStreetRaiser.isCutOff() || m_agent.m_lastStreetRaiser.isDealer()))
        {
            ++m_nbPossibleVsStealSB;
        }
        else if (m_myself.m_isBigBlind && (m_agent.m_nbBet == TWO_BET) && (m_agent.m_lastStreetRaiser.isCutOff() || m_agent.m_lastStreetRaiser.isDealer() || m_agent.m_lastStreetRaiser.isSmallBlind()))
        {
            ++m_nbPossibleVsStealBB;
        }
    }
    
    /**
     * Clone the object
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        final PreflopStats clone = (PreflopStats) super.clone();
        m_dealer = (PreflopPositionStats) m_dealer.clone();
        m_smallBlind = (PreflopPositionStats) m_smallBlind.clone();
        m_bigBlind = (PreflopPositionStats) m_bigBlind.clone();
        m_earlyPos = (PreflopPositionStats) m_earlyPos.clone();
        m_middlePos = (PreflopPositionStats) m_middlePos.clone();
        m_cutOff = (PreflopPositionStats) m_cutOff.clone();
        
        return clone;
    }
    
    /**
     * Action is fold
     */
    @Override
    public void fold()
    {
        super.fold();
        // FoldToFourBet
        if (m_agent.m_nbBet == FOUR_BET)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 4bet.");
            }
            ++m_nbFoldToFourBet;
            ++m_nbPossibleVsFourBet;
        }
        // Possible 4Bet/ VS3Bet
        else if (m_agent.m_nbBet == THREE_BET)
        {
            ++m_nbPossibleFourBet;
            if (m_myself.m_isBigBlind)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 3bet. Being BB.");
                }
                m_bigBlind.isFoldTo3Bet();
                m_bigBlind.isVs3Bet();
            }
            else if (m_myself.m_isSmallBlind)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 3bet. Being SB.");
                }
                m_smallBlind.isFoldTo3Bet();
                m_smallBlind.isVs3Bet();
            }
            else if (m_myself.m_isEarlyPos)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 3bet. Being EP.");
                }
                m_earlyPos.isFoldTo3Bet();
                m_earlyPos.isVs3Bet();
            }
            else if (m_myself.m_isMidPos)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 3bet. Being MP.");
                }
                m_middlePos.isFoldTo3Bet();
                m_middlePos.isVs3Bet();
            }
            else if (m_myself.m_isCutOff)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 3bet. Being CO.");
                }
                m_cutOff.isFoldTo3Bet();
                m_cutOff.isVs3Bet();
            }
            else if (m_myself.m_isDealer)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a 3bet. Being D.");
                }
                m_dealer.isFoldTo3Bet();
                m_dealer.isVs3Bet();
            }
        }
        // Possible 3Bet
        else if (m_agent.m_nbBet == TWO_BET)
        {
            if (m_myself.m_isBigBlind)
            {
                m_bigBlind.isPossible3Bet();
            }
            else if (m_myself.m_isSmallBlind)
            {
                m_smallBlind.isPossible3Bet();
            }
            else if (m_myself.m_isEarlyPos)
            {
                m_earlyPos.isPossible3Bet();
            }
            else if (m_myself.m_isMidPos)
            {
                m_middlePos.isPossible3Bet();
            }
            else if (m_myself.m_isCutOff)
            {
                m_cutOff.isPossible3Bet();
            }
            else if (m_myself.m_isDealer)
            {
                m_dealer.isPossible3Bet();
            }
        }
        
        if (m_myself == null)
        {
            System.out.println("WATCH");
        }
        
        // Possible Steal
        if (!m_myself.m_isBigBlind && m_agent.m_isUnopenedPot)
        {
            if (m_myself.m_isSmallBlind)
            {
                ++m_nbPossibleStealSB;
            }
            if (m_myself.m_isDealer)
            {
                ++m_nbPossibleStealD;
            }
            else if (m_myself.m_isCutOff)
            {
                ++m_nbPossibleStealCO;
            }
        }
        
        // FoldVSSteal
        if ((m_myself.m_isSmallBlind && !m_myself.m_isDealer) && (m_agent.m_nbBet == TWO_BET) && (m_agent.m_lastStreetRaiser.isCutOff() || m_agent.m_lastStreetRaiser.isDealer()))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a Steal. Being SB.");
            }
            ++m_FoldVsStealSB;
            ++m_nbPossibleVsStealSB;
        }
        else if (m_myself.m_isBigBlind && (m_agent.m_nbBet == TWO_BET) && (m_agent.m_lastStreetRaiser.isCutOff() || m_agent.m_lastStreetRaiser.isDealer() || m_agent.m_lastStreetRaiser.isSmallBlind()))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Fold versus a Steal. Being BB.");
            }
            ++m_FoldVsStealBB;
            ++m_nbPossibleVsStealBB;
        }
    }
    
    /**
     * Return the total number of time the player voluntary put money in the pot
     * 
     * @return total number of time the player voluntary put money in the pot
     */
    public int getNbVPIPTotal()
    {
        return m_nbVPIP;
    }
    
    /**
     * Return the probability that the player 3bet being the big blind
     * 
     * @return probability that the player 3bet being the big blind
     */
    public double getProb3Bet_BB()
    {
        return m_bigBlind.getProb3Bet();
    }
    
    /**
     * Return the probability that the player 3bet being the cutoff
     * 
     * @return probability that the player 3bet being the cutoff
     */
    public double getProb3Bet_CO()
    {
        return m_cutOff.getProb3Bet();
    }
    
    /**
     * Return the probability that the player 3bet being the dealer
     * 
     * @return probability that the player 3bet being the dealer
     */
    public double getProb3Bet_D()
    {
        return m_dealer.getProb3Bet();
    }
    
    /**
     * Return the probability that the player 3bet being in the early position
     * 
     * @return probability that the player 3bet being in the early position
     */
    public double getProb3Bet_EP()
    {
        return m_earlyPos.getProb3Bet();
    }
    
    /**
     * Return the probability that the player 3bet being in the middle position
     * 
     * @return probability that the player 3bet being in the middle position
     */
    public double getProb3Bet_MP()
    {
        return m_middlePos.getProb3Bet();
    }
    
    /**
     * Return the probability that the player 3bet being the small blind
     * 
     * @return probability that the player 3bet being the small blind
     */
    public double getProb3Bet_SB()
    {
        return m_smallBlind.getProb3Bet();
    }
    
    /**
     * Return the probability that the player 3bet vs a steal being the big
     * blind
     * 
     * @return probability that the player 3bet vs a steal being the big blind
     */
    public double getProbBB3BetVsSteal()
    {
        if (m_nbPossibleVsStealBB == 0)
        {
            return 0;
        }
        
        return m_3BetVsStealBB / (double) m_nbPossibleVsStealBB;
    }
    
    /**
     * Return the probability that the player fold vs a steal being the big
     * blind
     * 
     * @return probability that the player fold vs a steal being the big blind
     */
    public double getProbBBFoldVsSteal()
    {
        if (m_nbPossibleVsStealBB == 0)
        {
            return 0;
        }
        
        return m_FoldVsStealBB / (double) m_nbPossibleVsStealBB;
    }
    
    /**
     * Return the probability that the player steal being the cutoff
     * 
     * @return probability that the player steal being the cutoff
     */
    public double getProbCOSteal()
    {
        if (m_nbPossibleStealCO == 0)
        {
            return 0;
        }
        
        return m_nbStealCO / (double) m_nbPossibleStealCO;
    }
    
    /**
     * Return the probability that the player steal being the dealer
     * 
     * @return probability that the player steal being the dealer
     */
    public double getProbDSteal()
    {
        if (m_nbPossibleStealD == 0)
        {
            return 0;
        }
        
        return m_nbStealD / (double) m_nbPossibleStealD;
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the big blind
     * 
     * @return probability that the player fold vs a 3bet being the big blind
     */
    public double getProbFoldTo3Bet_BB()
    {
        return m_bigBlind.getProbFoldTo3Bet();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the cutoff
     * 
     * @return probability that the player fold vs a 3bet being the cutoff
     */
    public double getProbFoldTo3Bet_CO()
    {
        return m_cutOff.getProbFoldTo3Bet();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the dealer
     * 
     * @return probability that the player fold vs a 3bet being the dealer
     */
    public double getProbFoldTo3Bet_D()
    {
        return m_dealer.getProbFoldTo3Bet();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being in the early
     * position
     * 
     * @return probability that the player fold vs a 3bet being in the early
     *         position
     */
    public double getProbFoldTo3Bet_EP()
    {
        return m_earlyPos.getProbFoldTo3Bet();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being in the middle
     * position
     * 
     * @return probability that the player fold vs a 3bet being in the middle
     *         position
     */
    public double getProbFoldTo3Bet_MP()
    {
        return m_middlePos.getProbFoldTo3Bet();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the small
     * blind
     * 
     * @return probability that the player fold vs a 3bet being the small blind
     */
    public double getProbFoldTo3Bet_SB()
    {
        return m_smallBlind.getProbFoldTo3Bet();
    }
    
    /**
     * Return the probability that the player fold vs a 4bet
     * 
     * @return probability that the player fold vs a 4bet
     */
    public double getProbFoldToFourBet()
    {
        if (m_nbPossibleVsFourBet == 0)
        {
            return 0;
        }
        
        return m_nbFoldToFourBet / (double) m_nbPossibleVsFourBet;
    }
    
    /**
     * Return the probability that the player do a 4bet
     * 
     * @return probability that the player do a 4bet
     */
    public double getProbFourBet()
    {
        if (m_nbPossibleFourBet == 0)
        {
            return 0;
        }
        
        return m_nbFourBet / (double) m_nbPossibleFourBet;
    }
    
    /**
     * Return the probability that the player was the pfr being the big blind
     * 
     * @return probability that the player was the pfr being the big blind
     */
    public double getProbPFR_BB()
    {
        return m_bigBlind.getProbPfr();
    }
    
    /**
     * Return the probability that the player was the pfr being the cutoff
     * 
     * @return probability that the player was the pfr being the cutoff
     */
    public double getProbPFR_CO()
    {
        return m_cutOff.getProbPfr();
    }
    
    /**
     * Return the probability that the player was the pfr being the dealer
     * 
     * @return probability that the player was the pfr being the dealer
     */
    public double getProbPFR_D()
    {
        return m_dealer.getProbPfr();
    }
    
    /**
     * Return the probability that the player was the pfr being in the early
     * position
     * 
     * @return probability that the player was the pfr being in the early
     *         position
     */
    public double getProbPFR_EP()
    {
        return m_earlyPos.getProbPfr();
    }
    
    /**
     * Return the probability that the player was the pfr being in the middle
     * position
     * 
     * @return probability that the player was the pfr being in the middle
     *         position
     */
    public double getProbPFR_MP()
    {
        return m_middlePos.getProbPfr();
    }
    
    /**
     * Return the probability that the player was the pfr being the small blind
     * 
     * @return probability that the player was the pfr being the small blind
     */
    public double getProbPFR_SB()
    {
        return m_smallBlind.getProbPfr();
    }
    
    /**
     * Return the probability that the player 3bet vs a steal being the small
     * blind
     * 
     * @return probability that the player 3bet vs a steal being the small blind
     */
    public double getProbSB3BetVsSteal()
    {
        if (m_nbPossibleVsStealSB == 0)
        {
            return 0;
        }
        
        return m_3BetVsStealSB / (double) m_nbPossibleVsStealSB;
    }
    
    /**
     * Return the probability that the player fold vs a steal being the small
     * blind
     * 
     * @return probability that the player fold vs a steal being the small blind
     */
    public double getProbSBFoldVsSteal()
    {
        if (m_nbPossibleVsStealSB == 0)
        {
            return 0;
        }
        
        return m_FoldVsStealSB / (double) m_nbPossibleVsStealSB;
    }
    
    /**
     * Return the probability that the player steal being the small blind
     * 
     * @return probability that the player steal being the small blind
     */
    public double getProbSBSteal()
    {
        if (m_nbPossibleStealSB == 0)
        {
            return 0;
        }
        
        return m_nbStealSB / (double) m_nbPossibleStealSB;
    }
    
    /**
     * Notify that the player was the last raiser
     */
    public void isLastRaiser()
    {
        // PFR
        if (m_myself.m_isBigBlind)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a PFR. Being BB.");
            }
            m_bigBlind.isPFR();
        }
        else if (m_myself.m_isSmallBlind)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a PFR. Being SB.");
            }
            m_smallBlind.isPFR();
        }
        else if (m_myself.m_isEarlyPos)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a PFR. Being EP.");
            }
            m_earlyPos.isPFR();
        }
        else if (m_myself.m_isMidPos)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a PFR. Being MP.");
            }
            m_middlePos.isPFR();
        }
        else if (m_myself.m_isCutOff)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a PFR. Being CO.");
            }
            m_cutOff.isPFR();
        }
        else if (m_myself.m_isDealer)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a PFR. Being D.");
            }
            m_dealer.isPFR();
        }
    }
    
    /**
     * Marshal all the data of the player in a string
     * 
     * @return all the data for this player
     */
    @Override
    public String marshal()
    {
        final StringBuilder marshalData = new StringBuilder();
        marshalData.append(super.marshal());
        
        marshalData.append(m_nbVPIP);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbFourBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleFourBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbFoldToFourBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleVsFourBet);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbStealCO);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleStealCO);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbStealD);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleStealD);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbStealSB);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleStealSB);// 21
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_FoldVsStealSB);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_3BetVsStealSB);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleVsStealSB);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_FoldVsStealBB);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_3BetVsStealBB);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleVsStealBB);// 27
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_dealer.marshal());// 33
        marshalData.append(m_smallBlind.marshal());// 39
        marshalData.append(m_bigBlind.marshal());// 45
        marshalData.append(m_earlyPos.marshal());// 51
        marshalData.append(m_middlePos.marshal());// 57
        marshalData.append(m_cutOff.marshal());// 63
        
        return marshalData.toString();
    }
    
    /**
     * Notify the player that a new hand just started
     */
    public void playNewHand()
    {
        m_isVPIP = false;
        
        boolean isNothing = true;
        if (m_myself.m_isBigBlind)
        {
            isNothing = false;
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just started a new hand. Being BB.");
            }
            m_bigBlind.playNewHand();
        }
        if (m_myself.m_isSmallBlind)
        {
            isNothing = false;
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just started a new hand. Being SB.");
            }
            m_smallBlind.playNewHand();
        }
        if (m_myself.m_isEarlyPos)
        {
            isNothing = false;
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just started a new hand. Being EP.");
            }
            m_earlyPos.playNewHand();
        }
        if (m_myself.m_isMidPos)
        {
            isNothing = false;
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just started a new hand. Being MP.");
            }
            m_middlePos.playNewHand();
        }
        if (m_myself.m_isCutOff)
        {
            isNothing = false;
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just started a new hand. Being CO.");
            }
            m_cutOff.playNewHand();
        }
        if (m_myself.m_isDealer)
        {
            isNothing = false;
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just started a new hand. Being D.");
            }
            m_dealer.playNewHand();
        }
        if (isNothing)
        {
            PreflopStats.g_cptBangs++;
            System.out.println(m_myself.m_name + ": BANGGGGGGGGGGGGGG!!!! (" + PreflopStats.g_cptBangs + ")");
        }
    }
    
    /**
     * Action is raise
     */
    @Override
    public void raise()
    {
        super.raise();
        
        // Volontary put in pot
        if (!m_isVPIP)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a VPIP.");
            }
            m_isVPIP = true;
            ++m_nbVPIP;
        }
        
        // Do a 4Bet/ VS 3Bet
        if (m_agent.m_nbBet == THREE_BET)
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 4Bet.");
            }
            ++m_nbFourBet;
            ++m_nbPossibleFourBet;
            if (m_myself.m_isBigBlind)
            {
                m_bigBlind.isVs3Bet();
            }
            else if (m_myself.m_isSmallBlind)
            {
                m_smallBlind.isVs3Bet();
            }
            else if (m_myself.m_isEarlyPos)
            {
                m_earlyPos.isVs3Bet();
            }
            else if (m_myself.m_isMidPos)
            {
                m_middlePos.isVs3Bet();
            }
            else if (m_myself.m_isCutOff)
            {
                m_cutOff.isVs3Bet();
            }
            else if (m_myself.m_isDealer)
            {
                m_dealer.isVs3Bet();
            }
        }
        // VS4Bet
        else if (m_agent.m_nbBet == FOUR_BET)
        {
            ++m_nbPossibleVsFourBet;
        }
        // Do a 3Bet
        else if (m_agent.m_nbBet == TWO_BET)
        {
            if (m_myself.m_isBigBlind)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet. Being BB.");
                }
                m_bigBlind.is3Bet();
                m_bigBlind.isPossible3Bet();
            }
            else if (m_myself.m_isSmallBlind)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet. Being SB.");
                }
                m_smallBlind.is3Bet();
                m_smallBlind.isPossible3Bet();
            }
            else if (m_myself.m_isEarlyPos)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet. Being EP.");
                }
                m_earlyPos.is3Bet();
                m_earlyPos.isPossible3Bet();
            }
            else if (m_myself.m_isMidPos)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet. Being MP.");
                }
                m_middlePos.is3Bet();
                m_middlePos.isPossible3Bet();
            }
            else if (m_myself.m_isCutOff)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet. Being CO.");
                }
                m_cutOff.is3Bet();
                m_cutOff.isPossible3Bet();
            }
            else if (m_myself.m_isDealer)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet. Being D.");
                }
                m_dealer.is3Bet();
                m_dealer.isPossible3Bet();
            }
        }
        
        // Steal
        if (!m_myself.m_isBigBlind && m_agent.m_isUnopenedPot)
        {
            if (m_myself.m_isSmallBlind)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Steal. Being SB.");
                }
                ++m_nbStealSB;
                ++m_nbPossibleStealSB;
            }
            if (m_myself.m_isDealer)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Steal. Being D.");
                }
                ++m_nbStealD;
                ++m_nbPossibleStealD;
            }
            else if (m_myself.m_isCutOff)
            {
                if (Constants.DEBUG_STATS)
                {
                    System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a Steal. Being CO.");
                }
                ++m_nbStealCO;
                ++m_nbPossibleStealCO;
            }
        }
        
        // RaiseVSSteal
        if ((m_myself.m_isSmallBlind && !m_myself.m_isDealer) && (m_agent.m_nbBet == TWO_BET) && (m_agent.m_lastStreetRaiser.isCutOff() || m_agent.m_lastStreetRaiser.isDealer()))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet versus a Steal. Being SB.");
            }
            ++m_3BetVsStealSB;
            ++m_nbPossibleVsStealSB;
        }
        else if (m_myself.m_isBigBlind && (m_agent.m_nbBet == TWO_BET) && (m_agent.m_lastStreetRaiser.isCutOff() || m_agent.m_lastStreetRaiser.isDealer() || m_agent.m_lastStreetRaiser.isSmallBlind()))
        {
            if (Constants.DEBUG_STATS)
            {
                System.out.println("[DEBUG]-->Player:" + m_myStats.getName() + " just did a 3Bet versus a Steal. Being BB.");
            }
            ++m_3BetVsStealBB;
            ++m_nbPossibleVsStealBB;
        }
    }
    
    /**
     * Extract all the data from the player
     * 
     * @param p_playerData
     *            Data of the player
     */
    @Override
    public void unmarshal(StringTokenizer p_playerData)
    {
        super.unmarshal(p_playerData);
        
        m_nbVPIP = Integer.parseInt(p_playerData.nextToken());
        m_nbFourBet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleFourBet = Integer.parseInt(p_playerData.nextToken());
        m_nbFoldToFourBet = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleVsFourBet = Integer.parseInt(p_playerData.nextToken());
        
        m_nbStealCO = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleStealCO = Integer.parseInt(p_playerData.nextToken());
        
        m_nbStealD = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleStealD = Integer.parseInt(p_playerData.nextToken());
        
        m_nbStealSB = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleStealSB = Integer.parseInt(p_playerData.nextToken());
        
        m_FoldVsStealSB = Integer.parseInt(p_playerData.nextToken());
        m_3BetVsStealSB = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleVsStealSB = Integer.parseInt(p_playerData.nextToken());
        
        m_FoldVsStealBB = Integer.parseInt(p_playerData.nextToken());
        m_3BetVsStealBB = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleVsStealBB = Integer.parseInt(p_playerData.nextToken());
        
        m_dealer.unmarshal(p_playerData);
        m_smallBlind.unmarshal(p_playerData);
        m_bigBlind.unmarshal(p_playerData);
        m_earlyPos.unmarshal(p_playerData);
        m_middlePos.unmarshal(p_playerData);
        m_cutOff.unmarshal(p_playerData);
    }
}
