package newPokerClientAI;

import gameLogic.GameCard;
import newPokerLogic.IPokerGame;
import newPokerLogic.PokerPlayerInfo;
import newPokerLogic.PokerTableInfo;
import newPokerLogic.TypePokerGameRound;
import pokerStats.MonteCarlo;

public class PokerAIGenetic extends AbstractPokerAI
{
    
    private final static int NB_SIMULATIONS = 10000;
    
    private final double[] alpha = new double[2];
    private final double[] beta = new double[2];
    private final double[] sigma = new double[2];
    private final double[] phi = new double[2];
    
    public PokerAIGenetic(IPokerGame game, int seatViewed)
    {
        this(game, seatViewed, new double[] { 0.09796216575881056, 0.05873844040051912, 0.11932433543686727, 0.6461736739523037, 0.48315192075952695, 0.2903068543987788, 0.8896891202927723, 0.7165465583082452 });
    }
    
    public PokerAIGenetic(IPokerGame game, int seatViewed, double[] p_adn)
    {
        super(game, seatViewed);
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
    protected int playMoney()
    {
        final PokerTableInfo table = m_game.getPokerTable();
        final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
        final boolean canRaise = table.getCurrentHigherBet() >= (p.getCurrentSafeMoneyAmount() + p.getCurrentBetMoneyAmount());
        final boolean canCheck = table.getCurrentHigherBet() == p.getCurrentBetMoneyAmount();
        int finalBet;
        final double mc = calculateHandValues(p, table);
        
        int gameState = 1;
        if (table.getCurrentGameRound() == TypePokerGameRound.PREFLOP)
        {
            gameState = 0;
        }
        
        final double rnd = (Math.random() - 0.5);
        int bet = (int) (phi[gameState] * ((mc - alpha[gameState]) + (beta[gameState]) * rnd) * (p.getCurrentSafeMoneyAmount() - p.getCurrentBetMoneyAmount()));
        
        bet -= p.getCurrentBetMoneyAmount();
        
        if (bet < 0)
        {
            // Check - Fold
            if (canCheck)
            {
                finalBet = table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
            }
            else
            {
                finalBet = -1;
            }
        }
        else if ((table.getCurrentHigherBet() > (1 - sigma[gameState]) * bet) && (table.getCurrentHigherBet() < (1 + sigma[gameState]) * bet))
        {
            finalBet = table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
        }
        else
        {
            // Raise
            if (canRaise)
            {
                final int maxRaise = (p.getCurrentSafeMoneyAmount() + p.getCurrentBetMoneyAmount()) - table.getCurrentHigherBet();
                final int minRaise = Math.min((table.getCurrentHigherBet() + table.getBigBlindAmount()) - p.getCurrentBetMoneyAmount(), maxRaise);
                if (bet < minRaise)
                {
                    finalBet = minRaise;
                }
                else if (bet > maxRaise)
                {
                    finalBet = maxRaise;
                }
                else
                {
                    finalBet = bet;
                }
            }
            else
            {
                finalBet = table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
            }
        }
        
        System.out.println("Play money of " + finalBet + " ... " + bet + "(" + alpha[0] + ", " + alpha[1] + ", " + beta[0] + ", " + beta[1] + ", " + sigma[0] + ", " + sigma[1] + ", " + phi[0] + ", " + phi[1] + ")");
        
        return finalBet;
    }
    
    private double calculateHandValues(PokerPlayerInfo p, PokerTableInfo table)
    {
        final GameCard[] myCards = p.getCurrentHand(true);
        final GameCard[] myBoardCards = new GameCard[5];
        table.getCurrentBoardCards().toArray(myBoardCards);
        
        final double score = MonteCarlo.CalculateWinRatio(myCards, myBoardCards, table.getNbPlaying(), PokerAIGenetic.NB_SIMULATIONS).m_winRatio;
        
        return score;
    }
    
}
