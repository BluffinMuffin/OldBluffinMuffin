package newPokerLogic;

import pokerLogic.Card;
import pokerLogic.Deck;

public class PokerDealer
{
    private Deck m_Deck;
    private final PokerGame m_Game;
    
    public PokerDealer(PokerGame game)
    {
        m_Game = game;
    }
    
    public void initialize()
    {
        m_Deck = new Deck(false);
    }
    
    public void DealHoles()
    {
        
    }
    
    public void DealFlop()
    {
        final Card c1 = m_Deck.pop();
        final Card c2 = m_Deck.pop();
        final Card c3 = m_Deck.pop();
    }
    
    public void DealTurn()
    {
        final Card c = m_Deck.pop();
    }
    
    public void DealRiver()
    {
        final Card c = m_Deck.pop();
    }
    
    public PokerGame getGame()
    {
        return m_Game;
    }
}
