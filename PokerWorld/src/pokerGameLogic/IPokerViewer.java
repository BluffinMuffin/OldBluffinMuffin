package pokerGameLogic;

import pokerGameTools.PokerGameObserver;

public interface IPokerViewer
{
    void setPokerObserver(PokerGameObserver o);
    
    void setGame(IPokerGame c, int s);
    
    void start();
}
