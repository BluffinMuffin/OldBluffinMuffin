package newPokerLogicTools;

import java.util.EventListener;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.TypePokerGameRound;
import newPokerLogic.TypePokerGameRoundState;
import newPokerLogic.TypePokerGameState;

public interface PokerGameListener extends EventListener
{
    void gameStateChanged(TypePokerGameState oldState, TypePokerGameState newState);
    
    void roundChanged(TypePokerGameState gameState, TypePokerGameRound oldRound, TypePokerGameRound newRound);
    
    void roundStateChanged(TypePokerGameState gameState, TypePokerGameRound round, TypePokerGameRoundState oldRoundState, TypePokerGameRoundState newRoundState);
    
    void playerJoined(PokerPlayerInfo p);
    
    void bigBlindPosted(PokerPlayerInfo p, int bbAmount);
    
    void smallBlindPosted(PokerPlayerInfo p, int sbAmount);
    
    void playerFolded(PokerPlayerInfo p);
    
    void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue);
    
    void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue);
    
}
