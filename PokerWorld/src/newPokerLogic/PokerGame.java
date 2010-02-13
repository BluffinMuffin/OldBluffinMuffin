package newPokerLogic;

public class PokerGame implements Runnable
{
    /**
     * Start the table.
     * This method creates a new thread.
     */
    public void start()
    {
        new Thread(this).start();
    }
    
    @Override
    public void run()
    {
        initializeTable();
        while (initializeGame())
        {
            placeBlinds();
            dealHoles();
            if (startBettingRound())
            {
                dealFlop();
                // m_info.dealFlop();
                // m_pokerObserver.flopDealt(this, m_info.getBoard());
                if (startBettingRound())
                {
                    dealTurn();
                    // m_info.dealTurn();
                    // m_pokerObserver.turnDeal(this, m_info.getBoard());
                    if (startBettingRound())
                    {
                        dealRiver();
                        // m_info.dealRiver();
                        // m_pokerObserver.riverDeal(this, m_info.getBoard());
                        if (startBettingRound())
                        {
                            showdown();
                        }
                    }
                }
            }
            endGame();
        }
    }
    
    private void endGame()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void showdown()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void dealRiver()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void dealTurn()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void dealFlop()
    {
        // TODO Auto-generated method stub
        
    }
    
    private boolean startBettingRound()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    private void dealHoles()
    {
        // TODO Auto-generated method stub
        
    }
    
    private void placeBlinds()
    {
        // TODO Auto-generated method stub
        
    }
    
    private boolean initializeGame()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    private void initializeTable()
    {
        // TODO Auto-generated method stub
        
    }
}
