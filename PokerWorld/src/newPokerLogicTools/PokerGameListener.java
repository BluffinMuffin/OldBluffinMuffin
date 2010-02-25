package newPokerLogicTools;

import java.util.EventListener;

import newPokerLogic.PokerMoneyPot;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameAction;
import newPokerLogic.TypePokerGameRound;

public interface PokerGameListener extends EventListener
{
    
    void everythingEnded(PokerTableInfo t);
    
    //
    // GAME ////////////////////////////////////////////////
    void gameBoardCardsChanged(PokerTableInfo t);
    
    void gameBlindsNeeded(PokerTableInfo t);
    
    void gameEnded(PokerTableInfo t);
    
    void gameBettingRoundEnded(PokerTableInfo t, TypePokerGameRound r);
    
    void gameGenerallyUpdated(PokerTableInfo t);
    
    //
    // PLAYER ////////////////////////////////////////////////
    void playerJoined(PokerTableInfo t, PokerPlayerInfo p);
    
    void playerLeaved(PokerTableInfo t, PokerPlayerInfo p);
    
    void playerActionTaken(PokerTableInfo t, PokerPlayerInfo p, TypePokerGameAction reason, int playedAmount);
    
    void playerMoneyChanged(PokerTableInfo t, PokerPlayerInfo p);
    
    void playerActionNeeded(PokerTableInfo t, PokerPlayerInfo p);
    
    void playerHoleCardsChanged(PokerTableInfo t, PokerPlayerInfo p);
    
    void playerWonPot(PokerTableInfo t, PokerPlayerInfo p, PokerMoneyPot pot, int wonAmount);
}
