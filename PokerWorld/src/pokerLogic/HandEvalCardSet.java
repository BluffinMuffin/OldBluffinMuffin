package pokerLogic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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
public class HandEvalCardSet implements Set<HandEvalCard>
{
    
    private final ArrayList<HandEvalCard> cards;
    
    private static final HandEvalCardSet madeDeck = new HandEvalCardSet(52);
    static
    {
        for (final HandEvalCard.Suit suit : HandEvalCard.Suit.values())
        {
            for (final HandEvalCard.Rank rank : HandEvalCard.Rank.values())
            {
                HandEvalCardSet.madeDeck.add(new HandEvalCard(rank, suit));
            }
        }
    }
    
    /**
     * Return an ordered 52-card deck.
     * 
     * @return a 52-card deck in order from clubs to spades and within each suit from deuce to Ace.
     */
    public static HandEvalCardSet freshDeck()
    {
        return new HandEvalCardSet(HandEvalCardSet.madeDeck);
    }
    
    /**
     * Return a shuffled 52-card deck.
     * 
     * @return a shuffled 52-card deck.
     */
    public static HandEvalCardSet shuffledDeck()
    {
        final HandEvalCardSet result = new HandEvalCardSet(HandEvalCardSet.madeDeck);
        Collections.shuffle(result.cards);
        return result;
    }
    
    public HandEvalCardSet()
    {
        cards = new ArrayList<HandEvalCard>();
    }
    
    /**
     * Copy constructor
     */
    public HandEvalCardSet(HandEvalCardSet source)
    {
        cards = new ArrayList<HandEvalCard>(source.cards);
    }
    
    public HandEvalCardSet(int initialCapacity)
    {
        cards = new ArrayList<HandEvalCard>(initialCapacity);
    }
    
    /**
     * Returns <code>true</code> if this CardSet did not already contain the specified Card.
     * 
     * @return <code>true</code> if this CardSet did not already contain the specified Card.
     */
    public boolean add(HandEvalCard c)
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
    public boolean addAll(Collection<? extends HandEvalCard> coll)
    {
        boolean result = false;
        for (final HandEvalCard c : coll)
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
        for (final HandEvalCard c : cards)
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
        for (final HandEvalCard c : cards)
        {
            result += c.hashCode();
        }
        return result;
    }
    
    public boolean isEmpty()
    {
        return cards.isEmpty();
    }
    
    public Iterator<HandEvalCard> iterator()
    {
        return cards.iterator();
    }
    
    public boolean remove(Object o)
    {
        return cards.remove(o);
    }
    
    public HandEvalCard pop()
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
    
    public Object[] toArray()
    {
        return cards.toArray(new HandEvalCard[cards.size()]);
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
