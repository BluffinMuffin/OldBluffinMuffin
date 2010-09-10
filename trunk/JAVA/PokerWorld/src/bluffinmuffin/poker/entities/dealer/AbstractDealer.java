package bluffinmuffin.poker.entities.dealer;

import bluffinmuffin.game.entities.Card;
import bluffinmuffin.game.entities.CardSet;
import bluffinmuffin.poker.entities.PlayerInfo;

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
