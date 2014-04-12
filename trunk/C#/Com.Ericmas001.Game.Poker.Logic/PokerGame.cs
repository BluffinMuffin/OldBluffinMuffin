using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Linq;
using Com.Ericmas001;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.Logic;
using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;
using Com.Ericmas001.Util;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Logic
{
    /// <summary>
    /// This represent only one "Game" of Poker. This means from Putting Blinds to preflop, flop, turn, river and then declaring the winner.
    /// Tipically you would have many "Game" while sitting at a table, but players can sit-in and sit-out, so it's like a different game every "Game".
    /// </summary>
    public class PokerGame : IPokerGame
    {
        #region Fields
        protected readonly AbstractDealer m_Dealer; // Dealer

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
        public event EventHandler<SeatEventArgs> SeatUpdated = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerLeaved = delegate { };
        public event EventHandler<HistoricPlayerInfoEventArgs> PlayerActionNeeded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged = delegate { };
        public event EventHandler<PlayerActionEventArgs> PlayerActionTaken = delegate { };
        public event EventHandler<PotWonEventArgs> PlayerWonPot = delegate { };

        public event IntHandler SitInResponseReceived = delegate { };
        public event BooleanHandler SitOutResponseReceived = delegate { };
        #endregion

        #region Properties
        /// <summary>
        /// The Table Entity
        /// </summary>
        public TableInfo Table { get; private set; }
        /// <summary>
        /// The PokerTable Entity
        /// </summary>
        public PokerTable GameTable { get { return (PokerTable)Table; } }

        /// <summary>
        /// The Rules Entity
        /// </summary>
        public TableParams Params { get; private set; }

        /// <summary>
        /// Current Round of the Playing State
        /// </summary>
        public RoundTypeEnum Round
        {
            get { return Table.Round; }
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
        public string Encode { get { return string.Format("{0}{1}{2}{3}{4}", 1, 0, 0, 0, (int)Table.Round); } }

        #endregion

        #region Ctors & Init
        public PokerGame()
            : this(new TexasHoldemDealer())
        {
        }
        public PokerGame(AbstractDealer dealer) :
            this(new TexasHoldemDealer(), new PokerTable())
        {
        }

        public PokerGame(PokerTable table)
            : this(new TexasHoldemDealer(), table)
        {
        }

        public PokerGame(AbstractDealer dealer, PokerTable table)
        {
            m_Dealer = dealer;
            Table = table;
            Params = table.Params;
            m_State = GameStateEnum.Init;
            m_RoundState = RoundStateEnum.Cards;
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

            PlayerJoined(this, new PlayerInfoEventArgs(p));
            return GameTable.JoinTable(p);
        }

        public int SitIn(PlayerInfo p, int noSeat = -1)
        {
            bool ok = GameTable.AskToSitIn(p, noSeat);
            if (ok)
            {
                TupleSeat seat = new TupleSeat()
                {
                    Player = p.Clone(),
                    NoSeat = p.NoSeat,
                    IsSmallBlind = Table.NoSeatSmallBlind == p.NoSeat,
                    IsEmpty = false,
                    IsDealer = Table.NoSeatDealer == p.NoSeat,
                    IsCurrentPlayer = Table.NoSeatCurrPlayer == p.NoSeat,
                    IsBigBlind = Table.NoSeatBigBlind == p.NoSeat
                };
                SeatUpdated(this, new SeatEventArgs(seat));

                if (m_State == GameStateEnum.WaitForPlayers)
                    TryToBegin();
                return p.NoSeat;
            }
            return -1;
        }

        public bool SitOut(PlayerInfo p)
        {
            int oldSeat = p.NoSeat;
            if (oldSeat == -1)
                return true;

            bool wasPlaying = (State == GameStateEnum.Playing && Table.CurrentPlayer == p);
            int blindNeeded = GameTable.GetBlindNeeded(p);

            if (Table.SitOut(p))
            {
                if (wasPlaying)
                    PlayMoney(p, -1);
                if (blindNeeded > 0)
                    PlayMoney(p, blindNeeded);
                TupleSeat seat = new TupleSeat()
                {
                    Player = null,
                    NoSeat = oldSeat,
                    IsSmallBlind = false,
                    IsEmpty = true,
                    IsDealer = false,
                    IsCurrentPlayer = false,
                    IsBigBlind = false
                };
                SeatUpdated(this, new SeatEventArgs(seat));
                return true;
            }
            return false;
        }

        /// <summary>
        /// The player is leaving the game
        /// </summary>
        public bool LeaveGame(PlayerInfo p)
        {
            bool sitOutOk = SitOut(p);

            if (sitOutOk && Table.LeaveTable(p))
            {
                PlayerLeaved(this, new PlayerInfoEventArgs(p));

                if (Table.Players.Count == 0)
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
            lock(Table)
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
                    Table.HigherBet = 0;
                    break;
                case GameStateEnum.Playing:
                    Table.Round = RoundTypeEnum.Preflop;
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

            if (Table.Round == RoundTypeEnum.River)
                AdvanceToNextGameState(); //Advancing to Showdown State
            else
            {
                m_RoundState = RoundStateEnum.Cards;
                Table.NoSeatCurrPlayer = Table.NoSeatDealer;
                Table.Round = (RoundTypeEnum)(((int)Table.Round) + 1);
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
            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Currently, we need {0} minimum money from this player", Table.CallAmnt(p));

            //Validation: Is it the player's turn to play ?
            if (p.NoSeat != Table.NoSeatCurrPlayer)
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

            int amntNeeded = Table.CallAmnt(p);

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

            if (amnt > amntNeeded && amnt < Table.MinRaiseAmnt(p)) 
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
            Table.MinimumRaiseAmount = Math.Max(Table.MinimumRaiseAmount, p.MoneyBetAmnt);

            //Notify the change in the player's account
            PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));

            //Is the player All-In?
            if (p.MoneySafeAmnt == 0)
            {
                LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Player now All-In !");
                p.State = PlayerStateEnum.AllIn;
                Table.NbAllIn++;
                GameTable.AddAllInCap(p.MoneyBetAmnt);
            }

            //Hmmm ... More Money !! 
            Table.TotalPotAmnt += amnt;

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
            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Total blinds needed is {0}", GameTable.TotalBlindNeeded);
            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "{0} is putting blind of {1}", p.Name, amnt);

            //What is the need Blind from the player ?
            int needed = GameTable.GetBlindNeeded(p);

            //If the player isn't giving what we expected from him
            if (amnt != needed)
            {
                //If the player isn't playing enough but it's all he got, time to go All-In
                if (amnt < needed && !p.CanBet(amnt + 1))
                {
                    LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Player now All-In !");
                    p.State = PlayerStateEnum.AllIn;
                    Table.NbAllIn++;
                    GameTable.AddAllInCap(p.MoneyBetAmnt + amnt);
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
            Table.TotalPotAmnt += amnt;

            //Notify the change in the player's account
            PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));

            //Take note of the given Blind Amount for the player.
            GameTable.SetBlindNeeded(p, 0);

            //Take note of the action
            bool isPostingSmallBlind = (needed == Table.SmallBlindAmnt);
            LogManager.Log(LogLevel.MessageLow, "PokerGame.PlayMoney", "{0} POSTED {1} BLIND", p.Name, isPostingSmallBlind ? "SMALL" : "BIG");
            PlayerActionTaken(this, new PlayerActionEventArgs(p, isPostingSmallBlind ? GameActionEnum.PostSmallBlind : GameActionEnum.PostBigBlind, amnt));

            //Let's set the HigherBet
            if (amnt > Table.HigherBet)
                Table.HigherBet = amnt;

            LogManager.Log(LogLevel.MessageVeryLow, "PokerGame.PlayMoney", "Total blinds still needed is {0}", GameTable.TotalBlindNeeded);

            //If we got all the blinds, what are we waiting for ?!
            if (GameTable.TotalBlindNeeded == 0)
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
            GameTable.ManagePotsRoundEnd();

            GameBettingRoundEnded(this, new RoundEventArgs(Table.Round));

            if (Table.NbPlayingAndAllIn <= 1)
                AdvanceToNextGameState(); //Advancing to Showdown State
            else
                AdvanceToNextRound(); //Advancing to Next Round
        }
        private void StartBettingRound()
        {
            GameBettingRoundStarted(this, new RoundEventArgs(Table.Round));

            Table.NbPlayed = 0;
            Table.NoSeatLastRaise = Table.GetPlayingPlayerNextTo(Table.NoSeatCurrPlayer).NoSeat;
            Table.MinimumRaiseAmount = Table.Params.BlindAmount;

            WaitALittle(Params.WaitingTimes.AfterBoardDealed);

            if (Table.NbPlaying <= 1)
                EndBettingRound();
            else
                ContinueBettingRound();
        }
        private void StartCardRound()
        {
            switch (Table.Round)
            {
                case RoundTypeEnum.Preflop:
                    Table.NoSeatCurrPlayer = Table.NoSeatBigBlind;
                    DealHole();
                    break;
                case RoundTypeEnum.Flop:
                    DealFlop();
                    break;
                case RoundTypeEnum.Turn:
                    DealTurn();
                    break;
                case RoundTypeEnum.River:
                    DealRiver();
                    break;
            }

            AdvanceToNextRoundState(); // Advance to Betting Round State
        }
        private void DealRiver()
        {
            GameTable.AddCards(m_Dealer.DealRiver());
        }
        private void DealTurn()
        {
            GameTable.AddCards(m_Dealer.DealTurn());
        }
        private void DealFlop()
        {
            GameTable.AddCards(m_Dealer.DealFlop());
        }
        private void DealHole()
        {
            foreach (PlayerInfo p in Table.PlayingAndAllInPlayers)
            {
                p.Cards = m_Dealer.DealHoles();
                PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p));
            }
        }
        private void ShowAllCards()
        {
            foreach (PlayerInfo p in Table.Players)
                if (p.IsPlaying || p.IsAllIn)
                {
                    p.IsShowingCards = true;
                    PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p));
                }
            AdvanceToNextGameState(); //Advancing to DecideWinners State
        }
        private void FoldPlayer(PlayerInfo p)
        {
            p.State = PlayerStateEnum.SitIn;

            WaitALittle(Params.WaitingTimes.AfterPlayerAction);

            PlayerActionTaken(this, new PlayerActionEventArgs(p, GameActionEnum.Fold, -1));
        }
        private void CallPlayer(PlayerInfo p, int played)
        {
            Table.NbPlayed++;

            WaitALittle(Params.WaitingTimes.AfterPlayerAction);

            PlayerActionTaken(this, new PlayerActionEventArgs(p, GameActionEnum.Call, played));
        }
        private void RaisePlayer(PlayerInfo p, int played)
        {
            // Since every raise "restart" the round, 
            // the number of players who played is the number of AllIn players plus the raising player
            Table.NbPlayed = Table.NbAllIn;
            if (!p.IsAllIn)
                Table.NbPlayed++;

            Table.NoSeatLastRaise = p.NoSeat;
            Table.HigherBet = p.MoneyBetAmnt;

            WaitALittle(Params.WaitingTimes.AfterPlayerAction);

            PlayerActionTaken(this, new PlayerActionEventArgs(p, GameActionEnum.Raise, played));
        }
        private void ContinueBettingRound()
        {
            if (Table.NbPlayingAndAllIn == 1 || Table.NbPlayed >= Table.NbPlayingAndAllIn)
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
            PlayerInfo next = Table.GetPlayingPlayerNextTo(Table.NoSeatCurrPlayer);

            Table.NoSeatCurrPlayer = next.NoSeat;

            PlayerActionNeeded(this, new HistoricPlayerInfoEventArgs(next,Table.CurrentPlayer));

            if (next.IsZombie)
            {
                if (Table.CanCheck(next))
                    PlayMoney(next, 0);
                else
                    PlayMoney(next, -1);
            }
        }
        private void DistributeMoney()
        {
            foreach (MoneyPot pot in Table.Pots)
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
                            WaitALittle(Params.WaitingTimes.AfterPotWon);
                        }
                    }
                }
            }
        }
        private void DecideWinners()
        {
            GameTable.CleanPotsForWinning();
            AdvanceToNextGameState(); //Advancing to DistributeMoney State
        }
        private void WaitALittle(int waitingTime)
        {
            Thread.Sleep(waitingTime);
        }
        private void TryToBegin()
        {
            foreach (PlayerInfo p in Table.Players)
            {
                if (p.IsZombie)
                    LeaveGame(p);
                else if (p.CanPlay)
                {
                    p.State = PlayerStateEnum.Playing;
                    p.IsShowingCards = false;
                }
                else
                    p.State = PlayerStateEnum.SitIn;
            }

            if (Table.NbPlaying >= Table.Params.MinPlayersToStart)
            {
                Table.Params.MinPlayersToStart = 2;
                Table.InitTable();
                m_Dealer.FreshDeck();
                AdvanceToNextGameState(); //Advancing to WaitForBlinds State
                GameBlindNeeded(this, new EventArgs());
            }
            else
            {
                Table.NoSeatDealer = -1;
                Table.NoSeatSmallBlind = -1;
                Table.NoSeatSmallBlind = -1;
            }
        }
        #endregion Private Methods
    }
}
