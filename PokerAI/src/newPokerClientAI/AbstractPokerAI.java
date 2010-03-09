package newPokerClientAI;

import newPokerLogic.IPokerGame;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogicTools.PokerGameAdapter;
import newPokerLogicTools.PokerGameObserver;

public abstract class AbstractPokerAI
{
    protected PokerGameObserver m_pokerObserver;
    protected IPokerGame m_game;
    protected int m_currentTablePosition;
    
    public AbstractPokerAI()
    {
    }
    
    public void init(IPokerGame game, int seatViewed, PokerGameObserver observer)
    {
        m_game = game;
        m_currentTablePosition = seatViewed;
        m_pokerObserver = observer;
        initializePokerObserver();
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new PokerGameAdapter()
        {
            @Override
            public void playerActionNeeded(PokerPlayerInfo p)
            {
                if (p.getCurrentTablePosition() == m_currentTablePosition)
                {
                    final int played = PlayMoney();
                    m_game.playMoney(p, played);
                }
            }
        });
    }
    
    /**
     * Analyze available informations on the poker table and
     * chose the action to do.
     * 
     * @return
     *         Money played.
     */
    protected int PlayMoney()
    {
        final PokerTableInfo table = m_game.getPokerTable();
        final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
        if (p.getCurrentBetMoneyAmount() >= table.getCurrentHigherBet())
        {
            return 0;
        }
        return -1;
    }
    
}
