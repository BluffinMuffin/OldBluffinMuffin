package pokerLogic;

import java.util.Iterator;
import java.util.List;



public class HandEvalHoldem {

    
    /**
     * Calculate the hand value of a seven cards hand
     * 
     * @param p_cards
     *            The seven cards to evaluate
     * @return
     *         The hand strength
     */
    public static int get7CardsHandValue(List<Card> p_cards)
    {
        final pokerLogic.HandEvalCardSet cardSet = new pokerLogic.HandEvalCardSet(7);
        
        Card card;
        final Iterator<Card> it = p_cards.iterator();
        while (it.hasNext())
        {
            card = it.next();
            cardSet.add(new pokerLogic.HandEvalCard(card.getCode()));
        }
        
        return pokerLogic.HandEvaluator.hand7Eval(pokerLogic.HandEvaluator.encode(cardSet));
    }
}
