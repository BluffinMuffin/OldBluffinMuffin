package poker.game;

import poker.game.observer.IPokerGameListener;

public interface IPokerGame
{
    
    void attach(IPokerGameListener listener);
    
    void detach(IPokerGameListener listener);
    
    TableInfo getTable();
    
    boolean playMoney(PlayerInfo player, int amount);
    
    boolean leaveGame(PlayerInfo player);
    
}
