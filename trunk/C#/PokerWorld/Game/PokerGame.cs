using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using EricUtility;
using PokerWorld.Game.Dealer;
using PokerWorld.Game.Enums;
using PokerWorld.Game.PokerEventArgs;

namespace PokerWorld.Game
{
    /// <summary>
    /// This represent only one "Game" of Poker. This means from Putting Blinds to preflop, flop, turn, river and then declaring the winner.
    /// Tipically you would have many "Game" while sitting at a table, but players can sit-in and sit-out, so it's like a different game every "Game".
    /// </summary>
    public abstract class PokerGame : IPokerGame
    {
        #region Fields
        protected readonly TableInfo m_Table; // La table
        protected readonly AbstractDealer m_Dealer; // Dealer

        // WAITING TIME
        protected readonly int m_WaitingTimeAfterPlayerAction; // Attente apres chaque player action (ms)
        protected readonly int m_WaitingTimeAfterBoardDealed; // Attente apres chaque board dealed (ms)
        protected readonly int m_WaitingTimeAfterPotWon; // Attente apres chaque pot won ! (ms)

        // STATES
        protected GameStateEnum m_State; // L'etat global de la game
        protected RoundStateEnum m_RoundState; // L'etat de la game pour chaque round
        #endregion Fields

        #region Events
        public event EventHandler EverythingEnded = delegate { };
        public event EventHandler GameBlindNeeded = delegate { };
        public event EventHandler GameEnded = delegate { };
        public event EventHandler GameGenerallyUpdated = delegate { };
        public event EventHandler<RoundEventArgs> GameBettingRoundStarted = delegate { };
        public event EventHandler<RoundEventArgs> GameBettingRoundEnded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerJoined = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerLeaved = delegate { };
        public event EventHandler<HistoricPlayerInfoEventArgs> PlayerActionNeeded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged = delegate { };
        public event EventHandler<PlayerActionEventArgs> PlayerActionTaken = delegate { };
        public event EventHandler<PotWonEventArgs> PlayerWonPot = delegate { };
        #endregion

        #region Properties
        /// <summary>
        /// The Table Entity
        /// </summary>
        public TableInfo Table
        {
            get { return m_Table; }
        }

        /// <summary>
        /// Current Round of the Playing State
        /// </summary>
        public RoundEnum Round
        {
            get { return m_Table.Round; }
        }

        /// <summary>
        /// Is the Game currently Running ? (Not Ended)
        /// </summary>
        public bool IsRunning
        {
            get { return m_State != GameStateEnum.End; }
        }

        /// <summary>
        /// Current State of the Game
        /// </summary>
        public GameStateEnum State
        {
            get { return m_State; }
        }

        /// <summary>
        /// Every position is an information:
        /// 0 : Real money (1)
        /// 1 : Texas Hold'em (0)
        /// 2 : Ring game (0)
        /// 3 : NoLimit (0)
        /// 4 : GameRound (0,1,2,3)
        /// </summary>
        public string Encode { get { return string.Format("{0}{1}{2}{3}{4}", 1, 0, 0, 0, (int)m_Table.Round); } }

        #endregion

        #region Ctors & Init
        public PokerGame()
            : this(new RandomDealer())
        {
        }
        public PokerGame(AbstractDealer dealer) :
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
            m_State = GameStateEnum.Init;
            m_RoundState = RoundStateEnum.Cards;
            m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
            m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
            m_WaitingTimeAfterPotWon = wtaPotWon;
        }
        #endregion Ctors & Init

        #region Public Methods
        /// <summary>
        /// Starts the Game. Only works in Init State
        /// </summary>
        public void Start()
        {
            if( m_State == GameStateEnum.Init )
                AdvanceToNextGameState(); //Advancing to WaitForPlayers State
        }

