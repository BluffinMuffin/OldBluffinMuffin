package clientAI;

import gameLogic.GameCard;

import java.util.ArrayList;

import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldTypePlayerAction;
import pokerLogic.OldTypePokerRound;
import pokerStats.MonteCarlo;

@Deprecated
public class OldPokerGeneticAI extends OldPokerAI
{
    
    private final static int NB_SIMULATIONS = 10000;
    
    private final double[] alpha = new double[2];
    private final double[] beta = new double[2];
    private final double[] sigma = new double[2];
    private final double[] phi = new double[2];
    
    public OldPokerGeneticAI()
    {
        this(new double[] { 0.09796216575881056, 0.05873844040051912, 0.11932433543686727, 0.6461736739523037, 0.48315192075952695, 0.2903068543987788, 0.8896891202927723, 0.7165465583082452 });
    }
    
    public OldPokerGeneticAI(double[] p_adn)
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
    protected OldPokerPlayerAction analyze(ArrayList<OldTypePlayerAction> pActionsAllowed, int pMinRaiseAmount, int pMaxRaiseAmount)
    {
        
        OldPokerPlayerAction action = null;
        final double mc = calculateHandValues();
        
        int gameState = 1;
        if (m_table.m_gameState == OldTypePokerRound.PREFLOP)
        {
            gameState = 0;
        }
        
        final double rnd = (Math.random() - 0.5);
        int bet = (int) (phi[gameState] * ((mc - alpha[gameState]) + (beta[gameState]) * rnd) * (m_table.m_localPlayer.getMoney() - m_table.m_localPlayer.m_betAmount));
        
        bet -= m_table.m_localPlayer.m_betAmount;
        
        if (bet < 0)
        {
            // Check - Fold
            if (pActionsAllowed.contains(OldTypePlayerAction.CALL))
            {
                action = new OldPokerPlayerAction(OldTypePlayerAction.FOLD, 0);
            }
            else
            {
                action = new OldPokerPlayerAction(OldTypePlayerAction.CHECK, 0);
            }
        }
        else if ((m_callAmount > (1 - sigma[gameState]) * bet) && (m_callAmount < (1 + sigma[gameState]) * bet))
        {
            // Check - Call
            if (pActionsAllowed.contains(OldTypePlayerAction.CALL))
            {
                action = new OldPokerPlayerAction(OldTypePlayerAction.CALL, m_callAmount);
            }
            else
            {
                action = new OldPokerPlayerAction(OldTypePlayerAction.CHECK, 0);
            }
        }
        else
        {
            // Raise
            if (pActionsAllowed.contains(OldTypePlayerAction.RAISE))
            {
                if (bet < pMinRaiseAmount)
                {
                    action = new OldPokerPlayerAction(OldTypePlayerAction.RAISE, pMinRaiseAmount);
                }
                else if (bet > pMaxRaiseAmount)
                {
                    action = new OldPokerPlayerAction(OldTypePlayerAction.RAISE, pMaxRaiseAmount);
                }
                else
                {
                    action = new OldPokerPlayerAction(OldTypePlayerAction.RAISE, bet);
                }
            }
            else if (pActionsAllowed.contains(OldTypePlayerAction.CALL))
            {
                action = new OldPokerPlayerAction(OldTypePlayerAction.CALL, m_callAmount);
            }
        }
        
        System.out.println(action.getType() + " of " + action.getAmount() + " ... " + bet + "(" + alpha[0] + ", " + alpha[1] + ", " + beta[0] + ", " + beta[1] + ", " + sigma[0] + ", " + sigma[1] + ", " + phi[0] + ", " + phi[1] + ")");
        
        return action;
    }
    
    private double calculateHandValues()
    {
        final GameCard[] myCards = m_table.m_localPlayer.getHand();
        final GameCard[] myBoardCards = m_table.m_boardCards.toArray(new GameCard[m_table.m_boardCards.size()]);
        
        final double score = MonteCarlo.CalculateWinRatio(myCards, myBoardCards, m_table.m_nbRemainingPlayers, OldPokerGeneticAI.NB_SIMULATIONS).m_winRatio;
        
        return score;
    }
    
}