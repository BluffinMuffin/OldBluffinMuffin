package gameLogic;

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
public class GameCardSet implements Set<GameCard>
{
    
    private final List<GameCard> cards;
    
    private static final GameCardSet madeDeck = new GameCardSet(52);
    static
    {
        for (final TypeGameCardSuit suit : TypeGameCardSuit.values())
        {
            for (final TypeGameCardRank rank : TypeGameCardRank.values())
            {
                GameCardSet.madeDeck.add(GameCard.getInstance(rank, suit));
            }
        }
    }
    
    /**
     * Return an ordered 52-card deck.
     * 
     * @return a 52-card deck in order from clubs to spades and within each suit from deuce to Ace.
     */
    public static GameCardSet freshDeck()
    {
        return new GameCardSet(GameCardSet.madeDeck);
    }
    
    /**
     * Return a shuffled 52-card deck.
     * 
     * @return a shuffled 52-card deck.
     */
    public static GameCardSet shuffledDeck()
    {
        final GameCardSet result = new GameCardSet(GameCardSet.madeDeck);
        Collections.shuffle(result.cards);
        return result;
    }
    
    public GameCardSet()
    {
        cards = new ArrayList<GameCard>();
    }
    
    /**
     * Copy constructor
     */
    public GameCardSet(GameCardSet source)
    {
        cards = new ArrayList<GameCard>(source.cards);
    }
    
    public GameCardSet(List<GameCard> source)
    {
        cards = source;
    }
    
    public GameCardSet(int initialCapacity)
    {
        cards = new ArrayList<GameCard>(initialCapacity);
    }
    
    /**
     * Returns <code>true</code> if this CardSet did not already contain the specified Card.
     * 
     * @return <code>true</code> if this CardSet did not already contain the specified Card.
     */
    public boolean add(GameCard c)
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
    public boolean addAll(Collection<? extends GameCard> coll)
    {
        boolean result = false;
        for (final GameCard c : coll)
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
        for (final GameCard c : cards)
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
        for (final GameCard c : cards)
        {
            result += c.hashCode();
        }
        return result;
    }
    
    public boolean isEmpty()
    {
        return cards.isEmpty();
    }
    
    public Iterator<GameCard> iterator()
    {
        return cards.iterator();
    }
    
    public boolean remove(Object o)
    {
        return cards.remove(o);
    }
    
    public GameCard pop()
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
        return cards.toArray(new GameCard[cards.size()]);
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
