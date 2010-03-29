using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PokerWorld.Game
{
    //TODO!
    public class PokerGame : IPokerGame
    {
        // Global States of the Game
        public enum TypeState
        {
            Init,
            WaitForPlayers,
            WaitForBlinds,
            Playing,
            Showdown,
            DecideWinners,
            DistributeMoney,
            End
        }

        // States of the Game in each Round
        public enum TypeRoundState
        {
            Cards,
            Betting,
            Cumul
        }

        public event EventHandler EverythingEnded = delegate { };
        public event EventHandler GameBlindNeeded = delegate { };
        public event EventHandler GameEnded = delegate { };
        public event EventHandler GameGenerallyUpdated = delegate { };
        public event EventHandler<RoundEventArgs> GameBettingRoundStarted = delegate { };
        public event EventHandler<RoundEventArgs> GameBettingRoundEnded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerJoined = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerLeaved = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerActionNeeded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged = delegate { };
        public event EventHandler<PlayerActionEventArgs> PlayerActionTaken = delegate { };
        public event EventHandler<PotWonEventArgs> PlayerWonPot = delegate { };

        private readonly TableInfo m_Table; // La table
        private readonly AbstractDealer m_Dealer; // Dealer
    
        // WAITING TIME
        private readonly int m_WaitingTimeAfterPlayerAction; // Attente apres chaque player action (ms)
        private readonly int m_WaitingTimeAfterBoardDealed; // Attente apres chaque board dealed (ms)
        private readonly int m_WaitingTimeAfterPotWon; // Attente apres chaque pot won ! (ms)

        // STATES
        private TypeState m_State; // L'etat global de la game
        private TypeRoundState m_RoundState; // L'etat de la game pour chaque round

        public TableInfo Table
        {
            get { return m_Table; }
        }

        public PokerGame()
            : this(new RandomDealer())
        {
        }
        public PokerGame(AbstractDealer dealer):
            this(new RandomDealer(), new TableInfo(), 0, 0, 0)
        {
        }

        public PokerGame(TableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
            : this(new RandomDealer(), table, wtaPlayerAction, wtaBoardDealed, wtaPotWon)
        {
        }

        public PokerGame(AbstractDealer dealer, TableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
        {
            m_Dealer = dealer;
            m_Table = table;
            m_State = TypeState.Init;
            m_RoundState = TypeRoundState.Cards;
            m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
            m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
            m_WaitingTimeAfterPotWon = wtaPotWon;
        }

        public bool PlayMoney(PlayerInfo p, int amnt)
        {
            //TODO!
            throw new NotImplementedException();
        }

        public bool LeaveGame(PlayerInfo p)
        {
            //TODO!
            throw new NotImplementedException();
        }
    }
}
