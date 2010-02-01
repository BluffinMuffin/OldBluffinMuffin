package backend.agent;

import java.util.ArrayList;

import stats.MonteCarlo;
import utility.Card;
import utility.TypeGameState;
import utility.TypePlayerAction;
import backend.PlayerAction;

@Deprecated
public class PokerGeneticAI extends PokerAI
{
    
    private final static int NB_SIMULATIONS = 10000;
    
    private final double[] alpha = new double[2];
    private final double[] beta = new double[2];
    private final double[] sigma = new double[2];
    private final double[] phi = new double[2];
    
    public PokerGeneticAI()
    {
        this(new double[] { 0.09796216575881056, 0.05873844040051912, 0.11932433543686727, 0.6461736739523037, 0.48315192075952695, 0.2903068543987788, 0.8896891202927723, 0.7165465583082452 });
    }
    
    public PokerGeneticAI(double[] p_adn)
    {
        alpha[0] = p_adn[0];
        alpha[1] = p_adn[1];
        beta[0] = p_adn[2];
        beta[1] = p_adn[3];
        sigma[0] = p_adn[4];
        sigma[1] = p_adn[5];
        phi[0] = p_adn[6];
        phi[1] = p_adn[7];
    }
    
    @Override
    protected PlayerAction analyze(ArrayList<TypePlayerAction> pActionsAllowed, int pMinRaiseAmount, int pMaxRaiseAmount)
    {
        
        PlayerAction action = null;
        final double mc = calculateHandValues();
        
        int gameState = 1;
        if (m_table.m_gameState == TypeGameState.PREFLOP)
        {
            gameState = 0;
        }
        
        final double rnd = (Math.random() - 0.5);
        int bet = (int) (phi[gameState] * ((mc - alpha[gameState]) + (beta[gameState]) * rnd) * (m_table.m_localPlayer.m_money - m_table.m_localPlayer.m_betAmount));
        
        bet -= m_table.m_localPlayer.m_betAmount;
        
        if (bet < 0)
        {
            // Check - Fold
            if (pActionsAllowed.contains(TypePlayerAction.CALL))
            {
                action = new PlayerAction(TypePlayerAction.FOLD, 0);
            }
            else
            {
                action = new PlayerAction(TypePlayerAction.CHECK, 0);
            }
        }
        else if ((m_callAmount > (1 - sigma[gameState]) * bet) && (m_callAmount < (1 + sigma[gameState]) * bet))
        {
            // Check - Call
            if (pActionsAllowed.contains(TypePlayerAction.CALL))
            {
                action = new PlayerAction(TypePlayerAction.CALL, m_callAmount);
            }
            else
            {
                action = new PlayerAction(TypePlayerAction.CHECK, 0);
            }
        }
        else
        {
            // Raise
            if (pActionsAllowed.contains(TypePlayerAction.RAISE))
            {
                if (bet < pMinRaiseAmount)
                {
                    action = new PlayerAction(TypePlayerAction.RAISE, pMinRaiseAmount);
                }
                else if (bet > pMaxRaiseAmount)
                {
                    action = new PlayerAction(TypePlayerAction.RAISE, pMaxRaiseAmount);
                }
                else
                {
                    action = new PlayerAction(TypePlayerAction.RAISE, bet);
                }
            }
            else if (pActionsAllowed.contains(TypePlayerAction.CALL))
            {
                action = new PlayerAction(TypePlayerAction.CALL, m_callAmount);
            }
        }
        
        System.out.println(action.m_typeAction + " of " + action.m_actionAmount + " ... " + bet + "(" + alpha[0] + ", " + alpha[1] + ", " + beta[0] + ", " + beta[1] + ", " + sigma[0] + ", " + sigma[1] + ", " + phi[0] + ", " + phi[1] + ")");
        
        return action;
    }
    
    private double calculateHandValues()
    {
        final Card[] myCards = new Card[] { m_table.m_localPlayer.m_card1, m_table.m_localPlayer.m_card2 };
        final Card[] myBoardCards = m_table.m_boardCards.toArray(new Card[m_table.m_boardCards.size()]);
        
        final double score = MonteCarlo.CalculateWinRatio(myCards, myBoardCards, m_table.m_nbRemainingPlayers, PokerGeneticAI.NB_SIMULATIONS).m_winRatio;
        
        return score;
    }
    
}
