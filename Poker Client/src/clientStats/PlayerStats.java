package clientStats;

import java.util.StringTokenizer;

import utility.Constants;

import clientGame.ClientPokerPlayerInfo;



/**
 * PlayerStats.java
 * 
 * @author Hocus
 *         Description: This class logs all the statistics for a specific
 *         player.
 */
public class PlayerStats implements Cloneable
{
    
    private final int PRF = 0;
    private final int FLOP = 1;
    private final int TURN = 2;
    private final int RIVER = 3;
    private final int SHOWDOWN = 4;
    
    StatsAgent m_statsAgent = null;
    ClientPokerPlayerInfo m_myself = null;
    String m_playerName;
    
    // Game State
    PreflopStats m_preflop = null;
    GameState m_flop = null;
    GameState m_turn = null;
    GameState m_river = null;
    
    // Stats
    int m_nbHandsTotal;
    int m_nbWTSD;
    int m_nbPossibleWTSD;
    boolean m_isFold;
    
    /**
     * Ctor
     * 
     * @param p_myself
     *            Handle on the player
     * @param p_statsAgent
     *            Handle on the statsAgent
     */
    public PlayerStats(ClientPokerPlayerInfo p_myself, StatsAgent p_statsAgent)
    {
        m_statsAgent = p_statsAgent;
        m_preflop = new PreflopStats(p_statsAgent, p_myself, this);
        m_flop = new GameState(p_statsAgent, p_myself, this);
        m_turn = new GameState(p_statsAgent, p_myself, this);
        m_river = new GameState(p_statsAgent, p_myself, this);
        m_nbHandsTotal = 0;
        m_nbWTSD = 0;
        m_nbPossibleWTSD = 0;
        m_isFold = false;
        m_myself = p_myself;
        m_playerName = m_myself.m_name;
    }
    
    /**
     * Ctor
     * 
     * @param p_statsAgent
     *            Handle on the statsAgent
     */
    public PlayerStats(StatsAgent p_statsAgent)
    {
        m_statsAgent = p_statsAgent;
        m_preflop = new PreflopStats(p_statsAgent, this);
        m_flop = new GameState(p_statsAgent, this);
        m_turn = new GameState(p_statsAgent, this);
        m_river = new GameState(p_statsAgent, this);
        m_nbHandsTotal = 0;
        m_nbWTSD = 0;
        m_nbPossibleWTSD = 0;
        m_isFold = false;
    }
    
    /**
     * Action is call
     */
    public void call()
    {
        switch (m_statsAgent.m_turnType)
        {
            case PRF:
                m_preflop.call();
                break;
            case FLOP:
                m_flop.call();
                break;
            case TURN:
                m_turn.call();
                break;
            case RIVER:
                m_river.call();
                break;
        }
    }
    
    /**
     * Action is check
     */
    public void check()
    {
        switch (m_statsAgent.m_turnType)
        {
            case PRF:
                m_preflop.check();
                break;
            case FLOP:
                m_flop.check();
                break;
            case TURN:
                m_turn.check();
                break;
            case RIVER:
                m_river.check();
                break;
        }
    }
    
    /**
     * Clone the object
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        final PlayerStats clone = (PlayerStats) super.clone();
        m_preflop = (PreflopStats) m_preflop.clone();
        m_flop = (GameState) m_flop.clone();
        m_turn = (GameState) m_turn.clone();
        m_river = (GameState) m_river.clone();
        
        clone.setPlayerStats();
        return clone;
    }
    
    /**
     * Action is fold
     */
    public void fold()
    {
        m_isFold = true;
        switch (m_statsAgent.m_turnType)
        {
            case PRF:
                m_preflop.fold();
                break;
            case FLOP:
                ++m_nbPossibleWTSD;
                m_flop.fold();
                break;
            case TURN:
                ++m_nbPossibleWTSD;
                m_turn.fold();
                break;
            case RIVER:
                ++m_nbPossibleWTSD;
                m_river.fold();
                break;
            
        }
    }
    
