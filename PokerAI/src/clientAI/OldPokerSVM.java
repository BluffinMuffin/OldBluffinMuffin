package clientAI;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import pokerAI.SVM;
import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldPokerPlayerInfo;
import pokerLogic.OldTypePlayerAction;
import pokerLogic.OldTypePokerRound;
import pokerStats.MonteCarlo;
import pokerStats.StatsInfos;
import utility.Constants;
import utility.Tool;
import clientGame.ClientPokerPlayerInfo;
import clientGameTools.ClientPokerAdapter;
import clientGameTools.ClientPokerObserver;
import clientGameTools.TypeSimplifiedAction;
import clientStats.StatsAgent;

/**
 * @author Hocus
 *         This class represents an artificial poker player.
 *         To make its decision, the agent uses SVMs.<br>
 * <br>
 *         See documentation for explaination about the algorithm used.
 */
public class OldPokerSVM extends OldPokerAI
{
    private final static int NB_MAX_NB_RAISE = 3;
    /** Number of times the agent raise in a row. **/
    private int m_cptRaise = 0;
    
    private final static String DEFAULT_FILENAME = "Default.mem";
    /** Is the file in which the statistics will be saved. **/
    private String m_filename = OldPokerSVM.DEFAULT_FILENAME;
    
    private SVM m_SVM_preflopFold = null;
    private SVM m_SVM_preflopRaise = null;
    private SVM m_SVM_postflopFold = null;
    private SVM m_SVM_postflopRaise = null;
    
    // These files need to have the format required by LIBSVM.
    private final static String SVM_PREFLOP_FOLD = "preflopFold.svm";
    private final static String SVM_PREFLOP_RAISE = "preflopRaise.svm";
    private final static String SVM_POSTFLOP_FOLD = "postflopFold.svm";
    private final static String SVM_POSTFLOP_RAISE = "postflopRaise.svm";
    
    private StatsAgent m_statsAgent = null;
    
    private boolean m_isLoading = false;
    
    /*
     * ****************************************************
     * Building the vector to make a prediction with a SVM.
     * ****************************************************
     */

    private final static int NB_STATS_PREFLOP = 28;
    
    private final static int NB_STATS_POSTFLOP = 9;
    
    private final static int NB_STATS = OldPokerSVM.NB_STATS_PREFLOP + OldPokerSVM.NB_STATS_POSTFLOP;
    
    private final static int NB_MC_ITERATIONS = 10000;
    
    // private final static int NB_PLAYER_INFOS = NB_OTHER_OPPONENTS_INFOS +
    // NB_STATS;
    
    /**
     * Better round algorithm, for better precision.
     * 
     * @param value
     *            - Value to round up.
     * @param decimalPlace
     *            - Number of decimal kept after rounding.
     * @return
     *         New rounded value.
     */
    public static double round(double value, int decimalPlace)
    {
        double power_of_ten = 1;
        // floating point arithmetic can be very tricky.
        // that's why I introduce a "fudge factor"
        double fudge_factor = 0.05;
        while (decimalPlace-- > 0)
        {
            power_of_ten *= 10.0d;
            fudge_factor /= 10.0d;
        }
        return Math.round((value + fudge_factor) * power_of_ten) / power_of_ten;
    }
    
    private Integer m_cptID = 1;
    
    public OldPokerSVM()
    {
    }
    
