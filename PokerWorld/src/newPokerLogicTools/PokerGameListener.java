package newPokerLogicTools;

import java.util.EventListener;

import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;

public interface PokerGameListener extends EventListener
{
    //
    // GAME ////////////////////////////////////////////////
    void boardCardsChanged(PokerTableInfo t);
    
    void blindsNeeded(PokerPlayerInfo sb, PokerPlayerInfo bb, int sbValue, int bbValue);
    
    void end();
    
    //
    // BLINDS ////////////////////////////////////////////////
    
    //
    // PLAYER ////////////////////////////////////////////////
    void playerJoined(PokerPlayerInfo p);
    
    void playerLeaved(PokerPlayerInfo p);
    
    void playerFolded(PokerPlayerInfo p);
    
    void playerCalled(PokerPlayerInfo p, int playedAmount, int totalCallValue);
    
    void playerRaised(PokerPlayerInfo p, int playedAmount, int totalRaiseValue);
    
    void playerMoneyChanged(PokerPlayerInfo p);
    
    void actionNeeded(PokerPlayerInfo p, int amountToCall, int maxRaise);
    
    void bigBlindPosted(PokerPlayerInfo p, int bbAmount);
    
    void smallBlindPosted(PokerPlayerInfo p, int sbAmount);
}
