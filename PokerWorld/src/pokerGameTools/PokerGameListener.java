package pokerGameTools;

import java.util.EventListener;

import pokerGameLogic.PokerMoneyPot;
import pokerGameLogic.PokerPlayerInfo;
import pokerGameLogic.TypePokerGameAction;
import pokerGameLogic.TypePokerGameRound;


public interface PokerGameListener extends EventListener
{
    
    void everythingEnded();
    
    //
    // GAME ////////////////////////////////////////////////
    void gameBettingRoundStarted();
    
    void gameBlindsNeeded();
    
    void gameEnded();
    
    void gameBettingRoundEnded(TypePokerGameRound r);
    
    void gameGenerallyUpdated();
    
    //
    // PLAYER ////////////////////////////////////////////////
    void playerJoined(PokerPlayerInfo p);
    
    void playerLeaved(PokerPlayerInfo p);
    
    void playerActionTaken(PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount);
    
    void playerMoneyChanged(PokerPlayerInfo p);
    
    void playerActionNeeded(PokerPlayerInfo p);
    
    void playerHoleCardsChanged(PokerPlayerInfo p);
    
    void playerWonPot(PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount);
}