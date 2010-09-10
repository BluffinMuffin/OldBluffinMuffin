package bluffinmuffin.poker.entities.dealer;

import bluffinmuffin.game.entities.Card;
import bluffinmuffin.game.entities.CardSet;
import bluffinmuffin.poker.entities.PlayerInfo;

public class RandomDealer extends AbstractDealer
{
    public RandomDealer()
    {
        super();
    }
    
    @Override
    public CardSet dealFlop()
    {
        final CardSet set = new CardSet(3);
        set.add(m_deck.pop());
        set.add(m_deck.pop());
        set.add(m_deck.pop());
        return set;
    }
    
    @Override
    public CardSet dealHoles(PlayerInfo p)
    {
        final CardSet set = new CardSet(2);
        set.add(m_deck.pop());
        set.add(m_deck.pop());
        return set;
    }
    
    @Override
    public Card dealRiver()
    {
        return m_deck.pop();
    }
    
    @Override
    public Card dealTurn()
    {
        return m_deck.pop();
    }
    
    @Override
    public void freshDeck()
    {
        m_deck = CardSet.shuffledDeck();
    }
    
}
