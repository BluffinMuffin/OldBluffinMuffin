package poker;

import poker.observer.PokerGameObserver;

public interface IPokerViewer
{
    void setPokerObserver(PokerGameObserver o);
    
    void setGame(IPokerGame c, int s);
    
    void start();
}
