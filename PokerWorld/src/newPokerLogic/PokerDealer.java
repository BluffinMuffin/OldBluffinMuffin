package newPokerLogic;


public class PokerDealer
{
    private GameCardSet m_Deck;
    private final PokerGame m_Game;
    
    public PokerDealer(PokerGame game)
    {
        m_Game = game;
    }
    
    public void initialize()
    {
        m_Deck = GameCardSet.shuffledDeck();
    }
    
    public void DealHoles()
    {
        
    }
    
    public void DealFlop()
    {
        final GameCard c1 = m_Deck.pop();
        final GameCard c2 = m_Deck.pop();
        final GameCard c3 = m_Deck.pop();
    }
    
    public void DealTurn()
    {
        final GameCard c = m_Deck.pop();
    }
    
    public void DealRiver()
    {
        final GameCard c = m_Deck.pop();
    }
    
    public PokerGame getGame()
    {
        return m_Game;
    }
}
