package bluffinmuffin.poker;

import bluffinmuffin.poker.game.IPokerGame;

public interface IPokerViewer
{
    
    void setGame(IPokerGame c, int s);
    
    void start();
}
