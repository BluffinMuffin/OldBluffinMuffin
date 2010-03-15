package pokerClientAI;

import gameLogic.GameCard;
import pokerGameLogic.IPokerGame;
import pokerGameLogic.PokerPlayerInfo;
import pokerGameLogic.PokerTableInfo;
import pokerGameStats.MonteCarlo;

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
public class PokerAIBasic extends AbstractPokerAI
{
    public PokerAIBasic(IPokerGame game, int seatViewed)
    {
        super(game, seatViewed);
    }
    
    private final static int NB_SIMULATIONS = 10000;
    
    @Override
    protected int playMoney()
    {
        final PokerTableInfo table = m_game.getPokerTable();
        final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
        final GameCard[] myCards = p.getCurrentHand(true);
        final GameCard[] myBoardCards = new GameCard[5];
        table.getCurrentBoardCards().toArray(myBoardCards);
        
        System.out.println("Calculating MonteCarlo score...");
        
        // Verifying that we agent two cards in our hand.
        if ((myCards[0] == null) || (myCards[1] == null))
        {
            System.err.println("Watch IT!!! Cannot take decision if I don't hava any cards.");
            return super.playMoney();
        }
        
        // Calculate Monte Carlo score�
        final double score = MonteCarlo.CalculateWinRatio(myCards, myBoardCards, table.getNbPlaying(), PokerAIBasic.NB_SIMULATIONS).m_winRatio;
        
        System.out.println("Analyzing " + score);
        final int x = table.getNbPlaying();
        // Hardcoded values depending on the number of remaining players in
        // play.
        final double threshold1 = Math.sqrt(3.0 / (5.0 * (x - 3.0 / 4.0))) + 0.02;
        final double threshold2 = Math.sqrt(4.0 / ((x + 3.0))) - 4.0 / 10.0 + 0.02;
        
        final boolean canRaise = table.getCurrentHigherBet() >= (p.getCurrentSafeMoneyAmount() + p.getCurrentBetMoneyAmount());
        final boolean canCheck = table.getCurrentHigherBet() == p.getCurrentBetMoneyAmount();
        
        if ((score >= threshold1) && canRaise)
        {
            System.out.println("RAISE");
            final int restant = (p.getCurrentSafeMoneyAmount() + p.getCurrentBetMoneyAmount()) - table.getCurrentHigherBet();
            return Math.min((table.getCurrentHigherBet() + table.getBigBlindAmount()) - p.getCurrentBetMoneyAmount(), restant);
        }
        else if ((score >= threshold2))
        {
            System.out.println("CALL");
            return table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
        }
        else
        {
            if (canCheck)
            {
                System.out.println("CHECK");
                return 0;
            }
            else
            {
                System.out.println("FOLD");
                return -1;
            }
        }
    }
    
    @Override
    public String toString()
    {
        return "Poker Basic AI";
    }
}
