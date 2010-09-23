package bluffinmuffin.poker;

import bluffinmuffin.poker.entities.TrainingTableInfo;
import bluffinmuffin.poker.entities.dealer.AbstractDealer;

public class TrainingPokerGame extends PokerGame implements IPokerGame
{
    public TrainingPokerGame()
    {
        super();
    }
    
    public TrainingPokerGame(TrainingTableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        super(table, wtaPlayerAction, wtaBoardDealed, wtaPotWon);
    }
    
    public TrainingPokerGame(AbstractDealer dealer)
    {
        super(dealer);
    }
    
    public TrainingPokerGame(AbstractDealer dealer, TrainingTableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        super(dealer, table, wtaPlayerAction, wtaBoardDealed, wtaPotWon);
    }
    
    public TrainingTableInfo getTrainingTable()
    {
        return (TrainingTableInfo) m_table;
    }
}