        /// <summary>
        /// Add a player to the table
        /// </summary>
        public bool JoinGame(PlayerInfo p)
        {
            if (m_State == GameStateEnum.Init || m_State == GameStateEnum.End)
            {
                LogManager.Log(LogLevel.Error, "PokerGame.JoinGame", "Can't join, bad timing: {0}", m_State);
                return false;
            }

            return m_Table.JoinTable(p);
        }


        /// <summary>
        /// The player will play the next game
        /// </summary>
        public void SitInGame(PlayerInfo p)
        {
            PlayerJoined(this, new PlayerInfoEventArgs(p));

            if (m_State == GameStateEnum.WaitForPlayers)
                TryToBegin();
        }

        /// <summary>
        /// The player is leaving the game
        /// </summary>
        public bool LeaveGame(PlayerInfo p)
        {
            bool wasPlaying = (State == GameStateEnum.Playing && m_Table.CurrentPlayer == p);
            int blindNeeded = m_Table.GetBlindNeeded(p);

            if (m_Table.LeaveTable(p))
            {
                if (wasPlaying)
                    PlayMoney(p, -1);
                if (blindNeeded > 0)
                    PlayMoney(p, blindNeeded);
                PlayerLeaved(this, new PlayerInfoEventArgs(p));

                if (m_Table.Players.Count == 0)
                    m_State = GameStateEnum.End;

                return true;
            }
            return false;
        }

