package pokerClientAI;

import pokerGameLogic.IPokerGame;
import pokerGameLogic.PokerPlayerInfo;
import pokerGameLogic.PokerTableInfo;
import utility.Hasard;

public class PokerAIRandom extends AbstractPokerAI
{
    public PokerAIRandom(IPokerGame game, int seatViewed)
    {
        super(game, seatViewed);
    }
    
    @Override
    protected int playMoney()
    {
        int bet = 0;
        final PokerTableInfo table = m_game.getPokerTable();
        final PokerPlayerInfo p = table.getPlayer(m_currentTablePosition);
        final boolean canRaise = table.getCurrentHigherBet() < (p.getCurrentBetMoneyAmount() + p.getCurrentSafeMoneyAmount());
        final boolean canCheck = table.getCurrentHigherBet() == p.getCurrentBetMoneyAmount();
        
        final int rndChx = Hasard.RandomMinMax(1, 3);
        if (rndChx == 2 || (canCheck && rndChx == 1) || (!canRaise && rndChx == 3))
        {
            // CHECK/CALL
            bet = table.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
            System.out.println(p.getPlayerName() + " randomly CHECK/CALL with " + bet);
        }
        else if (rndChx == 3)
        {
            // RAISE
            final int maxRaise = (p.getCurrentBetMoneyAmount() + p.getCurrentSafeMoneyAmount()) - table.getCurrentHigherBet();
            final int minRaise = Math.min((table.getCurrentHigherBet() + table.getBigBlindAmount()) - p.getCurrentBetMoneyAmount(), maxRaise);
            bet = minRaise;
            
            final int choice = Hasard.randomWithMax(100);
            final int m = maxRaise - minRaise;
            
            if (choice < 50)
            {
            }
            else if (choice < 90)
            {
                bet += Hasard.randomWithMax(m / (5 + Hasard.randomWithMax(10)));
            }
            else
            {
                bet += Hasard.randomWithMax(m / Hasard.RandomMinMax(1, 5));
            }
            System.out.println(p.getPlayerName() + " randomly RAISE with " + bet);
        }
        else
        {
            // FOLD
            bet = -1;
            System.out.println(p.getPlayerName() + " randomly FOLD with " + bet);
        }
        return bet;
    }
    
    @Override
    public String toString()
    {
        return "Poker Random AI";
    }
}
