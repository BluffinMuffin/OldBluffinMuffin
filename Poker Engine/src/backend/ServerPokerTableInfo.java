package backend;

import java.util.Stack;

import miscUtil.Constants;
import basePoker.Deck;
import basePoker.PokerTableInfo;
import basePoker.Pot;
import basePoker.TypePokerGame;
import basePoker.TypePokerRound;

public class ServerPokerTableInfo extends PokerTableInfo
{
    // Players variables
    public int m_nbFolded;
    public int m_nbAllIn;
    public int m_nbBetting;
    
    // Game variables
    public Deck m_deck;
    public int m_noSeatLastRaiser;
    public int m_playerTurn;
    public int m_bettingPlayer;
    public boolean m_firstTurn;
    public Stack<Pot> m_pots = new Stack<Pot>();
    public TypePokerGame m_gameType;
    
    public ServerPokerTableInfo()
    {
        super();
    }
    
    public ServerPokerTableInfo(int nbSeats)
    {
        super(nbSeats);
    }
    
    /**
     * Check if we can continue betting
     * 
     * @return
     *         True if we can continue betting
     */
    public boolean continueBetting()
    {
        boolean canContinue = false;
        if ((m_firstTurn && (m_nbBetting > 1)) || (getPlayer(m_playerTurn).getBet() < m_currentBet))
        {
            canContinue = true;
        }
        
        return canContinue;
    }
    
    /**
     * Deal the flop cards
     */
    public void dealFlop()
    {
        m_gameState = TypePokerRound.FLOP;
        for (int i = 0; i < 3; ++i)
        {
            m_boardCards.add(m_deck.pop());
        }
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Deal the River cards
     */
    public void dealRiver()
    {
        m_gameState = TypePokerRound.RIVER;
        m_boardCards.add(m_deck.pop());
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Deal the turn cards
     */
    public void dealTurn()
    {
        m_gameState = TypePokerRound.TURN;
        m_boardCards.add(m_deck.pop());
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