    /**
     * Notify the player that the game end
     */
    public void GameEnded()
    {
        if (!m_isFold)
        {
            switch (m_statsAgent.m_turnType)
            {
                case PRF:
                    break;
                case FLOP:
                    break;
                case TURN:
                    ++m_nbPossibleWTSD;
                    break;
                case RIVER:
                    ++m_nbPossibleWTSD;
                    break;
                case SHOWDOWN:
                    ++m_nbWTSD;
                    ++m_nbPossibleWTSD;
                    break;
            }
        }
        
    }
    
    /**
     * Return the name of the player
     * 
     * @return Name of the player
     */
    public String getName()
    {
        return m_playerName;
    }
    
    /**
     * Returns the number of hands the player played.
     * 
     * @return number of hands the player played.
     */
    public int getNbHands()
    {
        return m_nbHandsTotal;
    }
    
    /**
     * Return the passive factor of the player flop
     * 
     * @return passive factor of the player flop
     */
    public double getPassive_PF()
    {
        int scorePassive = 0;
        if ((getProbCBetFlop() + getProbCBetTurn()) / 2 <= 0.55)
        {
            scorePassive++;
        }
        
        if (getProbBetFlop() <= 0.38)
        {
            scorePassive++;
        }
        
        if (getProbBetTurn() <= 0.38)
        {
            scorePassive++;
        }
        
        if (getProbBetRiver() <= 0.38)
        {
            scorePassive++;
        }
        
        return scorePassive / 4.0;
    }
    
    /**
     * Return the passive factor of the player preflop
     * 
     * @return passive factor of the player preflop
     */
    public double getPassive_PRF()
    {
        int scorePassive = 0;
        if (getProbPFRTotal() <= 0.8)
        {
            ++scorePassive;
        }
        
        if (getProb3BetTotal() <= 0.3)
        {
            ++scorePassive;
        }
        
        if (getProbCOSteal_PRF() <= 0.22)
        {
            ++scorePassive;
        }
        
        if (getProbDSteal_PRF() <= 0.26)
        {
            ++scorePassive;
        }
        
        if (getProbSBSteal_PRF() <= 0.28)
        {
            ++scorePassive;
        }
        
        return scorePassive / 5.0;
    }
    
    /**
     * Return the probability that the player 3bet being the big blind
     * 
     * @return probability that the player 3bet being the big blind
     */
    public Double getProb3Bet_BB()
    {
        return m_preflop.getProb3Bet_BB();
    }
    
    /**
     * Return the probability that the player 3bet being the cutoff
     * 
     * @return probability that the player 3bet being the cutoff
     */
    public Double getProb3Bet_CO()
    {
        return m_preflop.getProb3Bet_CO();
    }
    
    /**
     * Return the probability that the player 3bet being the dealer
     * 
     * @return probability that the player 3bet being the dealer
     */
    public Double getProb3Bet_D()
    {
        return m_preflop.getProb3Bet_D();
    }
    
    /**
     * Return the probability that the player 3bet being in the early position
     * 
     * @return probability that the player 3bet being in the early position
     */
    public Double getProb3Bet_EP()
    {
        return m_preflop.getProb3Bet_EP();
    }
    
    /**
     * Return the probability that the player 3bet being in the middle position
     * 
     * @return probability that the player 3bet being in the middle position
     */
    public Double getProb3Bet_MP()
    {
        return m_preflop.getProb3Bet_MP();
    }
    
    /**
     * Return the probability that the player 3bet being the small blind
     * 
     * @return probability that the player 3bet being the small blind
     */
    public Double getProb3Bet_SB()
    {
        return m_preflop.getProb3Bet_SB();
    }
    
    /**
     * Return the probability that the player 3bet
     * 
     * @return probability that the player 3bet
     */
    public double getProb3BetTotal()
    {
        return (getProb3Bet_BB() + getProb3Bet_SB() + getProb3Bet_D() + getProb3Bet_CO() + getProb3Bet_EP() + getProb3Bet_MP()) / 6.0;
    }
    
    /**
     * Return the probability that the player do a 4bet
     * 
     * @return probability that the player do a 4bet
     */
    public Double getProb4Bet_PRF()
    {
        return m_preflop.getProbFourBet();
    }
    