        /// <summary>
        /// The player is putting money in the game
        /// </summary>
        public bool PlayMoney(PlayerInfo p, int amount)
        {
            lock(m_Table)
            {
                int amnt = Math.Min(amount, p.MoneySafeAmnt);
                LogManager.Log(LogLevel.MessageLow, "PokerGame.PlayMoney", "{0} is playing {1} money on state: {2}", p.Name, amnt, m_State);

                if (m_State == GameStateEnum.WaitForBlinds)
                    return PlayBlinds(p, amnt);
                else if (m_State == GameStateEnum.Playing && m_RoundState == RoundStateEnum.Betting)
                    return BetMoney(p, amnt);

                LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} played money but the game is not in the right state", p.Name);

                return false;
            }
        }
        #endregion

        #region Private Methods
        private void AdvanceToNextGameState()
        {
            if (m_State == GameStateEnum.End)
                return;

            m_State = (GameStateEnum)(((int)m_State) + 1);

            switch (m_State)
            {
                case GameStateEnum.Init:
                    break;
                case GameStateEnum.WaitForPlayers:
                    TryToBegin();
                    break;
                case GameStateEnum.WaitForBlinds:
                    m_Table.HigherBet = 0;
                    break;
                case GameStateEnum.Playing:
                    m_Table.Round = RoundEnum.Preflop;
                    m_RoundState = RoundStateEnum.Cards;
                    StartRound();
                    break;
                case GameStateEnum.Showdown:
                    ShowAllCards();
                    break;
                case GameStateEnum.DecideWinners:
                    DecideWinners();
                    break;
                case GameStateEnum.DistributeMoney:
                    DistributeMoney();
                    StartANewGame();
                    break;
                case GameStateEnum.End:
                    EverythingEnded(this, new EventArgs());
                    break;
            }
        }
        private void StartANewGame()
        {
            GameEnded(this, new EventArgs());
            m_State = GameStateEnum.WaitForPlayers;
            TryToBegin();
        }
        private void AdvanceToNextRound()
        {
            if (m_State != GameStateEnum.Playing)
                return;

            if (m_Table.Round == RoundEnum.River)
                AdvanceToNextGameState(); //Advancing to Showdown State
            else
            {
                m_RoundState = RoundStateEnum.Cards;
                m_Table.NoSeatCurrPlayer = m_Table.NoSeatDealer;
                m_Table.Round = (RoundEnum)(((int)m_Table.Round) + 1);
                StartRound();
            }
        }
        private void AdvanceToNextRoundState()
        {
            if (m_State != GameStateEnum.Playing)
                return;

            if (m_RoundState == RoundStateEnum.Cumul)
                return;

            m_RoundState = (RoundStateEnum)(((int)m_RoundState) + 1);
            StartRound();
        }
        private bool BetMoney(PlayerInfo p, int amnt)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Currently, we need {0} minimum money from this player", m_Table.CallAmnt(p));

            //Validation: Is it the player's turn to play ?
            if (p.NoSeat != m_Table.NoSeatCurrPlayer)
            {
                LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} just played but it wasn't his turn", p.Name);
                return false;
            }

            //The Player is Folding
            if (amnt == -1)
            {
                LogManager.Log(LogLevel.MessageLow, "PokerGame.PlayMoney", "{0} FOLDED", p.Name);
                FoldPlayer(p);
                ContinueBettingRound();
                return true;
            }

            int amntNeeded = m_Table.CallAmnt(p);

            //Validation: Is the player betting under what he needs to Call ?
            if (amnt < amntNeeded)
            {
                //Validation: Can the player bet more ? If yes, this is not fair.
                if (p.CanBet(amnt + 1))
                {
                    LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} needed to play at least {1} and tried {2}", p.Name, amntNeeded, amnt);
                    return false;
                }

                //Else, well, that's ok, the player is All-In !
                amntNeeded = amnt;
            }

            if (amnt > amntNeeded && amnt < m_Table.MinRaiseAmnt(p))
            {
                LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} needed to play at least {1} to raise and tried {2}", p.Name, amntNeeded, amnt);
                return false;
            }

            //Let's hope the player has enough money ! Time to Bet!
            if (!p.TryBet(amnt))
            {
                LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} just put more money than he actually have ({1} > {2})", p.Name, amnt, p.MoneySafeAmnt);
                return false;
            }

            //Update the MinimumRaiseAmount
            m_Table.MinimumRaiseAmount = Math.Max(m_Table.MinimumRaiseAmount, p.MoneyBetAmnt);

            //Notify the change in the player's account
            PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));

            //Is the player All-In?
            if (p.MoneySafeAmnt == 0)
            {
                LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Player now All-In !");
                p.IsAllIn = true;
                m_Table.NbAllIn++;
                m_Table.AddAllInCap(p.MoneyBetAmnt);
            }

            //Hmmm ... More Money !! 
            m_Table.TotalPotAmnt += amnt;

            //Was it a CALL or a RAISE ?
            if (amnt == amntNeeded)
            {
                LogManager.Log(LogLevel.MessageLow, "PokerGame.PlayMoney", "{0} CALLED WITH ${1}", p.Name, amnt);
                CallPlayer(p, amnt);
            }
            else
            {
                LogManager.Log(LogLevel.MessageLow, "PokerGame.PlayMoney", "{0} RAISED WITH ${1}", p.Name, amnt);
                RaisePlayer(p, amnt);
            }

            // Ok this player received enough attention !
            ContinueBettingRound();

            return true;
        }
        private bool PlayBlinds(PlayerInfo p, int amnt)
        {
            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Total blinds needed is {0}", m_Table.TotalBlindNeeded);
            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "{0} is putting blind of {1}", p.Name, amnt);

            //What is the need Blind from the player ?
            int needed = m_Table.GetBlindNeeded(p);

            //If the player isn't giving what we expected from him
            if (amnt != needed)
            {
                //If the player isn't playing enough but it's all he got, time to go All-In
                if( amnt < needed && !p.CanBet(amnt+1))
                {
                    LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Player now All-In !");
                    p.IsAllIn = true;
                    m_Table.NbAllIn++;
                    m_Table.AddAllInCap(p.MoneyBetAmnt + amnt);
                }
                else //well, it's just not fair to play that
                {
                    LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} needed to put a blind of {1} and tried {2}", p.Name, needed, amnt);
                    return false;
                }
            }

            //Let's hope the player has enough money ! Time to put the blinds !
            if (!p.TryBet(amnt))
            {
                LogManager.Log(LogLevel.Warning, "PokerGame.PlayMoney", "{0} just put more money than he actually have ({1} > {2})", p.Name, amnt, p.MoneySafeAmnt);
                return false;
            }

            //Hmmm ... More Money !! 
            m_Table.TotalPotAmnt += amnt;

            //Notify the change in the player's account
            PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));

            //Take note of the given Blind Amount for the player.
            m_Table.SetBlindNeeded(p, 0);

            //Take note of the action
            bool isPostingSmallBlind = (needed == m_Table.SmallBlindAmnt);
            LogManager.Log(LogLevel.MessageLow, "PokerGame.PlayMoney", "{0} POSTED {1} BLIND", p.Name, isPostingSmallBlind ? "SMALL" : "BIG");
            PlayerActionTaken(this, new PlayerActionEventArgs(p, isPostingSmallBlind ? GameActionEnum.PostSmallBlind : GameActionEnum.PostBigBlind, amnt));

            //Let's set the HigherBet
            if (amnt > m_Table.HigherBet)
                m_Table.HigherBet = amnt;

            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Total blinds still needed is {0}", m_Table.TotalBlindNeeded);

            //If we got all the blinds, what are we waiting for ?!
            if (m_Table.TotalBlindNeeded == 0)
                AdvanceToNextGameState(); //Advancing to Playing State
            return true;
        }
        private void StartRound()
        {
            switch (m_RoundState)
            {
                case RoundStateEnum.Cards:
                    StartCardRound();
                    break;
                case RoundStateEnum.Betting:
                    StartBettingRound();
                    break;
                case RoundStateEnum.Cumul:
                    StartCumulRound();
                    break;
            }
        }
        private void StartCumulRound()
        {
            m_Table.ManagePotsRoundEnd();

            GameBettingRoundEnded(this, new RoundEventArgs(m_Table.Round));

            if (m_Table.NbPlayingAndAllIn <= 1)
                AdvanceToNextGameState(); //Advancing to Showdown State
            else
                AdvanceToNextRound(); //Advancing to Next Round
        }
        private void StartBettingRound()
        {
            GameBettingRoundStarted(this, new RoundEventArgs(m_Table.Round));

            m_Table.NbPlayed = 0;
            m_Table.NoSeatLastRaise = m_Table.GetPlayingPlayerNextTo(m_Table.NoSeatCurrPlayer).NoSeat;
            m_Table.MinimumRaiseAmount = m_Table.BigBlindAmnt;

            WaitALittle(m_WaitingTimeAfterBoardDealed);

            if (m_Table.NbPlaying <= 1)
                EndBettingRound();
            else
                ContinueBettingRound();
        }
        private void StartCardRound()
        {
            switch (m_Table.Round)
            {
                case RoundEnum.Preflop:
                    m_Table.NoSeatCurrPlayer = m_Table.NoSeatBigBlind;
                    DealHole();
                    break;
                case RoundEnum.Flop:
                    DealFlop();
                    break;
                case RoundEnum.Turn:
                    DealTurn();
                    break;
                case RoundEnum.River:
                    DealRiver();
                    break;
            }

            AdvanceToNextRoundState(); // Advance to Betting Round State
        }
        private void DealRiver()
        {
            m_Table.AddCards(m_Dealer.DealRiver());
        }
        private void DealTurn()
        {
            m_Table.AddCards(m_Dealer.DealTurn());
        }
        private void DealFlop()
        {
            m_Table.AddCards(m_Dealer.DealFlop());
        }
        private void DealHole()
        {
            foreach (PlayerInfo p in m_Table.PlayingAndAllInPlayers)
            {
                p.Cards = m_Dealer.DealHoles(p);
                PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p));
            }
        }
        private void ShowAllCards()
        {
            foreach (PlayerInfo p in m_Table.Players)
                if (p.IsPlaying || p.IsAllIn)
                {
                    p.IsShowingCards = true;
                    PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p));
                }
            AdvanceToNextGameState(); //Advancing to DecideWinners State
        }
        private void FoldPlayer(PlayerInfo p)
        {
            p.IsPlaying = false;

            WaitALittle(m_WaitingTimeAfterPlayerAction);

            PlayerActionTaken(this, new PlayerActionEventArgs(p, GameActionEnum.Fold, -1));
        }
        private void CallPlayer(PlayerInfo p, int played)
        {
            m_Table.NbPlayed++;

            WaitALittle(m_WaitingTimeAfterPlayerAction);

            PlayerActionTaken(this, new PlayerActionEventArgs(p, GameActionEnum.Call, played));
        }
        private void RaisePlayer(PlayerInfo p, int played)
        {
            // Since every raise "restart" the round, 
            // the number of players who played is the number of AllIn players plus the raising player
            m_Table.NbPlayed = m_Table.NbAllIn;
            if (!p.IsAllIn)
                m_Table.NbPlayed++;

            m_Table.NoSeatLastRaise = p.NoSeat;
            m_Table.HigherBet = p.MoneyBetAmnt;

            WaitALittle(m_WaitingTimeAfterPlayerAction);

            PlayerActionTaken(this, new PlayerActionEventArgs(p, GameActionEnum.Raise, played));
        }
        private void ContinueBettingRound()
        {
            if (m_Table.NbPlayingAndAllIn == 1 || m_Table.NbPlayed >= m_Table.NbPlayingAndAllIn)
                EndBettingRound();
            else
                ChooseNextPlayer();
        }
        private void EndBettingRound()
        {
            AdvanceToNextRoundState(); // Advance to Cumul Round State
        }
        private void ChooseNextPlayer()
        {
            PlayerInfo next = m_Table.GetPlayingPlayerNextTo(m_Table.NoSeatCurrPlayer);

            m_Table.NoSeatCurrPlayer = next.NoSeat;

            PlayerActionNeeded(this, new HistoricPlayerInfoEventArgs(next,m_Table.CurrentPlayer));

            if (next.IsZombie)
            {
                if (m_Table.CanCheck(next))
                    PlayMoney(next, 0);
                else
                    PlayMoney(next, -1);
            }
        }
        private void DistributeMoney()
        {
            foreach (MoneyPot pot in m_Table.Pots)
            {
                PlayerInfo[] players = pot.AttachedPlayers;
                if (players.Length > 0)
                {
                    int wonAmount = pot.Amount / players.Length;
                    if (wonAmount > 0)
                    {
                        foreach (PlayerInfo p in players)
                        {
                            p.MoneySafeAmnt += wonAmount;
                            PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));
                            PlayerWonPot(this, new PotWonEventArgs(p, pot.Id, wonAmount));
                            WaitALittle(m_WaitingTimeAfterPotWon);
                        }
                    }
                }
            }
        }
        private void DecideWinners()
        {
            m_Table.CleanPotsForWinning();
            AdvanceToNextGameState(); //Advancing to DistributeMoney State
        }
        private void WaitALittle(int waitingTime)
        {
            Thread.Sleep(waitingTime);
        }
        private void TryToBegin()
        {
            foreach (PlayerInfo p in m_Table.Players)
            {
                if (p.IsZombie)
                    LeaveGame(p);
                else if (p.CanPlay)
                {
                    p.IsPlaying = true;
                    p.IsShowingCards = false;
                }
                else
                    p.IsPlaying = false;
            }

            if (m_Table.NbPlaying >= m_Table.NbMinPlayersToStart)
            {
                m_Table.NbMinPlayersToStart = 2;
                m_Table.InitTable();
                m_Dealer.FreshDeck();
                AdvanceToNextGameState(); //Advancing to WaitForBlinds State
                GameBlindNeeded(this, new EventArgs());
            }
            else
            {
                m_Table.NoSeatDealer = -1;
                m_Table.NoSeatSmallBlind = -1;
                m_Table.NoSeatSmallBlind = -1;
            }
        }
        #endregion Private Methods
    }
}
