package poker;

import poker.observer.PokerGameObserver;

public interface IPokerGame
{
    PokerGameObserver getGameObserver();
    
    PokerTableInfo getPokerTable();
    
    boolean playMoney(PokerPlayerInfo player, int amount);
    
    boolean leaveGame(PokerPlayerInfo player);
    
}