    /**
     * Return the probability that the player 3bet vs a steal being the big
     * blind
     * 
     * @return probability that the player 3bet vs a steal being the big blind
     */
    public Double getProbBB3BetVsSteal_PRF()
    {
        return m_preflop.getProbBB3BetVsSteal();
    }
    
    /**
     * Return the probability that the player fold vs a steal being the big
     * blind
     * 
     * @return probability that the player fold vs a steal being the big blind
     */
    public Double getProbBBFoldVsSteal_PRF()
    {
        return m_preflop.getProbBBFoldVsSteal();
    }
    
    /**
     * Return the probability that the player bet on the flop
     * 
     * @return probability that the player bet on the flop
     */
    public Double getProbBetFlop()
    {
        return m_flop.getProbBet();
    }
    
    /**
     * Return the probability that the player bet on the river
     * 
     * @return probability that the player bet on the river
     */
    public Double getProbBetRiver()
    {
        return m_river.getProbBet();
    }
    
    /**
     * Return the probability that the player bet on the turn
     * 
     * @return probability that the player bet on the turn
     */
    public Double getProbBetTurn()
    {
        return m_turn.getProbBet();
    }
    
    /**
     * Return the probability that the player Cbet on the flop
     * 
     * @return probability that the player Cbet on the flop
     */
    public Double getProbCBetFlop()
    {
        return m_flop.getProbCBet();
    }
    
    /**
     * Return the probability that the player Cbet on the turn
     * 
     * @return probability that the player Cbet on the turn
     */
    public Double getProbCBetTurn()
    {
        return m_turn.getProbCBet();
    }
    
    /**
     * Return the probability that the player steal being the cutoff
     * 
     * @return probability that the player steal being the cutoff
     */
    public Double getProbCOSteal_PRF()
    {
        return m_preflop.getProbCOSteal();
    }
    
    /**
     * Return the probability that the player steal being the dealer
     * 
     * @return probability that the player steal being the dealer
     */
    public Double getProbDSteal_PRF()
    {
        return m_preflop.getProbDSteal();
    }
    
    /**
     * Return the probability that the player fold vs a Cbet on the flop
     * 
     * @return probability that the player fold vs a Cbet on the flop
     */
    public Double getProbFlopFoldVsCBet()
    {
        return m_flop.getProbFoldToCBet();
    }
    
