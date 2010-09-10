package bluffinmuffin.poker.observer;

import java.util.EventListener;

import bluffinmuffin.poker.entities.PotInfo;
import bluffinmuffin.poker.entities.PlayerInfo;
import bluffinmuffin.poker.entities.type.PlayerActionType;
import bluffinmuffin.poker.entities.type.GameRoundType;


public interface IPokerGameListener extends EventListener
{
    
    void everythingEnded();
    
    //
    // GAME ////////////////////////////////////////////////
    void gameBettingRoundStarted();
    
    void gameBlindsNeeded();
    
    void gameEnded();
    
    void gameBettingRoundEnded(GameRoundType r);
    
    void gameGenerallyUpdated();
    
    //
    // PLAYER ////////////////////////////////////////////////
    void playerJoined(PlayerInfo p);
    
    void playerLeaved(PlayerInfo p);
    
    void playerActionTaken(PlayerInfo p, PlayerActionType reason, int playedAmount);
    
    void playerMoneyChanged(PlayerInfo p);
    
    void playerActionNeeded(PlayerInfo p, PlayerInfo lastPlayer);
    
    void playerHoleCardsChanged(PlayerInfo p);
    
    void playerWonPot(PlayerInfo p, PotInfo pot, int wonAmount);
}
