package pokerStats;

import newPokerLogic.PokerHandEvaluator;
import utility.XORShiftRandom;


/**
 * @author Hocus
 *         This class calculate MonteCarlo using multiple thread to increase the speed of calculation.
 */
public class ParallelMonteCarlo extends Thread
{
    long m_nbSimulations;
    int m_nbMissingTableCards;
    int m_nbCardsShowedOnTable;
    int m_nbCardsToDraw;
    Integer[] m_deck;
    int[] m_playerCards;
    int[] m_dynamicTable;
    long m_gamesWon;
    
    XORShiftRandom m_myRandom = new XORShiftRandom();
    
    /**
     * Constructor
     * 
     * @param p_nbSimulations
     *            Number of simulation
     * @param p_nbMissingTableCards
     *            Number of cards missing on the board
     * @param p_nbCardsShowedOnTable
     *            Number of cards showed on the board
     * @param p_nbCardsToDraw
     *            Number of cards left to draw
     * @param p_deck
     *            Deck of cards
     * @param p_playerCards
     *            Number of players
     * @param p_dynamicTable
     *            Table
     */
    public ParallelMonteCarlo(long p_nbSimulations, int p_nbMissingTableCards, int p_nbCardsShowedOnTable, int p_nbCardsToDraw, Integer[] p_deck, int[] p_playerCards, int[] p_dynamicTable)
    {
        this.setName("MonteCarlo - [" + Thread.currentThread().getName() + "]");
        m_nbSimulations = p_nbSimulations;
        m_gamesWon = 0;
        m_nbMissingTableCards = p_nbMissingTableCards;
        m_nbCardsShowedOnTable = p_nbCardsShowedOnTable;
        m_nbCardsToDraw = p_nbCardsToDraw;
        m_deck = p_deck;
        m_playerCards = p_playerCards;
        m_dynamicTable = p_dynamicTable;
    }
    
    /**
     * Returns the number of game won
     * 
     * @return Number of game won
     */
    public long getNbGamesWon()
    {
        return m_gamesWon;
    }
    
    /**
     * Execute MonteCarlo
     */
    @Override
    public void run()
    {
        final int[] opponentCards = new int[MonteCarlo.NB_HOLECARDS];
        
        for (int i = 0; i < m_nbSimulations; ++i)
        {
            for (int j = 0; j < m_nbMissingTableCards; ++j)
            {
                m_dynamicTable[m_nbCardsShowedOnTable + j] = m_deck[j];
            }
            
            final long boardCode = (MonteCarlo.Encode(m_dynamicTable, 0));
            final long myResult = PokerHandEvaluator.hand7Eval(MonteCarlo.Encode(m_playerCards, boardCode));
            
            boolean lost = false;
            for (int j = m_nbMissingTableCards; j < m_nbCardsToDraw; j += 2)
            {
                opponentCards[0] = m_deck[j];
                opponentCards[1] = m_deck[j + 1];
                final int opponentResult = PokerHandEvaluator.hand7Eval(MonteCarlo.Encode(opponentCards, boardCode));
                
                if (opponentResult > myResult)
                {
                    lost = true;
                    break;
                }
            }
            
            if (!lost)
            {
                m_gamesWon++;
            }
            
            for (int k = 0; k != m_nbCardsToDraw; ++k)
            {
                final int l = m_myRandom.nextInt(m_deck.length - k) + k;
                final int tmp = m_deck[l];
                m_deck[l] = m_deck[k];
                m_deck[k] = tmp;
            }
        }
    }
}