    /**
     * Return the probability that the player raise vs a Cbet on the flop
     * 
     * @return probability that the player raise vs a Cbet on the flop
     */
    public Double getProbFlopRaiseVsCBet()
    {
        return m_flop.getProbRaiseToCBet();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the big blind
     * 
     * @return probability that the player fold vs a 3bet being the big blind
     */
    public Double getProbFoldTo3Bet_BB()
    {
        return m_preflop.getProbFoldTo3Bet_BB();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the cutoff
     * 
     * @return probability that the player fold vs a 3bet being the cutoff
     */
    public Double getProbFoldTo3Bet_CO()
    {
        return m_preflop.getProbFoldTo3Bet_CO();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the dealer
     * 
     * @return probability that the player fold vs a 3bet being the dealer
     */
    public Double getProbFoldTo3Bet_D()
    {
        return m_preflop.getProbFoldTo3Bet_D();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being in the early
     * position
     * 
     * @return probability that the player fold vs a 3bet being in the early
     *         position
     */
    public Double getProbFoldTo3Bet_EP()
    {
        return m_preflop.getProbFoldTo3Bet_EP();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being in the middle
     * position
     * 
     * @return probability that the player fold vs a 3bet being in the middle
     *         position
     */
    public Double getProbFoldTo3Bet_MP()
    {
        return m_preflop.getProbFoldTo3Bet_MP();
    }
    
    /**
     * Return the probability that the player fold vs a 3bet being the small
     * blind
     * 
     * @return probability that the player fold vs a 3bet being the small blind
     */
    public Double getProbFoldTo3Bet_SB()
    {
        return m_preflop.getProbFoldTo3Bet_SB();
    }
    
    /**
     * Return the probability that the player fold vs a 4bet
     * 
     * @return probability that the player fold vs a 4bet
     */
    public Double getProbFoldTo4Bet_PRF()
    {
        return m_preflop.getProbFoldToFourBet();
    }
    
    /**
     * Return the probability that the player was the pfr being the big blind
     * 
     * @return probability that the player was the pfr being the big blind
     */
    public Double getProbPFR_BB()
    {
        return m_preflop.getProbPFR_BB();
    }
    
    /**
     * Return the probability that the player was the pfr being the cutoff
     * 
     * @return probability that the player was the pfr being the cutoff
     */
    public Double getProbPFR_CO()
    {
        return m_preflop.getProbPFR_CO();
    }
    
    /**
     * Return the probability that the player was the pfr being the dealer
     * 
     * @return probability that the player was the pfr being the dealer
     */
    public Double getProbPFR_D()
    {
        return m_preflop.getProbPFR_D();
    }
    
    /**
     * Return the probability that the player was the pfr being in the early
     * position
     * 
     * @return probability that the player was the pfr being in the early
     *         position
     */
    public Double getProbPFR_EP()
    {
        return m_preflop.getProbPFR_EP();
    }
    
    /**
     * Return the probability that the player was the pfr being in the middle
     * position
     * 
     * @return probability that the player was the pfr being in the middle
     *         position
     */
    public Double getProbPFR_MP()
    {
        return m_preflop.getProbPFR_MP();
    }
    
    /**
     * Return the probability that the player was the pfr being the small blind
     * 
     * @return probability that the player was the pfr being the small blind
     */
    public Double getProbPFR_SB()
    {
        return m_preflop.getProbPFR_SB();
    }
    
    /**
     * Return the probability that the player was the pfr
     * 
     * @return probability that the player was the pfr
     */
    public double getProbPFRTotal()
    {
        return (getProbPFR_BB() + getProbPFR_SB() + getProbPFR_D() + getProbPFR_CO() + getProbPFR_EP() + getProbPFR_MP()) / 6.0;
    }
    
    /**
     * Return the probability that the player 3bet vs a steal being the small
     * blind
     * 
     * @return probability that the player 3bet vs a steal being the small blind
     */
    public Double getProbSB3BetVsSteal_PRF()
    {
        return m_preflop.getProbSB3BetVsSteal();
    }
    
    /**
     * Return the probability that the player fold vs a steal being the small
     * blind
     * 
     * @return probability that the player fold vs a steal being the small blind
     */
    public Double getProbSBFoldVsSteal_PRF()
    {
        return m_preflop.getProbSBFoldVsSteal();
    }
    
    /**
     * Return the probability that the player steal being the small blind
     * 
     * @return probability that the player steal being the small blind
     */
    public Double getProbSBSteal_PRF()
    {
        return m_preflop.getProbSBSteal();
    }
    
    /**
     * Return the probability that the player fold vs a Cbet on the turn
     * 
     * @return probability that the player fold vs a Cbet on the turn
     */
    public Double getProbTurnFoldVsCBet()
    {
        return m_turn.getProbFoldToCBet();
    }
    
    /**
     * Return the probability that the player has voluntary put money in the pot
     * 
     * @return probability that the player has voluntary put money in the pot
     */
    public Double getProbVPIPTotal_PRF()
    {
        if (m_nbHandsTotal == 0)
        {
            return 0.0;
        }
        
        return m_preflop.getNbVPIPTotal() / (double) m_nbHandsTotal;
    }
    
    /**
     * Return the probability that the player go to the showdown if he saw the
     * flop
     * 
     * @return probability that the player go to the showdown if he saw the flop
     */
    public Double getProbWTSD()
    {
        if (m_nbPossibleWTSD == 0)
        {
            return 0.0;
        }
        
        return m_nbWTSD / (double) m_nbPossibleWTSD;
    }
    
    /**
     * Return true if the player is the big blind.
     * 
     * @return true if the player is the big blind.
     */
    public boolean isBigBlind()
    {
        return m_myself.m_isBigBlind;
    }
    
    /**
     * Return true if the player is the cutoff.
     * 
     * @return true if the player is the cutoff.
     */
    public boolean isCutOff()
    {
        return m_myself.m_isCutOff;
    }
    
    /**
     * Return true if the player is the dealer.
     * 
     * @return true if the player is the dealer.
     */
    public boolean isDealer()
    {
        return m_myself.m_isDealer;
    }
    
    /**
     * Return true if the player is the small blind.
     * 
     * @return true if the player is the small blind.
     */
    public boolean isSmallBlind()
    {
        return m_myself.m_isSmallBlind;
    }
    
    /**
     * Return true if the player is tight
     * 
     * @return true if the player is tight
     */
    public boolean isTight()
    {
        return getProbVPIPTotal_PRF() < 0.15;
    }
    
    /**
     * Notify the player he was the last raiser
     */
    public void lastRaiser()
    {
        switch (m_statsAgent.m_turnType)
        {
            case PRF:
                m_preflop.isLastRaiser();
                break;
            case FLOP:
                break;
            case TURN:
                break;
            case RIVER:
                break;
        }
    }
    
    /**
     * Marshal all the data of the player in a string
     * 
     * @return all the data for this player
     */
    public String marshal()
    {
        final StringBuilder marshalData = new StringBuilder();
        
        marshalData.append(m_playerName);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbHandsTotal);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbWTSD);
        marshalData.append(Constants.DELIMITER);
        
        marshalData.append(m_nbPossibleWTSD);
        marshalData.append(Constants.DELIMITER);
        
        // Append each street
        marshalData.append(m_preflop.marshal());// 63
        marshalData.append(m_flop.marshal());// 71
        marshalData.append(m_turn.marshal());
        marshalData.append(m_river.marshal());
        
        return marshalData.toString();
    }
    
    /**
     * Indicate that a new hand is starting
     */
    public void playNewHand()
    {
        ++m_nbHandsTotal;
        m_preflop.playNewHand();
        m_isFold = false;
    }
    
    /**
     * Action is raise
     */
    public void raise()
    {
        switch (m_statsAgent.m_turnType)
        {
            case PRF:
                m_preflop.raise();
                break;
            case FLOP:
                m_flop.raise();
                break;
            case TURN:
                m_turn.raise();
                break;
            case RIVER:
                m_river.raise();
                break;
            
        }
    }
    
    /**
     * Set the player
     * 
     * @param p_myself
     *            player
     */
    public void setPlayer(ClientPokerPlayerInfo p_myself)
    {
        m_myself = p_myself;
        m_preflop.setPlayer(p_myself);
        m_flop.setPlayer(p_myself);
        m_turn.setPlayer(p_myself);
        m_river.setPlayer(p_myself);
        m_playerName = m_myself.m_name;
    }
    
    /**
     * Set the playerStats
     */
    public void setPlayerStats()
    {
        m_preflop.setPlayerStats(this);
        m_flop.setPlayerStats(this);
        m_turn.setPlayerStats(this);
        m_river.setPlayerStats(this);
        
    }
    
    /**
     * Notify the player that the turn end
     */
    public void TurnEnd()
    {
        switch (m_statsAgent.m_turnType)
        {
            case PRF:
                m_preflop.isLastRaiser();
                break;
            case FLOP:
                break;
            case TURN:
                break;
            case RIVER:
                break;
        }
        
    }
    
    /**
     * Extract all the data from the player
     * 
     * @param p_playerName
     *            Name of the player
     * @param p_playerData
     *            Data of the player
     */
    public void unmarshal(String p_playerName, StringTokenizer p_playerData)
    {
        m_playerName = p_playerName;
        m_nbHandsTotal = Integer.parseInt(p_playerData.nextToken());
        m_nbWTSD = Integer.parseInt(p_playerData.nextToken());
        m_nbPossibleWTSD = Integer.parseInt(p_playerData.nextToken());
        
        m_preflop.unmarshal(p_playerData);
        m_flop.unmarshal(p_playerData);
        m_turn.unmarshal(p_playerData);
        m_river.unmarshal(p_playerData);
        
    }
    
}
