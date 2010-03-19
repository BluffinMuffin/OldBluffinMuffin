package poker;

import game.GameCard;
import game.GameCardSet;

public class RandomPokerDealer extends AbstractPokerDealer
{
    public RandomPokerDealer()
    {
        super();
    }
    
    @Override
    public GameCardSet dealFlop()
    {
        final GameCardSet set = new GameCardSet(3);
        set.add(m_deck.pop());
        set.add(m_deck.pop());
        set.add(m_deck.pop());
        return set;
    }
    
    @Override
    public GameCardSet dealHoles(PokerPlayerInfo p)
    {
        final GameCardSet set = new GameCardSet(2);
        set.add(m_deck.pop());
        set.add(m_deck.pop());
        return set;
    }
    
    @Override
    public GameCard dealRiver()
    {
        return m_deck.pop();
    }
    
    @Override
    public GameCard dealTurn()
    {
        return m_deck.pop();
    }
    
    @Override
    public void freshDeck()
    {
        m_deck = GameCardSet.shuffledDeck();
    }
    
}
