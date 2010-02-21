package newPokerLogicTools;

import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;
import utility.EventObserver;

public class PokerGameObserver extends EventObserver<PokerGameListener> implements PokerGameListener
{
    @Override
    public void gameBlindsNeeded(PokerTableInfo t)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameBlindsNeeded(t);
        }
    }
    
    @Override
    public void playerJoined(PokerTableInfo t, PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerJoined(t, p);
        }
    }
    
    @Override
    public void playerLeaved(PokerTableInfo t, PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerLeaved(t, p);
        }
    }
    
    @Override
    public void playerActionNeeded(PokerTableInfo t, PokerPlayerInfo p)
    {
        // TODO: on me call jamais :(
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerActionNeeded(t, p);
        }
    }
    
    @Override
    public void gameBoardCardsChanged(PokerTableInfo t)
    {
        // TODO: on me call jamais :(
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameBoardCardsChanged(t);
        }
    }
    
    @Override
    public void everythingEnded(PokerTableInfo t)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.everythingEnded(t);
        }
    }
    
    @Override
    public void playerMoneyChanged(PokerTableInfo t, PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerMoneyChanged(t, p);
        }
    }
    
    @Override
    public void playerActionTaken(PokerTableInfo t, PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerActionTaken(t, p, reason, playedAmount);
        }
    }
    
    @Override
    public void gameEnded(PokerTableInfo t)
    {
        // TODO: on me call jamais :(
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameEnded(t);
        }
    }
    
    @Override
    public void playerWonPot(PokerTableInfo t, PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount)
    {
        // TODO: on me call jamais :(
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerWonPot(t, null, null, 0);
        }
    }
    
    @Override
    public void gameBettingRoundEnded(PokerTableInfo t, TypePokerGameRound r)
    {
        // TODO: on me call jamais :(
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameBettingRoundEnded(t, r);
        }
    }
    
    @Override
    public void playerHoleCardsChanged(PokerTableInfo t, PokerPlayerInfo p)
    {
        // TODO: on me call jamais :(
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerHoleCardsChanged(t, p);
        }
    }
}
