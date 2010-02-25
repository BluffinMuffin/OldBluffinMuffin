package newPokerLogic;

import newPokerLogicTools.PokerGameObserver;

public interface IPokerGame
{
    PokerGameObserver getGameObserver();
    
    boolean playMoney(PokerPlayerInfo player, int amount);
    
    boolean leaveGame(PokerPlayerInfo player);
    
}
