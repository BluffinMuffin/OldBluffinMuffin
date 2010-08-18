package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Original class from Steve Brecher, version 2006Dec11.0
 * Adapted by Bluffin Muffin
 * 
 * A set of distinct {@link HandEvalCard}s.
 * <p>
 * This implementation is a wrapper on {@link ArrayList}<{@link HandEvalCard}> that allows no duplicates. A CardSet's iterator will provide elements in FIFO order -- the order in which the elements were added -- if the instance's shuffle method has not been invoked.
 * <p>
 * It also provides a 52-card deck.
 * <p>
 * Methods not otherwise documented forward to {@link ArrayList}<{@link HandEvalCard}> or perform as specified by the {@link Set} interface.
 * 
 */
public class CardSet implements Set<Card>
{
    
    private final List<Card> cards;
    
    private static final CardSet madeDeck = new CardSet(52);
    static
    {
        for (final TypeCardSuit suit : TypeCardSuit.values())
        {
            for (final TypeCardRank rank : TypeCardRank.values())
            {
                CardSet.madeDeck.add(Card.getInstance(rank, suit));
            }
        }
    }
    
    /**
     * Return an ordered 52-card deck.
     * 
     * @return a 52-card deck in order from clubs to spades and within each suit from deuce to Ace.
     */
    public static CardSet freshDeck()
    {
        return new CardSet(CardSet.madeDeck);
    }
    
    /**
     * Return a shuffled 52-card deck.
     * 
     * @return a shuffled 52-card deck.
     */
    public static CardSet shuffledDeck()
    {
        final CardSet result = new CardSet(CardSet.madeDeck);
        Collections.shuffle(result.cards);
        return result;
    }
    
    public CardSet()
    {
        cards = new ArrayList<Card>();
    }
    
    /**
     * Copy constructor
     */
    public CardSet(CardSet source)
    {
        cards = new ArrayList<Card>(source.cards);
    }
    
    public CardSet(List<Card> source)
    {
        cards = source;
    }
    
    public CardSet(int initialCapacity)
    {
        cards = new ArrayList<Card>(initialCapacity);
    }
    
    /**
     * Returns <code>true</code> if this CardSet did not already contain the specified Card.
     * 
     * @return <code>true</code> if this CardSet did not already contain the specified Card.
     */
    public boolean add(Card c)
    {
        if (cards.contains(c))
        {
            return false;
        }
        return cards.add(c);
    }
    
    /**
     * Returns <code>true</code> if this CardSet changed as a result of the call.
     * 
     * @return <code>true</code> if this CardSet changed as a result of the call; <code>false</code> if all of the Cards in the specified Collection were already present in this CardSet.
     */
    public boolean addAll(Collection<? extends Card> coll)
    {
        boolean result = false;
        for (final Card c : coll)
        {
            result |= add(c);
        }
        return result;
    }
    
    public void clear()
    {
        cards.clear();
    }
    
    public boolean contains(Object o)
    {
        return cards.contains(o);
    }
    
    public Card get(int i)
    {
        if (i < 0 || i >= cards.size())
        {
            return null;
        }
        return cards.get(i);
    }
    
    public boolean containsAll(Collection<?> coll)
    {
        return cards.containsAll(coll);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object that)
    {
        if (!(that instanceof Set) || (((Set) that).size() != cards.size()))
        {
            return false;
        }
        for (final Card c : cards)
        {
            if (!((Set) that).contains(c))
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode()
    {
        int result = 0;
        for (final Card c : cards)
        {
            result += c.hashCode();
        }
        return result;
    }
    
    public boolean isEmpty()
    {
        return cards.isEmpty();
    }
    
    public Iterator<Card> iterator()
    {
        return cards.iterator();
    }
    
    public boolean remove(Object o)
    {
        return cards.remove(o);
    }
    
    public Card pop()
    {
        return cards.remove(0);
    }
    
    public boolean removeAll(Collection<?> coll)
    {
        return cards.removeAll(coll);
    }
    
    public boolean retainAll(Collection<?> coll)
    {
        return cards.retainAll(coll);
    }
    
    public void shuffle()
    {
        Collections.shuffle(cards);
    }
    
    public int size()
    {
        return cards.size();
    }
    
    public Card[] toArray()
    {
        return cards.toArray(new Card[cards.size()]);
    }
    
    public <T> T[] toArray(T[] a)
    {
        return cards.toArray(a);
    }
    
    /**
     * Returns a {@link String} containing a comma-space-separated list of cards.
     * 
     * @return a {@link String} containing a comma-space-separated list of cards,
     *         each the result of {@link HandEvalCard#toString()}.
     */
    @Override
    public String toString()
    {
        return cards.toString();
    }
}