    /**
     * Initialize the StatsAgent by loading data from the .mem file.
     * 
     * @param p_statsAgent
     *            - Agent required to captured statictics.
     * @param p_playerName
     *            - Name of the agent used to load the .mem file.
     */
    public OldPokerSVM(StatsAgent p_statsAgent, String p_playerName)
    {
        this();
        loadSVMs();
        m_statsAgent = p_statsAgent;
        
        if (m_filename == OldPokerSVM.DEFAULT_FILENAME)
        {
            m_filename = p_playerName + ".mem";
            
            try
            {
                // Load all statistics for all known player.
                final ArrayList<String> playersData = new ArrayList<String>();
                final BufferedReader memory = new BufferedReader(new FileReader(m_filename));
                String line = memory.readLine();
                
                while (line != null)
                {
                    playersData.add(line);
                    line = memory.readLine();
                }
                
                m_statsAgent.unmarshal(playersData);
            }
            catch (final FileNotFoundException e)
            {
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected OldPokerPlayerAction analyze(ArrayList<OldTypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        while (!isReady())
        {
            try
            {
                Thread.sleep(500);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        
        if (m_table.m_gameState == OldTypePokerRound.PREFLOP)
        {
            if (getSVM_PreflopFold().predict(getVector(OldTypePokerRound.PREFLOP)) <= 0)
            {
                m_cptRaise = 0;
                return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
            }
            else
            {
                if (getSVM_PreflopaRaise().predict(getVector(OldTypePokerRound.PREFLOP)) > 0)
                {
                    // If we should raise, we need to check if m_cptRaise is
                    // under a certain constant.
                    if (p_actionsAllowed.contains(OldTypePlayerAction.RAISE) && (m_cptRaise < OldPokerSVM.NB_MAX_NB_RAISE))
                    {
                        m_cptRaise++;
                        return new OldPokerPlayerAction(OldTypePlayerAction.RAISE, Math.min(p_minRaiseAmount + 3 * m_table.m_bigBlindAmount, p_maxRaiseAmount));
                    }
                    else if (p_actionsAllowed.contains(OldTypePlayerAction.CALL))
                    {
                        m_cptRaise = 0;
                        return new OldPokerPlayerAction(OldTypePlayerAction.CALL);
                    }
                    else
                    {
                        m_cptRaise = 0;
                        System.out.println("Wanted to RAISE, but cannot!!!!");
                        return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
                    }
                }
                else
                {
                    m_cptRaise = 0;
                    if (p_actionsAllowed.contains(OldTypePlayerAction.CALL))
                    {
                        return new OldPokerPlayerAction(OldTypePlayerAction.CALL);
                    }
                    else if (p_actionsAllowed.contains(OldTypePlayerAction.CHECK))
                    {
                        return new OldPokerPlayerAction(OldTypePlayerAction.CHECK);
                    }
                    else
                    {
                        System.out.println("Wanted to CHECK/CALL, but cannot!!!!");
                        return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
                    }
                }
            }
        }
        else
        {
            if (getSVM_PostflopFold().predict(getVector(OldTypePokerRound.FLOP)) <= 0)
            {
                m_cptRaise = 0;
                return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
            }
            else
            {
                if (getSVM_PostflopRaise().predict(getVector(OldTypePokerRound.FLOP)) > 0)
                {
                    // If we should raise, we need to check if m_cptRaise is
                    // under a certain constant.
                    if (p_actionsAllowed.contains(OldTypePlayerAction.RAISE) && (m_cptRaise < OldPokerSVM.NB_MAX_NB_RAISE))
                    {
                        m_cptRaise++;
                        return new OldPokerPlayerAction(OldTypePlayerAction.RAISE, Math.min(p_minRaiseAmount + 3 * m_table.m_bigBlindAmount, p_maxRaiseAmount));
                    }
                    else if (p_actionsAllowed.contains(OldTypePlayerAction.CALL))
                    {
                        m_cptRaise = 0;
                        return new OldPokerPlayerAction(OldTypePlayerAction.CALL);
                    }
                    else
                    {
                        m_cptRaise = 0;
                        System.out.println("Wanted to RAISE, but cannot!!!!");
                        return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
                    }
                }
                else
                {
                    m_cptRaise = 0;
                    if (p_actionsAllowed.contains(OldTypePlayerAction.CALL))
                    {
                        return new OldPokerPlayerAction(OldTypePlayerAction.CALL);
                    }
                    else if (p_actionsAllowed.contains(OldTypePlayerAction.CHECK))
                    {
                        return new OldPokerPlayerAction(OldTypePlayerAction.CHECK);
                    }
                    else
                    {
                        System.out.println("Wanted to CHECK/CALL, but cannot!!!!");
                        return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
                    }
                }
            }
        }
    }
    
    /**
     * Return values obtains by running Monte Carlo.
     * 
     * @return
     *         Monte Carlo's values
     */
    private StatsInfos calculateHandValues()
    {
        final GameCard[] holeCards = m_table.m_localPlayer.getHand();
        final GameCard[] boardCards = m_table.m_boardCards.toArray(new GameCard[m_table.m_boardCards.size()]);
        
        return MonteCarlo.CalculateWinRatio(holeCards, boardCards, m_table.m_nbPlayingPlayers, OldPokerSVM.NB_MC_ITERATIONS);
    }
    
    @SuppressWarnings("unchecked")
    /**
     * Format value of the object to be compatible with SVM.
     */
    private String format(Object p_object)
    {
        String text = "";
        
        if (p_object == null)
        {
            // No need to add null value, because SVM treat vector like sparse
            // matrix.
            // text = m_cptID.toString() + ":" + NOT_AVAILABLE + SEPARATOR;
        }
        else if (p_object.getClass().isEnum())
        {
            text = m_cptID.toString() + ":" + ((Enum) p_object).ordinal() + ' ';
        }
        else if (p_object instanceof Double)
        {
            final double value = OldPokerSVM.round(((Double) p_object).doubleValue(), 3);
            if ((int) value == value)
            {
                if (value != 0.0)
                {
                    text = m_cptID.toString() + ":" + (int) value + ' ';
                }
            }
            else if (value != 0.0)
            {
                text = m_cptID.toString() + ":" + String.format(Locale.US, "%.3f", value) + ' ';
            }
        }
        else if (p_object instanceof Integer)
        {
            final int value = ((Integer) p_object).intValue();
            if (value != 0)
            {
                text = m_cptID.toString() + ":" + value + ' ';
            }
        }
        else
        {
            text = m_cptID.toString() + ":" + p_object.toString() + ' ';
        }
        
        m_cptID++;
        
        return text;
    }
    
    /**
     * Format a enum's value in binary.
     * 
     * @param p_enum
     *            - Enum's value.
     * @param p_class
     *            - Enum's class.
     * @return
     *         Binary representation of the value.
     */
    @SuppressWarnings("unchecked")
    private String formatEnum(Enum p_enum, Class p_class)
    {
        final StringBuilder sb = new StringBuilder();
        
        if (p_enum != null)
        {
            for (final String bit : Tool.formatEnum(p_enum))
            {
                if (bit.equals("1"))
                {
                    sb.append(m_cptID);
                    sb.append(":");
                    sb.append(bit);
                    sb.append(Constants.SVM_SEPARATOR);
                }
                
                m_cptID++;
            }
        }
        else
        {
            for (int i = 0; i != p_class.getFields().length; ++i)
            {
                m_cptID++;
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Format a player's position in binary.
     * 
     * @param p_position
     *            - Player's position.
     * @return
     *         Binary representation of the position.
     */
    private String formatPosition(int p_position)
    {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 1; i != 10; ++i)
        {
            if (i == p_position)
            {
                sb.append(m_cptID);
                sb.append(":");
                sb.append(1);
                sb.append(Constants.SVM_SEPARATOR);
            }
            
            m_cptID++;
        }
        
        return sb.toString();
    }
    
    /**
     * Get SVM corresponding to the Postflop-Fold decisions.
     * If not loaded, loads it.
     * 
     * @return
     *         SVM
     */
    private SVM getSVM_PostflopFold()
    {
        if (m_SVM_postflopFold == null)
        {
            m_SVM_postflopFold = new SVM(OldPokerSVM.SVM_POSTFLOP_FOLD);
        }
        
        return m_SVM_postflopFold;
    }
    
    /**
     * Get SVM corresponding to the Postflop-Raise decisions.
     * If not loaded, loads it.
     * 
     * @return
     *         SVM
     */
    private SVM getSVM_PostflopRaise()
    {
        if (m_SVM_postflopRaise == null)
        {
            m_SVM_postflopRaise = new SVM(OldPokerSVM.SVM_POSTFLOP_RAISE);
        }
        
        return m_SVM_postflopRaise;
    }
    
    /**
     * Get SVM corresponding to the Preflop-Raise decisions.
     * If not loaded, loads it.
     * 
     * @return
     *         SVM
     */
    private SVM getSVM_PreflopaRaise()
    {
        if (m_SVM_preflopRaise == null)
        {
            m_SVM_preflopRaise = new SVM(OldPokerSVM.SVM_PREFLOP_RAISE);
        }
        
        return m_SVM_preflopRaise;
    }
    
    /**
     * Get SVM corresponding to the Preflop-Fold decisions.
     * If not loaded, loads it.
     * 
     * @return
     *         SVM
     */
    private SVM getSVM_PreflopFold()
    {
        if (m_SVM_preflopFold == null)
        {
            m_SVM_preflopFold = new SVM(OldPokerSVM.SVM_PREFLOP_FOLD);
        }
        
        return m_SVM_preflopFold;
    }
    
    /**
     * Build a vector that will be used to make a prediction with a SVM.
     * 
     * @param p_state
     *            - State of the poker game.
     * @return
     *         Vector of attributes
     */
    private String getVector(OldTypePokerRound p_state)
    {
        double highMoney = 0.0;
        for (final OldPokerPlayerInfo player : m_table.getPlayers())
        {
            highMoney = Math.max(highMoney, player.getMoney());
        }
        
        m_cptID = 1;
        final StatsInfos infos = calculateHandValues();
        
        final StringBuilder sb = new StringBuilder();
        sb.append(Constants.SVM_SEPARATOR);
        sb.append(format(infos.m_winRatio)); // 1
        sb.append(format(infos.m_standardDeviation)); // 2
        sb.append(format(m_table.m_localPlayer.getMoney() / highMoney)); // 3
        sb.append(format((double) (m_callAmount - m_table.m_localPlayer.m_betAmount) / (double) m_table.m_localPlayer.m_initialMoney));// 4
        sb.append(format((double) (m_callAmount - m_table.m_localPlayer.m_betAmount) / (double) m_table.m_totalPotAmount));// 5
        sb.append(formatPosition(m_table.m_localPlayer.m_relativePosition)); // 5-13
        
        sb.append(formatEnum(((ClientPokerPlayerInfo) m_table.m_localPlayer).m_lastActionsPreflop, TypeSimplifiedAction.class)); // 14-20
        sb.append(formatEnum(((ClientPokerPlayerInfo) m_table.m_localPlayer).m_lastActionsFlop, TypeSimplifiedAction.class)); // 21-27
        sb.append(formatEnum(((ClientPokerPlayerInfo) m_table.m_localPlayer).m_lastActionsTurn, TypeSimplifiedAction.class)); // 28-34
        sb.append(formatEnum(((ClientPokerPlayerInfo) m_table.m_localPlayer).m_lastActionsRiver, TypeSimplifiedAction.class)); // 35-41
        
        sb.append(format((double) m_table.m_totalPotAmount / (double) m_table.m_localPlayer.getMoney())); // 42
        sb.append(formatEnum(p_state, OldTypePokerRound.class)); // 43-46
        // sb.append(format(m_currentInfos.m_players.size())); //47
        
        for (final OldPokerPlayerInfo player : m_table.getPlayers())
        {
            if (player == m_table.m_localPlayer)
            {
                continue;
            }
            if (!player.m_isPlaying)
            {
                continue;
            }
            
            sb.append(formatPosition(player.m_relativePosition)); // 48-56
            sb.append(format(player.getMoney() / highMoney)); // 57
            sb.append(format((double) player.m_betAmount / (double) player.m_initialMoney)); // 58
            sb.append(formatEnum(((ClientPokerPlayerInfo) player).m_lastActionsPreflop, TypeSimplifiedAction.class)); // 59-65
            sb.append(formatEnum(((ClientPokerPlayerInfo) player).m_lastActionsFlop, TypeSimplifiedAction.class)); // 66-72
            sb.append(formatEnum(((ClientPokerPlayerInfo) player).m_lastActionsTurn, TypeSimplifiedAction.class)); // 73-79
            sb.append(formatEnum(((ClientPokerPlayerInfo) player).m_lastActionsRiver, TypeSimplifiedAction.class)); // 80-86
            
            if (m_statsAgent.m_overallStats.get(player.m_name) == null)
            {
                for (int j = 0; j != OldPokerSVM.NB_STATS; ++j)
                {
                    sb.append(format(null)); // 87-123
                }
            }
            else
            {
                if (p_state == OldTypePokerRound.PREFLOP)
                {
                    // Stats preflop
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbVPIPTotal_PRF())); // 87
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb4Bet_PRF())); // 88
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo4Bet_PRF())); // 89
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbSBFoldVsSteal_PRF())); // 90
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBBFoldVsSteal_PRF())); // 91
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbCOSteal_PRF())); // 92
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbDSteal_PRF())); // 93
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbSBSteal_PRF())); // 94
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbSB3BetVsSteal_PRF())); // 95
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBB3BetVsSteal_PRF())); // 96
                    
                    // PreflopRaise by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_BB())); // 97
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_CO())); // 98
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_D())); // 99
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_EP())); // 100
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_MP())); // 101
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbPFR_SB())); // 102
                    
                    // 3bet% by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_BB())); // 103
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_CO())); // 104
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_D())); // 105
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_EP())); // 106
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_MP())); // 107
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProb3Bet_SB())); // 108
                    
                    // fold to 3bet% by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_BB())); // 109
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_CO())); // 110
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_D())); // 111
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_EP())); // 112
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_MP())); // 113
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFoldTo3Bet_SB())); // 114
                    
                    // Stats postflop
                    for (int j = 0; j != OldPokerSVM.NB_STATS_POSTFLOP; ++j)
                    {
                        sb.append(format(null)); // 115-123
                    }
                }
                else
                {
                    // Stats preflop
                    for (int j = 0; j != OldPokerSVM.NB_STATS_PREFLOP; ++j)
                    {
                        sb.append(format(null)); // 87-114
                    }
                    
                    // Stats postflop
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBetFlop())); // 115
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBetTurn())); // 116
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbBetRiver())); // 117
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbCBetFlop())); // 118
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbCBetTurn())); // 119
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFlopFoldVsCBet())); // 120
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbFlopRaiseVsCBet())); // 121
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbTurnFoldVsCBet())); // 122
                    sb.append(format(m_statsAgent.m_overallStats.get(player.m_name).getProbWTSD())); // 123
                }
            }
        }
        
        // for (int i= m_table.m_players.size()+1; i != 10; ++i)
        // {
        // for (int j= 0; j != NB_PLAYER_INFOS; ++j)
        // sb.append(format(null));//48-123
        // }
        
        return sb.toString();
    }
    
    /**
     * Indicates if all SVMs have been loaded..
     * 
     * @return
     *         Loading is done. (SVMs)
     */
    public boolean isReady()
    {
        return !m_isLoading;
    }
    
    /**
     * Load SVMs in another thread.
     * When done m_isLoading will be true.
     */
    private void loadSVMs()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                m_isLoading = true;
                m_SVM_preflopFold = new SVM(OldPokerSVM.SVM_PREFLOP_FOLD);
                m_SVM_preflopRaise = new SVM(OldPokerSVM.SVM_PREFLOP_RAISE);
                m_SVM_postflopFold = new SVM(OldPokerSVM.SVM_POSTFLOP_FOLD);
                m_SVM_postflopRaise = new SVM(OldPokerSVM.SVM_POSTFLOP_RAISE);
                m_isLoading = false;
            }
        }.start();
    }
    
    /**
     * Save all statistics in a .mem file.
     */
    private void saveMemory()
    {
        try
        {
            final BufferedWriter memory = new BufferedWriter(new FileWriter(m_filename));
            for (final String playeurInfos : m_statsAgent.marshal())
            {
                memory.write(playeurInfos);
                memory.write("\n");
                memory.flush();
            }
            memory.close();
        }
        catch (final IOException e)
        {
        }
    }
    
    @Override
    public String toString()
    {
        return "Poker SVM AI";
    }
    
    @Override
    public void setPokerObserver(ClientPokerObserver observer)
    {
        super.setPokerObserver(observer);
        initializePokerObserver(observer);
    }
    
    private void initializePokerObserver(ClientPokerObserver observer)
    {
        observer.subscribe(new ClientPokerAdapter()
        {
            
            @Override
            public void gameEnded()
            {
                saveMemory();
            }
            
        });
        
    }
}
