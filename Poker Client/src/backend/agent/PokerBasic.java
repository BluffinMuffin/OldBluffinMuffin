package backend.agent;

import java.util.ArrayList;

import stats.MonteCarlo;
import utility.Card;
import utility.TypePlayerAction;
import backend.PlayerAction;

/**
 * @author Hocus
 *         This class represents an artificial poker player.
 *         Two hardcoded tresholds are used to make the decision.<br>
 * <br>
 *         Let say X the value return by Monte Carlo (between 0 and 1), then:<br>
 *         X < t1 => Check/Fold<br>
 *         X > t2 => Raise<br>
 *         else => Check/Call<br>
 */
public class PokerBasic extends PokerAI
{
    private final static int NB_SIMULATIONS = 10000;
    
    public PokerBasic()
    {
    }
    
    @Override
    protected PlayerAction analyze(ArrayList<TypePlayerAction> p_actionsAllowed, int p_minRaiseAmount, int p_maxRaiseAmount)
    {
        final Card[] myCards = new Card[] { m_table.m_localPlayer.m_card1, m_table.m_localPlayer.m_card2 };
        final Card[] myBoardCards = m_table.m_boardCards.toArray(new Card[m_table.m_boardCards.size()]);
        
        System.out.println("Calculating MonteCarlo score...");
        
        // Verifying that we agent two cards in our hand.
        if ((myCards[0] == null) || (myCards[1] == null))
        {
            System.err.println("Watch IT!!! Cannot take decision if I don't hava any cards.");
            return super.analyze(p_actionsAllowed, p_minRaiseAmount, p_maxRaiseAmount);
        }
        
        // Calculate Monte Carlo score�
        final double score = MonteCarlo.CalculateWinRatio(myCards, myBoardCards, m_table.m_nbRemainingPlayers, PokerBasic.NB_SIMULATIONS).m_winRatio;
        
        System.out.println("Analyzing " + score);
        final int x = m_table.m_nbRemainingPlayers;
        // Hardcoded values depending on the number of remaining players in
        // play.
        final double threshold1 = Math.sqrt(3.0 / (5.0 * (x - 3.0 / 4.0))) + 0.02;
        final double threshold2 = Math.sqrt(4.0 / ((x + 3.0))) - 4.0 / 10.0 + 0.02;
        
        if ((score >= threshold1) && p_actionsAllowed.contains(TypePlayerAction.RAISE))
        {
            return new PlayerAction(TypePlayerAction.RAISE, Math.min(p_minRaiseAmount + m_table.m_smallBlindAmount, p_maxRaiseAmount));
        }
        else if ((score >= threshold2) && p_actionsAllowed.contains(TypePlayerAction.CALL))
        {
            return new PlayerAction(TypePlayerAction.CALL);
        }
        else
        {
            if (p_actionsAllowed.contains(TypePlayerAction.CHECK))
            {
                return new PlayerAction(TypePlayerAction.CHECK);
            }
            else
            {
                return new PlayerAction(TypePlayerAction.FOLD);
            }
        }
    }
    
    @Override
    public String toString()
    {
        return "Poker Basic AI";
    }
}
