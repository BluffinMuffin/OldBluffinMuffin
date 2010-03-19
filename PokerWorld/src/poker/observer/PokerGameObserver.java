package poker.observer;

import poker.PokerMoneyPot;
import poker.PokerPlayerInfo;
import poker.TypePokerGameAction;
import poker.TypePokerGameRound;
import utility.EventObserver;

public class PokerGameObserver extends EventObserver<PokerGameListener> implements PokerGameListener
{
    @Override
    public void gameBlindsNeeded()
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameBlindsNeeded();
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
    public void playerActionNeeded(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerActionNeeded(p);
        }
    }
    
    @Override
    public void gameBettingRoundStarted()
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameBettingRoundStarted();
        }
    }
    
    @Override
    public void everythingEnded()
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.everythingEnded();
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
    
    @Override
    public void playerActionTaken(PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerActionTaken(p, reason, playedAmount);
        }
    }
    
    @Override
    public void gameEnded()
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameEnded();
        }
    }
    
    @Override
    public void playerWonPot(PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerWonPot(p, pot, 0);
        }
    }
    
    @Override
    public void gameBettingRoundEnded(TypePokerGameRound r)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameBettingRoundEnded(r);
        }
    }
    
    @Override
    public void playerHoleCardsChanged(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerHoleCardsChanged(p);
        }
    }
    
    @Override
    public void gameGenerallyUpdated()
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameGenerallyUpdated();
        }
    }
}
