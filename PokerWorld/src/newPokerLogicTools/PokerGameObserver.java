package newPokerLogicTools;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import utility.EventObserver;

public class PokerGameObserver extends EventObserver<PokerGameListener> implements PokerGameListener
{
    
    @Override
    public void bigBlindPosted(PokerPlayerInfo p, int bbAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.bigBlindPosted(p, bbAmount);
        }
    }
    
    @Override
    public void blindsNeeded(PokerPlayerInfo sb, PokerPlayerInfo bb, int sbValue, int bbValue)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.blindsNeeded(sb, bb, sbValue, bbValue);
        }
    }
    
    @Override
    public void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerCalled(p, playedAmount, totalCallValue);
        }
    }
    
    @Override
    public void playerFolded(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerFolded(p);
        }
    }
    
    @Override
    public void playerJoined(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerJoined(p);
        }
    }
    
    @Override
    public void playerLeaved(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerLeaved(p);
        }
    }
    
    @Override
    public void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerRaised(p, playedAmount, totalRaiseValue);
        }
    }
    
    @Override
    public void smallBlindPosted(PokerPlayerInfo p, int sbAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.smallBlindPosted(p, sbAmount);
        }
    }
    
    @Override
    public void actionNeeded(PokerPlayerInfo p, int amountToCall, int maxRaise)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.actionNeeded(p, amountToCall, maxRaise);
        }
    }
    
    @Override
    public void boardCardsChanged(PokerTableInfo t)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.boardCardsChanged(t);
        }
    }
    
    @Override
    public void end()
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.end();
        }
    }
    
    @Override
    public void playerMoneyChanged(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerMoneyChanged(p);
        }
    }
}
