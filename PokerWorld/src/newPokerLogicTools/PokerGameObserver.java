package newPokerLogicTools;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.TypePokerGameRound;
import newPokerLogic.TypePokerGameRoundState;
import newPokerLogic.TypePokerGameState;
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
    public void gameStateChanged(TypePokerGameState oldState, TypePokerGameState newState)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameStateChanged(oldState, newState);
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
    public void roundChanged(TypePokerGameState gameState, TypePokerGameRound oldRound, TypePokerGameRound newRound)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.roundChanged(gameState, oldRound, newRound);
        }
    }
    
    @Override
    public void roundStateChanged(TypePokerGameState gameState, TypePokerGameRound round, TypePokerGameRoundState oldRoundState, TypePokerGameRoundState newRoundState)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.roundStateChanged(gameState, round, oldRoundState, newRoundState);
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
}
