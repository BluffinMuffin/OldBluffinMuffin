package pokerGameLogic;

import pokerGameTools.PokerGameObserver;

public interface IPokerGame
{
    PokerGameObserver getGameObserver();
    
    PokerTableInfo getPokerTable();
    
    boolean playMoney(PokerPlayerInfo player, int amount);
    
    boolean leaveGame(PokerPlayerInfo player);
    
}
