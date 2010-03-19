package poker;

import poker.game.IPokerGame;
import poker.game.observer.PokerGameObserver;

public interface IPokerViewer
{
    void setPokerObserver(PokerGameObserver o);
    
    void setGame(IPokerGame c, int s);
    
    void start();
}
