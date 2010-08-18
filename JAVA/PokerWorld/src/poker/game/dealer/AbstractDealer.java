package poker.game.dealer;

import poker.game.PlayerInfo;
import game.Card;
import game.CardSet;

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
