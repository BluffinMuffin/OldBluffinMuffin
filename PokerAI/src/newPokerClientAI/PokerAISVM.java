package newPokerClientAI;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import newPokerLogic.IPokerGame;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;
import newPokerLogicTools.PokerGameAdapter;
import newPokerLogicTools.PokerGameObserver;
import pokerAI.SVM;
import pokerStats.MonteCarlo;
import pokerStats.StatsInfos;
import utility.Constants;
import utility.Tool;
import clientStats.StatsAgent;

/**
 * @author Hocus
 *         This class represents an artificial poker player.
 *         To make its decision, the agent uses SVMs.<br>
 * <br>
 *         See documentation for explaination about the algorithm used.
 */
public class PokerAISVM extends AbstractPokerAI
{
    private final static int NB_MAX_NB_RAISE = 3;
    /** Number of times the agent raise in a row. **/
    private int m_cptRaise = 0;
    
    private final static String DEFAULT_FILENAME = "Default.mem";
    /** Is the file in which the statistics will be saved. **/
    private String m_filename = PokerAISVM.DEFAULT_FILENAME;
    
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
    
    private final static int NB_STATS = PokerAISVM.NB_STATS_PREFLOP + PokerAISVM.NB_STATS_POSTFLOP;
    private final static int NB_MC_ITERATIONS = 10000;
    
    // private final static int NB_PLAYER_INFOS = NB_OTHER_OPPONENTS_INFOS +
    // NB_STATS;
    
