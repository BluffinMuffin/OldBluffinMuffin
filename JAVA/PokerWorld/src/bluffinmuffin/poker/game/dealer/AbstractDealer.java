package bluffinmuffin.poker.game.dealer;

import bluffinmuffin.game.Card;
import bluffinmuffin.game.CardSet;
import bluffinmuffin.poker.game.PlayerInfo;

public abstract class AbstractDealer
{
    protected CardSet m_deck;
    
    public AbstractDealer()
    {
        freshDeck();
    }
    
    public abstract CardSet dealHoles(PlayerInfo p);
    
    public abstract CardSet dealFlop();
    
    public abstract Card dealTurn();
    
    public abstract Card dealRiver();
    
    public abstract void freshDeck();
}
