package newPokerLogic;

import newPokerLogicTools.PokerGameObserver;

public class PokerGame
{
    private final PokerGameObserver m_gameObserver;
    private final PokerTableInfo m_pokerTable;
    
    private int m_currentDealerNoSeat;
    private int m_currentSmallBlindNoSeat;
    private int m_currentBigBlindNoSeat;
    private TypePokerGameState m_currentGameState;
    private TypePokerGameRound m_currentGameRound;
    private TypePokerGameRoundState m_currentGameRoundState;
    private int m_currentPlayerNoSeat;
    
    public PokerGame()
    {
        m_gameObserver = new PokerGameObserver();
        m_pokerTable = new PokerTableInfo();
        m_currentGameState = TypePokerGameState.INIT;
        m_currentDealerNoSeat = -1;
        m_currentSmallBlindNoSeat = -1;
        m_currentBigBlindNoSeat = -1;
    }
    
    public int getCurrentDealerNoSeat()
    {
        return m_currentDealerNoSeat;
    }
    
    public void setCurrentDealerNoSeat(int currentDealerNoSeat)
    {
        m_currentDealerNoSeat = currentDealerNoSeat;
    }
    
    public int getCurrentSmallBlindNoSeat()
    {
        return m_currentSmallBlindNoSeat;
    }
    
    public void setCurrentSmallBlindNoSeat(int currentSmallBlindNoSeat)
    {
        m_currentSmallBlindNoSeat = currentSmallBlindNoSeat;
    }
    
    public int getCurrentBigBlindNoSeat()
    {
        return m_currentBigBlindNoSeat;
    }
    
    public void setCurrentBigBlindNoSeat(int currentBigBlindNoSeat)
    {
        m_currentBigBlindNoSeat = currentBigBlindNoSeat;
    }
    
    public TypePokerGameState getCurrentGameState()
    {
        return m_currentGameState;
    }
    
    public void setCurrentGameState(TypePokerGameState currentGameState)
    {
        m_currentGameState = currentGameState;
    }
    
    public TypePokerGameRound getCurrentGameRound()
    {
        return m_currentGameRound;
    }
    
    public void setCurrentGameRound(TypePokerGameRound currentGameRound)
    {
        m_currentGameRound = currentGameRound;
    }
    
    public TypePokerGameRoundState getCurrentGameRoundState()
    {
        return m_currentGameRoundState;
    }
    
    public void setCurrentGameRoundState(TypePokerGameRoundState currentGameRoundState)
    {
        m_currentGameRoundState = currentGameRoundState;
    }
    
    public int getCurrentPlayerNoSeat()
    {
        return m_currentPlayerNoSeat;
    }
    
    public void setCurrentPlayerNoSeat(int currentPlayerNoSeat)
    {
        m_currentPlayerNoSeat = currentPlayerNoSeat;
    }

    public PokerGameObserver getGameObserver()
    {
        return m_gameObserver;
    }

    public PokerTableInfo getPokerTable()
    {
        return m_pokerTable;
    }
}