    @Override
    public void init(IPokerGame game, int seatViewed, PokerGameObserver observer)
    {
        super.init(game, seatViewed, observer);
        initializePokerObserver();
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            @Override
            public void gameEnded()
            {
                saveMemory();
            }
        });
    }
    
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
    
    public PokerAISVM()
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
    public PokerAISVM(StatsAgent p_statsAgent, String p_playerName)
    {
        this();
        loadSVMs();
        m_statsAgent = p_statsAgent;
        
        if (m_filename == PokerAISVM.DEFAULT_FILENAME)
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
    protected int playMoney()
    {
        final PokerTableInfo table = m_game.getPokerTable();
        final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
        final boolean canRaise = table.getCurrentHigherBet() >= (p.getCurrentBetMoneyAmount() + p.getCurrentBetMoneyAmount());
        final int needed = table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
        final int maxRaise = (p.getCurrentBetMoneyAmount() + p.getCurrentBetMoneyAmount()) - table.getCurrentHigherBet();
        final int minRaise = Math.min(needed + table.getBigBlindAmount(), maxRaise);
        
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
        
        if (table.getCurrentGameRound() == TypePokerGameRound.PREFLOP)
        {
            if (getSVM_PreflopFold().predict(getVector(TypePokerGameRound.PREFLOP, p, table)) <= 0)
            {
                m_cptRaise = 0;
                return super.playMoney();
            }
            else
            {
                if (getSVM_PreflopaRaise().predict(getVector(TypePokerGameRound.PREFLOP, p, table)) > 0)
                {
                    // If we should raise, we need to check if m_cptRaise is
                    // under a certain constant.
                    if (canRaise && (m_cptRaise < PokerAISVM.NB_MAX_NB_RAISE))
                    {
                        m_cptRaise++;
                        return Math.min(minRaise + 3 * table.getBigBlindAmount(), minRaise);
                    }
                    else
                    {
                        m_cptRaise = 0;
                        return needed;
                    }
                }
                else
                {
                    m_cptRaise = 0;
                    return needed;
                }
            }
        }
        else
        {
            if (getSVM_PostflopFold().predict(getVector(TypePokerGameRound.FLOP, p, table)) <= 0)
            {
                m_cptRaise = 0;
                return super.playMoney();
            }
            else
            {
                if (getSVM_PostflopRaise().predict(getVector(TypePokerGameRound.FLOP, p, table)) > 0)
                {
                    // If we should raise, we need to check if m_cptRaise is
                    // under a certain constant.
                    if (canRaise && (m_cptRaise < PokerAISVM.NB_MAX_NB_RAISE))
                    {
                        m_cptRaise++;
                        return Math.min(minRaise + 3 * table.getBigBlindAmount(), minRaise);
                    }
                    else
                    {
                        m_cptRaise = 0;
                        return needed;
                    }
                }
                else
                {
                    m_cptRaise = 0;
                    return needed;
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
    
    private StatsInfos calculateHandValues(PokerPlayerInfo p, PokerTableInfo table)
    {
        final GameCard[] myCards = p.getCurrentHand(true);
        final GameCard[] myBoardCards = new GameCard[5];
        table.getCurrentBoardCards().toArray(myBoardCards);
        
        final StatsInfos score = MonteCarlo.CalculateWinRatio(myCards, myBoardCards, table.getNbPlaying(), PokerAISVM.NB_MC_ITERATIONS);
        
        return score;
    }
    
    /**
     * Format value of the object to be compatible with SVM.
     */
    @SuppressWarnings("unchecked")
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
            final double value = PokerAISVM.round(((Double) p_object).doubleValue(), 3);
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
            m_SVM_postflopFold = new SVM(PokerAISVM.SVM_POSTFLOP_FOLD);
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
            m_SVM_postflopRaise = new SVM(PokerAISVM.SVM_POSTFLOP_RAISE);
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
            m_SVM_preflopRaise = new SVM(PokerAISVM.SVM_PREFLOP_RAISE);
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
            m_SVM_preflopFold = new SVM(PokerAISVM.SVM_PREFLOP_FOLD);
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
    private String getVector(TypePokerGameRound p_state, PokerPlayerInfo p, PokerTableInfo table)
    {
        double highMoney = 0.0;
        for (final PokerPlayerInfo player : table.getPlayers())
        {
            highMoney = Math.max(highMoney, player.getCurrentSafeMoneyAmount());
        }
        
        m_cptID = 1;
        final StatsInfos infos = calculateHandValues(p, table);
        
        final StringBuilder sb = new StringBuilder();
        sb.append(Constants.SVM_SEPARATOR);
        sb.append(format(infos.m_winRatio)); // 1
        sb.append(format(infos.m_standardDeviation)); // 2
        sb.append(format(p.getCurrentSafeMoneyAmount() / highMoney)); // 3
        sb.append(format((table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount()) / (double) p.getInitialMoneyAmount()));// 4
        sb.append(format((table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount()) / (double) table.getTotalPotAmount()));// 5
        sb.append(formatPosition(p.getCurrentTablePosition())); // 5-13
        
        // TODO: SVM WANNA DIE TypeSimplifiedAction
        sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 14-20
        sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 21-27
        sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 28-34
        sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 35-41
        
        sb.append(format((double) table.getTotalPotAmount() / (double) p.getCurrentSafeMoneyAmount())); // 42
        sb.append(formatEnum(p_state, TypePokerGameRound.class)); // 43-46
        // sb.append(format(m_currentInfos.m_players.size())); //47
        
        for (final PokerPlayerInfo player : table.getPlayers())
        {
            if (player.getCurrentTablePosition() == m_currentTablePosition)
            {
                continue;
            }
            if (!player.isPlaying())
            {
                continue;
            }
            
            sb.append(formatPosition(player.getCurrentTablePosition())); // 48-56
            sb.append(format(player.getCurrentSafeMoneyAmount() / highMoney)); // 57
            sb.append(format((double) player.getCurrentBetMoneyAmount() / (double) player.getInitialMoneyAmount())); // 58
            
            // TODO: SVM WANNA DIE TypeSimplifiedAction
            sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 59-65
            sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 66-72
            sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 73-79
            sb.append(formatEnum(TypePokerGameAction.CALLED, TypePokerGameAction.class)); // 80-86
            
            if (m_statsAgent.m_overallStats.get(player.getPlayerName()) == null)
            {
                for (int j = 0; j != PokerAISVM.NB_STATS; ++j)
                {
                    sb.append(format(null)); // 87-123
                }
            }
            else
            {
                if (p_state == TypePokerGameRound.PREFLOP)
                {
                    // Stats preflop
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbVPIPTotal_PRF())); // 87
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb4Bet_PRF())); // 88
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo4Bet_PRF())); // 89
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbSBFoldVsSteal_PRF())); // 90
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbBBFoldVsSteal_PRF())); // 91
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbCOSteal_PRF())); // 92
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbDSteal_PRF())); // 93
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbSBSteal_PRF())); // 94
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbSB3BetVsSteal_PRF())); // 95
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbBB3BetVsSteal_PRF())); // 96
                    
                    // PreflopRaise by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbPFR_BB())); // 97
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbPFR_CO())); // 98
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbPFR_D())); // 99
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbPFR_EP())); // 100
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbPFR_MP())); // 101
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbPFR_SB())); // 102
                    
                    // 3bet% by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb3Bet_BB())); // 103
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb3Bet_CO())); // 104
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb3Bet_D())); // 105
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb3Bet_EP())); // 106
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb3Bet_MP())); // 107
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProb3Bet_SB())); // 108
                    
                    // fold to 3bet% by position
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo3Bet_BB())); // 109
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo3Bet_CO())); // 110
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo3Bet_D())); // 111
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo3Bet_EP())); // 112
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo3Bet_MP())); // 113
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFoldTo3Bet_SB())); // 114
                    
                    // Stats postflop
                    for (int j = 0; j != PokerAISVM.NB_STATS_POSTFLOP; ++j)
                    {
                        sb.append(format(null)); // 115-123
                    }
                }
                else
                {
                    // Stats preflop
                    for (int j = 0; j != PokerAISVM.NB_STATS_PREFLOP; ++j)
                    {
                        sb.append(format(null)); // 87-114
                    }
                    
                    // Stats postflop
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbBetFlop())); // 115
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbBetTurn())); // 116
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbBetRiver())); // 117
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbCBetFlop())); // 118
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbCBetTurn())); // 119
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFlopFoldVsCBet())); // 120
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbFlopRaiseVsCBet())); // 121
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbTurnFoldVsCBet())); // 122
                    sb.append(format(m_statsAgent.m_overallStats.get(player.getPlayerName()).getProbWTSD())); // 123
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
                m_SVM_preflopFold = new SVM(PokerAISVM.SVM_PREFLOP_FOLD);
                m_SVM_preflopRaise = new SVM(PokerAISVM.SVM_PREFLOP_RAISE);
                m_SVM_postflopFold = new SVM(PokerAISVM.SVM_POSTFLOP_FOLD);
                m_SVM_postflopRaise = new SVM(PokerAISVM.SVM_POSTFLOP_RAISE);
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
}
