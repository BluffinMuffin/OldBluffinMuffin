package newPokerLogic;

import gameLogic.GameCard;
import gameLogic.GameCardSet;

public abstract class AbstractPokerDealer
{
    protected GameCardSet m_deck;
    
    public AbstractPokerDealer()
    {
        m_deck = GameCardSet.freshDeck();
    }
    
    public abstract GameCardSet dealHoles(PokerPlayerInfo p);
    
    public abstract GameCardSet dealFlop();
    
    public abstract GameCard dealTurn();
    
    public abstract GameCard dealRiver();
}
