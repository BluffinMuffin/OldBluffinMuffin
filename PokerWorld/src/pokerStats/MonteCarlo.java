package pokerStats;

import java.util.ArrayList;
import java.util.Collections;

import basePoker.Card;


/**
 * @author Hocus
 *         This class perform a simulation of NB_SIMULATION game with the current known cards.
 */
public class MonteCarlo
{
    private static final int NB_SIMULATION = 10000;
    private static final int NB_THREADS = 1;
    public static final int NB_BOARDCARDS = 5;
    public static final int NB_HOLECARDS = 2;
    public static final int MAX_DECK_SIZE = 52; // 54 - 2jokers
    
    /**
     * Calculate the probability to win with the known cards
     * 
     * @param p_playerCards
     *            Cards of the player
     * @param p_tableCards
     *            Cards on the table
     * @param p_nbPlayers
     *            Number of players
     * @param p_nbIterations
     *            Number of wanted iteration
     * @return Number of game won
     */
    public static long CalculateNbWins(Card[] p_playerCards, Card[] p_tableCards, int p_nbPlayers, long p_nbIterations)
    {
        if ((p_playerCards[0].getId() < 0) || (p_playerCards[1].getId() < 0))
        {
            return 0;
        }
        
        int nbBoardCards = 0;
        while ((nbBoardCards != p_tableCards.length) && (p_tableCards[nbBoardCards].getId() >= 0))
        {
            nbBoardCards++;
        }
        
        final int nbOpponents = p_nbPlayers - 1;
        final int nbMissingTableCards = MonteCarlo.NB_BOARDCARDS - nbBoardCards;
        final int nbCardsToDraw = nbOpponents * 2 + nbMissingTableCards;
        
        final ArrayList<Integer> deck = new ArrayList<Integer>();
        
        for (int i = 0; i < MonteCarlo.MAX_DECK_SIZE; ++i)
        {
            deck.add(i);
        }
        
        for (int i = 0; i != nbBoardCards; ++i)
        {
            deck.remove((Object) p_tableCards[i].getId());
        }
        
        deck.remove((Object) p_playerCards[0].getId());
        deck.remove((Object) p_playerCards[1].getId());
        
        Collections.shuffle(deck);
        
        final int[] playerCards = new int[2];
        playerCards[0] = p_playerCards[0].getId();
        playerCards[1] = p_playerCards[1].getId();
        
        final int[] dynamicTable = new int[MonteCarlo.NB_BOARDCARDS];
        
        for (int i = 0; i != nbBoardCards; ++i)
        {
            dynamicTable[i] = p_tableCards[i].getId();
        }
        
        final ArrayList<ParallelMonteCarlo> m_threads = new ArrayList<ParallelMonteCarlo>();
        
        for (int i = 1; i != MonteCarlo.NB_THREADS; ++i)
        {
            m_threads.add(new ParallelMonteCarlo(p_nbIterations / MonteCarlo.NB_THREADS, nbMissingTableCards, nbBoardCards, nbCardsToDraw, deck.toArray(new Integer[deck.size()]), playerCards, dynamicTable));
        }
        
        // Start all threads.
        for (final ParallelMonteCarlo thread : m_threads)
        {
            thread.start();
        }
        
        final ParallelMonteCarlo mainThread = new ParallelMonteCarlo(p_nbIterations / MonteCarlo.NB_THREADS, nbMissingTableCards, nbBoardCards, nbCardsToDraw, deck.toArray(new Integer[deck.size()]), playerCards, dynamicTable);
        mainThread.run();
        
        // Wait for all threads to be finished.
        for (final ParallelMonteCarlo thread : m_threads)
        {
            try
            {
                thread.join();
            }
            catch (final InterruptedException e)
            {
            }
        }
        
        long nbGamesWon = mainThread.getNbGamesWon();
        for (final ParallelMonteCarlo thread : m_threads)
        {
            nbGamesWon += thread.getNbGamesWon();
        }
        
        return nbGamesWon;
    }
    
