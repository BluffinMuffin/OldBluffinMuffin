package utility;

import java.util.Collections;
import java.util.Stack;

/**
 * 
 * This class is the representation of a deck of playing cards
 * 
 * @author Hokus
 * 
 */
public class Deck
{
    
    /**
     * Shuffle the cards
     * 
     * @param p_cards
     *            Cards to be shuffled
     */
    public static void shuffleCards(Stack<Card> p_cards)
    {
        Collections.shuffle(p_cards);
    }
    
    /**
     * The stack of cards
     */
    private Stack<Card> m_cards;
    
    /**
     * The type of the deck
     */
    private TypeDeck m_type;
    
    /**
     * Default Deck constructor. Make a shuffled card deck without Joker
     */
    public Deck()
    {
        this(TypeDeck.NO_JOKER);
    }
    
    /**
     * Deck constructor. Make a deck with the specified cards of the specified type.
     * Does not shuffle the Cards.
     * 
     * @param p_cards
     * @param p_type
     */
    public Deck(Stack<Card> p_cards, TypeDeck p_type)
    {
        setType(p_type);
        setCards(p_cards);
    }
    
    /**
     * Deck constructor. Make a shuffled card deck of the specified type
     * 
     * @param p_type
     *            Type of the Deck
     */
    public Deck(TypeDeck p_type)
    {
        m_cards = new Stack<Card>();
        m_type = p_type;
        
        int nbCards = 54;
        if (p_type == TypeDeck.NO_JOKER)
        {
            nbCards = 52;
        }
        
        for (int i = 0; i < nbCards; i++)
        {
            m_cards.push(Card.getInstance().get(i));
        }
        
        Deck.shuffleCards(m_cards);
    }
    
    /**
     * Check if the deck is empty
     * 
     * @return
     *         true if there is no more cards
     */
    public boolean empty()
    {
        return m_cards.empty();
    }
    
    /**
     * Get the cards
     * 
     * @return
     *         The cards of the deck
     */
    public Stack<Card> getCards()
    {
        return m_cards;
    }
    
    /**
     * Get the type of Deck
     * 
     * @return
     *         Type of the Deck
     */
    public TypeDeck getType()
    {
        return m_type;
    }
    
    /**
     * Get the number of cards in the Deck
     * 
     * @return
     *         The number of cards in the Deck
     */
    public int numberOfCards()
    {
        return m_cards.size();
    }
    
    /**
     * Get the card on the top of the Deck
     * 
     * @return
     *         The first Card of the Deck
     */
    public Card pop()
    {
        return m_cards.pop();
    }
    
    /**
     * Add a card on the top of the Deck
     * 
     * @param p_card
     *            The card to add
     */
    public void push(Card p_card)
    {
        m_cards.push(p_card);
    }
    
    /**
     * Set the cards of the deck. Does not shuffle the cards.
     * 
     * @param p_cards
     *            The stack of cards
     */
    public void setCards(Stack<Card> p_cards)
    {
        m_cards = p_cards;
    }
    
    /**
     * Set the type of Deck
     * 
     * @param p_type
     *            Type of the Deck
     */
    public void setType(TypeDeck p_type)
    {
        m_type = p_type;
    }
    
}
