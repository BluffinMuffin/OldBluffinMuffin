using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace PokerWorld.Game
{
    public class TrainingPokerGame : PokerGame
    {
        public TrainingTableInfo TrainingTable
        {
            get { return (TrainingTableInfo)m_Table; }
        }
        public TrainingPokerGame()
            : base()
        {
        }
        public TrainingPokerGame(AbstractDealer dealer) :
            base(dealer)
        {
        }

        public TrainingPokerGame(TrainingTableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
            : base( table,  wtaPlayerAction,  wtaBoardDealed,  wtaPotWon)
        {
        }

        public TrainingPokerGame(AbstractDealer dealer, TrainingTableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
            : base(dealer,table, wtaPlayerAction, wtaBoardDealed, wtaPotWon)
        {
        }
    }
}
