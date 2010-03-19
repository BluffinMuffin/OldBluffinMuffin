package poker.game;

import poker.game.observer.PokerGameObserver;

public interface IPokerGame
{
    PokerGameObserver getGameObserver();
    
    TableInfo getPokerTable();
    
    boolean playMoney(PlayerInfo player, int amount);
    
    boolean leaveGame(PlayerInfo player);
    
}
