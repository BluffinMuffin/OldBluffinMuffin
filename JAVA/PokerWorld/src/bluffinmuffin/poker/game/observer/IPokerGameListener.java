package bluffinmuffin.poker.game.observer;

import java.util.EventListener;

import bluffinmuffin.poker.game.MoneyPot;
import bluffinmuffin.poker.game.PlayerInfo;
import bluffinmuffin.poker.game.TypeAction;
import bluffinmuffin.poker.game.TypeRound;


public interface IPokerGameListener extends EventListener
{
    
    void everythingEnded();
    
    //
    // GAME ////////////////////////////////////////////////
    void gameBettingRoundStarted();
    
    void gameBlindsNeeded();
    
    void gameEnded();
    
    void gameBettingRoundEnded(TypeRound r);
    
    void gameGenerallyUpdated();
    
    //
    // PLAYER ////////////////////////////////////////////////
    void playerJoined(PlayerInfo p);
    
    void playerLeaved(PlayerInfo p);
    
    void playerActionTaken(PlayerInfo p, TypeAction reason, int playedAmount);
    
    void playerMoneyChanged(PlayerInfo p);
    
    void playerActionNeeded(PlayerInfo p, PlayerInfo lastPlayer);
    
    void playerHoleCardsChanged(PlayerInfo p);
    
    void playerWonPot(PlayerInfo p, MoneyPot pot, int wonAmount);
}
