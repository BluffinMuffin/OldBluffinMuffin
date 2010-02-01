package tools;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import utility.Card;
import backend.IHoldEmObserver;

/**
 * @author HOCUS
 *         Utilities for the Hold'Em Poker simulateur
 */
public class PokerUtil
{
    
    /**
     * Find a method of the IHoldEmObserver class
     * 
     * @param p_name
     *            The name of the method
     * @param p_classes
     *            The classes of the parameters
     * @return
     *         The method requested
     */
    public static Method getIHoldEmObserverMethod(String p_name, Class<?>... p_classes)
    {
        try
        {
            return IHoldEmObserver.class.getMethod(p_name, p_classes);
        }
        catch (final SecurityException e)
        {
            e.printStackTrace();
        }
        catch (final NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Calculate the hand value of a seven cards hand
     * 
     * @param p_cards
     *            The seven cards to evaluate
     * @return
     *         The hand strength
     */
    public static int getSteveBercherHandValue(List<Card> p_cards)
    {
        final com.stevebrecher.poker.CardSet cardSet = new com.stevebrecher.poker.CardSet(7);
        
        Card card;
        final Iterator<Card> it = p_cards.iterator();
        while (it.hasNext())
        {
            card = it.next();
            cardSet.add(new com.stevebrecher.poker.Card(card.getCode()));
        }
        
        return com.stevebrecher.poker.HandEval.hand7Eval(com.stevebrecher.poker.HandEval.encode(cardSet));
    }
}
