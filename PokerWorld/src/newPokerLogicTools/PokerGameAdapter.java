package newPokerLogicTools;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.TypePokerGameRound;
import newPokerLogic.TypePokerGameRoundState;
import newPokerLogic.TypePokerGameState;

public class PokerGameAdapter implements PokerGameListener
{
    
    @Override
    public void bigBlindPosted(PokerPlayerInfo p, int bbAmount)
    {
    }
    
    @Override
    public void blindsNeeded(PokerPlayerInfo sb, PokerPlayerInfo bb, int sbValue, int bbValue)
    {
    }
    
    @Override
    public void gameStateChanged(TypePokerGameState oldState, TypePokerGameState newState)
    {
    }
    
    @Override
    public void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue)
    {
    }
    
    @Override
    public void playerFolded(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerJoined(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerLeaved(PokerPlayerInfo p)
    {
    }
    
    @Override
    public void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue)
    {
    }
    
    @Override
    public void roundChanged(TypePokerGameState gameState, TypePokerGameRound oldRound, TypePokerGameRound newRound)
    {
    }
    
    @Override
    public void roundStateChanged(TypePokerGameState gameState, TypePokerGameRound round, TypePokerGameRoundState oldRoundState, TypePokerGameRoundState newRoundState)
    {
    }
    
    @Override
    public void smallBlindPosted(PokerPlayerInfo p, int sbAmount)
    {
    }
    
}
