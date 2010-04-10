using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace PokerWorld.Game
{
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
        public event EventHandler<HistoricPlayerInfoEventArgs> PlayerActionNeeded = delegate { };
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
        public TypeRound Round
        {
            get { return m_Table.Round; }
        }

        public bool IsRunning
        {
            get { return m_State != TypeState.End; }
        }

        public string Encode
        {
            get
            {
                // 0 : Assume que les game sont en real money (1)
                // 1 : Assume que c'est tlt du Texas Hold'em (0)
                // 2 : Assume que c'Est tlt des Ring game (0)
                // 3 : Assume que c'Est tlt du NoLimit (0)
                // 4 : GameRound (0,1,2,3)
                return string.Format("{0}{1}{2}{3}{4}",1,0,0,0,(int)m_Table.Round);
            }
        }

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
            m_State = TypeState.Init;
            m_RoundState = TypeRoundState.Cards;
            m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
            m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
            m_WaitingTimeAfterPotWon = wtaPotWon;
        }

        private void NextState(TypeState state)
        {
            TypeState oldState = m_State;

            if (m_State == TypeState.End)
                return;

            if ((int)state - (int)oldState != 1)
                return;

            m_State = state;

            switch (m_State)
            {
                case TypeState.Init:
                    break;
                case TypeState.WaitForPlayers:
                    TryToBegin();
                    break;
                case TypeState.WaitForBlinds:
                    m_Table.HigherBet = 0;
                    break;
                case TypeState.Playing:
                    m_Table.Round = TypeRound.Preflop;
                    m_RoundState = TypeRoundState.Cards;
                    StartRound();
                    break;
                case TypeState.Showdown:
                    ShowAllCards();
                    break;
                case TypeState.DecideWinners:
                    DecideWinners();
                    break;
                case TypeState.DistributeMoney:
                    DistributeMoney();
                    break;
                case TypeState.End:
                    EverythingEnded(this, new EventArgs());
                    break;
            }
        }
        private void NextRound(TypeRound round)
        {

            TypeRound oldRound = m_Table.Round;

            if (m_State != TypeState.Playing)
                return;

            if ((int)round - (int)oldRound != 1)
                return;

            m_RoundState = TypeRoundState.Cards;
            m_Table.NoSeatCurrPlayer = m_Table.NoSeatDealer;
            m_Table.Round = round;
            StartRound();
        }
        private void NextRoundState(TypeRoundState roundState)
        {

            TypeRoundState oldRoundState = m_RoundState;

            if (m_State != TypeState.Playing)
                return;

            if ((int)roundState - (int)oldRoundState != 1)
                return;

            m_RoundState = roundState;
            StartRound();
        }

        public void Start()
        {
            NextState(TypeState.WaitForPlayers);
        }

        public bool JoinGame(PlayerInfo p)
        {
            if (m_State == TypeState.Init || m_State == TypeState.End)
            {
                Console.WriteLine("Bad timing:" + m_State);
                return false;
            }

            return m_Table.JoinTable(p);
        }

        public void SitInGame(PlayerInfo p)
        {
            PlayerJoined(this, new PlayerInfoEventArgs(p));
            if (m_State == TypeState.WaitForPlayers)
                TryToBegin();
        }

        public bool LeaveGame(PlayerInfo p)
        {
            if (m_Table.LeaveTable(p))
            {
                PlayerLeaved(this, new PlayerInfoEventArgs(p));
                if (m_Table.NbUsedSeats == 0)
                    m_State = TypeState.End;
                return true;
            }
            return false;
        }

        public bool PlayMoney(PlayerInfo p, int amount)
        {
            int amnt = Math.Min(amount, p.MoneySafeAmnt);
            Console.WriteLine(p.Name + " is playing " + amnt + " money on state: " + m_State);
            if (m_State == TypeState.WaitForBlinds)
            {
                Console.WriteLine("Total blinds needed is " + m_Table.TotalBlindNeeded);
                Console.WriteLine(p.Name + " is putting blind of " + amnt);
                int needed = m_Table.GetBlindNeeded(p);
                if (amnt != needed)
                {
                    if (p.CanBet(amnt + 1))
                    {
                        Console.WriteLine(p.Name + " needed to put " + needed);
                        return false;
                    }
                    else
                    {
                        Console.WriteLine("So ... All-In !");
                        p.IsAllIn = true;
                        m_Table.NbAllIn++;
                        m_Table.AddAllInCap(p.MoneyBetAmnt + amnt);
                    }
                }
                if (!p.TryBet(amnt))
                {
                    Console.WriteLine(p.Name + " just .. can't !! ");
                    return false;
                }
                m_Table.TotalPotAmnt += amnt;
                PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));
                m_Table.AddBlindNeeded(p, 0);

                if (amnt == m_Table.SmallBlindAmnt)
                    PlayerActionTaken(this, new PlayerActionEventArgs(p, TypeAction.PostSmallBlind, amnt));
                else
                    PlayerActionTaken(this, new PlayerActionEventArgs(p, TypeAction.PostBigBlind, amnt));

                m_Table.TotalBlindNeeded -= needed;

                if (m_Table.TotalBlindNeeded == 0)
                    NextState(TypeState.Playing);

                if (amnt > m_Table.HigherBet)
                    m_Table.HigherBet = amnt;

                Console.WriteLine("Total blinds still needed is " + m_Table.TotalBlindNeeded);
                return true;
            }

            else if (m_State == TypeState.Playing && m_RoundState == TypeRoundState.Betting)
            {
                Console.WriteLine("Currently, we need " + m_Table.CallAmnt(p) + " minimum money from this player");
                if (p.NoSeat != m_Table.NoSeatCurrPlayer)
                {
                    Console.WriteLine("BUT SCREW YOU, IT'S NOT YOUR TURN !!!!!");
                    return false;
                }

                if (amnt == -1)
                {
                    Console.WriteLine("So ... the little girl folds !");
                    FoldPlayer(p);
                    ContinueBettingRound();
                    return true;
                }
                int amntNeeded = m_Table.CallAmnt(p);
                if (amnt < amntNeeded)
                {
                    if (p.CanBet(amnt + 1))
                    {
                        Console.WriteLine("BUT SCREW YOU, IT'S NOT ENOUGH !!!!!");
                        return false;
                    }
                    amntNeeded = amnt;
                }
                if (!p.TryBet(amnt))
                {
                    Console.WriteLine("BUT SCREW YOU, YOU JUST CAN'T !!!!!");
                    return false;
                }
                PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));
                if (p.MoneySafeAmnt == 0)
                {
                    Console.WriteLine("So ... All-In ! getCurrentBetMoneyAmount: " + p.MoneyBetAmnt);
                    p.IsAllIn = true;
                    m_Table.NbAllIn++;
                    m_Table.AddAllInCap(p.MoneyBetAmnt);
                }
                if (amnt == amntNeeded)
                {
                    Console.WriteLine("Will call with $" + amnt);
                    m_Table.TotalPotAmnt += amnt;
                    CallPlayer(p, amnt);
                    ContinueBettingRound();
                    return true;
                }
                Console.WriteLine("Will raise with $" + amnt);
                m_Table.TotalPotAmnt += amnt;
                RaisePlayer(p, amnt);
                ContinueBettingRound();
                return true;
            }
            Console.WriteLine("BUT WE DON'T CARE !!!!!");
            return false;
        }
        private void StartRound()
        {
            switch (m_RoundState)
            {
                case TypeRoundState.Cards:
                    StartCardRound();
                    break;
                case TypeRoundState.Betting:
                    StartBettingRound();
                    break;
                case TypeRoundState.Cumul:
                    StartCumulRound();
                    break;
            }
        }
        private void StartCumulRound()
        {
            m_Table.ManagePotsRoundEnd();
            GameBettingRoundEnded(this, new RoundEventArgs(m_Table.Round));
            if (m_Table.NbPlayingAndAllIn <= 1)
            {
                NextState(TypeState.Showdown);
            }
            else
            {
                switch (m_Table.Round)
                {
                    case TypeRound.Preflop:
                        NextRound(TypeRound.Flop);
                        break;
                    case TypeRound.Flop:
                        NextRound(TypeRound.Turn);
                        break;
                    case TypeRound.Turn:
                        NextRound(TypeRound.River);
                        break;
                    case TypeRound.River:
                        NextState(TypeState.Showdown);
                        break;
                }
            }
        }
        private void StartBettingRound()
        {
            GameBettingRoundStarted(this, new RoundEventArgs(m_Table.Round));
            m_Table.NbPlayed = 0;
            m_Table.NoSeatLastRaise = m_Table.GetPlayingPlayerNextTo(m_Table.NoSeatCurrPlayer).NoSeat;
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
                case TypeRound.Preflop:
                    m_Table.NoSeatCurrPlayer = m_Table.NoSeatBigBlind;
                    DealHole();
                    break;
                case TypeRound.Flop:
                    DealFlop();
                    break;
                case TypeRound.Turn:
                    DealTurn();
                    break;
                case TypeRound.River:
                    DealRiver();
                    break;
            }
            NextRoundState(TypeRoundState.Betting);
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
            foreach (PlayerInfo p in m_Table.PlayingPlayers)
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
            NextState(TypeState.DecideWinners);
        }
        private void FoldPlayer(PlayerInfo p)
        {
            p.IsPlaying = false;
            WaitALittle(m_WaitingTimeAfterPlayerAction);
            PlayerActionTaken(this, new PlayerActionEventArgs(p, TypeAction.Fold, -1));
        }
        private void CallPlayer(PlayerInfo p, int played)
        {
            m_Table.NbPlayed++;
            WaitALittle(m_WaitingTimeAfterPlayerAction);
            PlayerActionTaken(this, new PlayerActionEventArgs(p, TypeAction.Call, played));
        }
        private void RaisePlayer(PlayerInfo p, int played)
        {
            int count = m_Table.NbAllIn;
            if (!p.IsAllIn)
                count++;
            m_Table.NoSeatLastRaise = p.NoSeat;
            m_Table.NbPlayed = count;
            m_Table.HigherBet = p.MoneyBetAmnt;
            WaitALittle(m_WaitingTimeAfterPlayerAction);
            PlayerActionTaken(this, new PlayerActionEventArgs(p, TypeAction.Raise, played));
        }
        private void ContinueBettingRound()
        {
            if (m_Table.NbPlayingAndAllIn == 1 || m_Table.NbPlayed >= m_Table.NbPlayingAndAllIn)
                EndBettingRound();
            else
                PlayNext();
        }
        private void EndBettingRound()
        {
            NextRoundState(TypeRoundState.Cumul);
        }
        private void PlayNext()
        {
            PlayerInfo old = m_Table.GetPlayer(m_Table.NoSeatCurrPlayer);
            PlayerInfo player = m_Table.GetPlayingPlayerNextTo(m_Table.NoSeatCurrPlayer);
            m_Table.NoSeatCurrPlayer = player.NoSeat;
            PlayerActionNeeded(this, new HistoricPlayerInfoEventArgs(player,old));
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
            GameEnded(this, new EventArgs());
            m_State = TypeState.WaitForPlayers;
            TryToBegin();
        }
        private void DecideWinners()
        {
            m_Table.CleanPotsForWinning();
            NextState(TypeState.DistributeMoney);
        }
        private void WaitALittle(int waitingTime)
        {
            Thread.Sleep(waitingTime);
        }
        private void TryToBegin()
        {
            m_Table.DecidePlayingPlayers();
            if (m_Table.NbPlaying > 1)
            {
                m_Table.InitTable();
                m_Dealer.FreshDeck();
                NextState(TypeState.WaitForBlinds);
                GameBlindNeeded(this, new EventArgs());
            }
            else if (m_Table.Players.Count > 1)
            {
                m_State = TypeState.End;
                EverythingEnded(this, new EventArgs());
            }
            else
            {
                m_Table.NoSeatDealer = -1;
                m_Table.NoSeatSmallBlind = -1;
                m_Table.NoSeatSmallBlind = -1;
            }
        }
    }
}