    /**
     * Calculate the ratio win/loss. Utility function to have the variance
     * 
     * @param p_playerCards
     *            Cards of the player
     * @param p_tableCards
     *            Cards on the board
     * @param p_nbPlayers
     *            Number of players
     * @param p_nbIterations
     *            Number of iteration
     * @return The winratio and the StandardDerivation in StatsInfo
     */
    public static StatsInfos CalculateWinRatio(Card[] p_playerCards, Card[] p_tableCards, int p_nbPlayers, long p_nbIterations)
    {
        final StatsInfos result = new StatsInfos();
        if ((p_playerCards[0].getId() < 0) || (p_playerCards[1].getId() < 0))
        {
            return result;
        }
        
        int nbBoardCards = 0;
        while ((nbBoardCards != p_tableCards.length) && (p_tableCards[nbBoardCards].getId() >= 0))
        {
            nbBoardCards++;
        }
        
        if (nbBoardCards <= 0)
        {
            result.m_winRatio = MonteCarlo.CalculateNbWins(p_playerCards, p_tableCards, p_nbPlayers, p_nbIterations) / ((double) p_nbIterations);
            result.m_standardDeviation = 1;
            return result;
        }
        
        if (nbBoardCards >= 5)
        {
            result.m_winRatio = MonteCarlo.CalculateNbWins(p_playerCards, p_tableCards, p_nbPlayers, p_nbIterations) / ((double) p_nbIterations);
            result.m_standardDeviation = 0;
            return result;
        }
        
        final int nbOpponents = p_nbPlayers - 1;
        final int nbMissingTableCards = MonteCarlo.NB_BOARDCARDS - nbBoardCards;
        final int nbCardsToDraw = nbOpponents * 2 + nbMissingTableCards;
        
        final ArrayList<Integer> deck = new ArrayList<Integer>();
        
        for (int i = 0; i < MonteCarlo.MAX_DECK_SIZE; ++i)
        {
            deck.add(i);
        }
        
        for (int i = 0; i != nbBoardCards; ++i)
        {
            deck.remove((Object) p_tableCards[i].getId());
        }
        
        deck.remove((Object) p_playerCards[0].getId());
        deck.remove((Object) p_playerCards[1].getId());
        
        Collections.shuffle(deck);
        
        final int[] playerCards = new int[2];
        playerCards[0] = p_playerCards[0].getId();
        playerCards[1] = p_playerCards[1].getId();
        
        final int[] dynamicTable = new int[MonteCarlo.NB_BOARDCARDS];
        
        for (int i = 0; i != nbBoardCards; ++i)
        {
            dynamicTable[i] = p_tableCards[i].getId();
        }
        
        final ArrayList<ParallelMonteCarloWithVariance> m_threads = new ArrayList<ParallelMonteCarloWithVariance>();
        
        for (int i = 0; i != MonteCarlo.NB_THREADS; ++i)
        {
            m_threads.add(new ParallelMonteCarloWithVariance(p_nbIterations / MonteCarlo.NB_THREADS, nbMissingTableCards, nbBoardCards, nbCardsToDraw, deck.toArray(new Integer[deck.size()]), playerCards, dynamicTable));
        }
        
        // Start all threads.
        for (final ParallelMonteCarloWithVariance thread : m_threads)
        {
            thread.start();
        }
        
        // Wait for all threads to be finished.
        for (final ParallelMonteCarloWithVariance thread : m_threads)
        {
            try
            {
                thread.join();
            }
            catch (final InterruptedException e)
            {
            }
        }
        
        final long[] m_gameWonPerCard = new long[52];
        final long[] m_nbSimulationPerCard = new long[52];
        long nbGamesWon = 0;
        
        for (final ParallelMonteCarloWithVariance thread : m_threads)
        {
            for (int i = 0; i < 52; i++)
            {
                m_gameWonPerCard[i] += thread.m_gameWonPerCard[i];
                m_nbSimulationPerCard[i] += thread.m_nbSimulationPerCard[i];
                
            }
        }
        
        for (int i = 0; i < 52; i++)
        {
            nbGamesWon += m_gameWonPerCard[i];
        }
        result.m_winRatio = nbGamesWon / ((double) p_nbIterations);
        
        int nbCardUsed = 0;
        for (int i = 0; i < 52; i++)
        {
            if (m_nbSimulationPerCard[i] > 0)
            {
                ++nbCardUsed;
                result.m_standardDeviation += Math.pow((m_gameWonPerCard[i] / ((double) m_nbSimulationPerCard[i])) - result.m_winRatio, 2);
            }
        }
        
        result.m_standardDeviation = Math.sqrt(result.m_standardDeviation) / nbCardUsed;
        
        return result;
    }
    
    /**
     * Encode the hand for steve brecher
     * 
     * @param hand
     *            Hand of the player
     * @param code
     *            Current code
     * @return New code
     */
    public static long Encode(int[] hand, long code)
    {
        for (int i = 0; i < hand.length; ++i)
        {
            code |= 0x1L << hand[i];
        }
        return code;
        
    }
    
    /**
     * Main for testing purpose
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        final Card[] playerCards = new Card[2];
        playerCards[0] = Card.DK;
        playerCards[1] = Card.SQ;
        
        final Card[] tableCards = new Card[] { Card.getInstance().get(Card.NO_CARD), Card.getInstance().get(Card.NO_CARD), Card.getInstance().get(Card.NO_CARD), Card.getInstance().get(Card.NO_CARD), Card.getInstance().get(Card.NO_CARD) };
        
        final long startTime = System.currentTimeMillis();
        
        final StatsInfos result = MonteCarlo.CalculateWinRatio(playerCards, tableCards, 2, MonteCarlo.NB_SIMULATION);
        
        final long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in execution is :" + (double) (endTime - startTime) / 1000);
        
        System.out.println("Mean = " + result.m_winRatio);
        System.out.println("Écart-type = " + result.m_standardDeviation);
    }
    
    public MonteCarlo()
    {
    }
    
}
