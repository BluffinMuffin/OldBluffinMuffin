package newPokerLogicTools;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.TypePokerGameRound;
import newPokerLogic.TypePokerGameRoundState;
import newPokerLogic.TypePokerGameState;
import utility.EventObserver;

public class PokerGameObserver extends EventObserver<PokerGameListener>
{
    
    public void bigBlindPosted(PokerPlayerInfo p, int bbAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.bigBlindPosted(p, bbAmount);
        }
    }
    
    public void gameStateChanged(TypePokerGameState oldState, TypePokerGameState newState)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.gameStateChanged(oldState, newState);
        }
    }
    
    public void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerCalled(p, playedAmount, totalCallValue);
        }
    }
    
    public void playerFolded(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerFolded(p);
        }
    }
    
    public void playerJoined(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerJoined(p);
        }
    }
    
    public void playerLeaved(PokerPlayerInfo p)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerLeaved(p);
        }
    }
    
    public void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.playerRaised(p, playedAmount, totalRaiseValue);
        }
    }
    
    public void roundChanged(TypePokerGameState gameState, TypePokerGameRound oldRound, TypePokerGameRound newRound)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.roundChanged(gameState, oldRound, newRound);
        }
    }
    
    public void roundStateChanged(TypePokerGameState gameState, TypePokerGameRound round, TypePokerGameRoundState oldRoundState, TypePokerGameRoundState newRoundState)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.roundStateChanged(gameState, round, oldRoundState, newRoundState);
        }
    }
    
    public void smallBlindPosted(PokerPlayerInfo p, int sbAmount)
    {
        for (final PokerGameListener listener : getSubscribers())
        {
            listener.smallBlindPosted(p, sbAmount);
        }
    }
}
