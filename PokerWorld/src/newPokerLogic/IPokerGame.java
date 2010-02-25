package newPokerLogic;

import newPokerLogicTools.PokerGameObserver;

public interface IPokerGame
{
    
    PokerGameObserver getGameObserver();
    
    boolean joinGame(PokerPlayerInfo player);
    
    PokerTableInfo getPokerTable();
    
    void sitInGame(PokerPlayerInfo player);
    
    boolean playMoney(PokerPlayerInfo player, int smallBlindAmount);
    
    boolean leaveGame(PokerPlayerInfo player);
    
}
