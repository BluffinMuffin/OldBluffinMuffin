package handEvaluation;

import java.util.Iterator;
import java.util.List;

import pokerLogic.Card;


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
        final handEvaluation.HandEvalCardSet cardSet = new handEvaluation.HandEvalCardSet(7);
        
        Card card;
        final Iterator<Card> it = p_cards.iterator();
        while (it.hasNext())
        {
            card = it.next();
            cardSet.add(new handEvaluation.HandEvalCard(card.getCode()));
        }
        
        return handEvaluation.HandEvaluator.hand7Eval(handEvaluation.HandEvaluator.encode(cardSet));
    }
}
