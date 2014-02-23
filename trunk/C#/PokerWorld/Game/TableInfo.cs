﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using EricUtility;
using PokerWorld.Game.Enums;
using System.Linq;

namespace PokerWorld.Game
{
    public class TableInfo
    {
        #region Fields
        protected readonly GameCard[] m_Cards = new GameCard[5];
        protected readonly PlayerInfo[] m_Players;
        protected readonly List<MoneyPot> m_Pots = new List<MoneyPot>();
        protected readonly Stack<int> m_RemainingSeats = new Stack<int>(); // LIFO with the unused seats
        protected readonly List<int> m_AllInCaps = new List<int>(); // All the distincts ALL_IN CAPS of the ROUND
        protected readonly Dictionary<PlayerInfo, int> m_BlindNeeded = new Dictionary<PlayerInfo, int>();
        protected int m_CurrPotId;
        #endregion Fields

        #region Properties
        /// <summary>
        /// Table Name
        /// </summary>
        public string Name { get; private set; }

        /// <summary>
        /// Type of Limit: NO_LIMIT, POT_LIMIT, ...
        /// </summary>
        public BetEnum BetLimit { get; set; }

        /// <summary>
        /// Cards on the Board
        /// </summary>
        public GameCard[] Cards
        {
            get { return m_Cards.Select(c => c == null ? GameCard.NO_CARD : c).ToArray(); }
            set
            {
                if (value != null && value.Length == 5)
                {
                    for (int i = 0; i < 5; ++i)
                        m_Cards[i] = value[i];
                }
            }
        }

        /// <summary>
        /// Maximum of players that can be seated around the table
        /// </summary>
        public int NbMaxSeats { get; private set; }

        /// <summary>
        /// List of MoneyPots currently on the table. There should always have at least one MoneyPot
        /// </summary>
        public List<MoneyPot> Pots
        {
            get { return m_Pots; }
        }

        /// <summary>
        /// Contains all the money currently on the table (All Pots + Money currently played in front of the players)
        /// </summary>
        public int TotalPotAmnt{ get; set; }

        /// <summary>
        /// Amount of the Small Blind
        /// </summary>
        public int SmallBlindAmnt { get; private set; }

        /// <summary>
        /// Amount of the Big Blind
        /// </summary>
        public int BigBlindAmnt { get; private set; }

        /// <summary>
        /// Total amount of money still needed as Blinds for the game to start
        /// </summary>
        public int TotalBlindNeeded { get { return m_BlindNeeded.Values.Sum(); } }

        /// <summary>
        /// Where is the Dealer
        /// </summary>
        public int NoSeatDealer { get; set; }

        /// <summary>
        /// Where is the Small Blind
        /// </summary>
        public int NoSeatSmallBlind { get; set; }

        /// <summary>
        /// Where is the Big Blind
        /// </summary>
        public int NoSeatBigBlind { get; set; }

        /// <summary>
        /// Where is the current player
        /// </summary>
        public int NoSeatCurrPlayer { get; set; }

        /// <summary>
        /// Where is the one who setted the current Bet Amount
        /// </summary>
        public int NoSeatLastRaise { get; set; }

        /// <summary>
        /// How many player have played this round and are ready to play the next one
        /// </summary>
        public int NbPlayed { get; set; }

        /// <summary>
        /// How many players are All In
        /// </summary>
        public int NbAllIn { get; set; }

        /// <summary>
        /// How many players are still in the Game (All-In not included)
        /// </summary>
        public int NbPlaying { get { return PlayingPlayers.Count; } }

        /// <summary>
        /// How many players are still in the Game (All-In included)
        /// </summary>
        public int NbPlayingAndAllIn { get { return NbPlaying + NbAllIn; } }

        /// <summary>
        /// What is the amount to equal to stay in the game ?
        /// </summary>
        public int HigherBet { get; set; }

        /// <summary>
        /// What is the actual Round of the Game
        /// </summary>
        public RoundEnum Round { get; set; }

        /// <summary>
        /// List of the Players currently seated
        /// </summary>
        public List<PlayerInfo> Players { get { return m_Players.Where(p => p != null).ToList(); } }

        /// <summary>
        /// List of the playing Players in order starting from the first seat
        /// </summary>
        public List<PlayerInfo> PlayingPlayers
        {
            get { return PlayingPlayersFrom(0); }
        }

        /// <summary>
        /// List of the playing Players in order starting from the next player that will have to play
        /// </summary>
        public List<PlayerInfo> PlayingPlayersFromNext
        {
            get { return PlayingPlayersFrom(GetPlayingPlayerNextTo(NoSeatCurrPlayer).NoSeat); }
        }

        /// <summary>
        /// List of the playing Players in order starting from the current playing player
        /// </summary>
        public List<PlayerInfo> PlayingPlayersFromCurrent
        {
            get { return PlayingPlayersFrom(NoSeatCurrPlayer); }
        }

        /// <summary>
        /// List of the playing Players in order starting from the last raising player
        /// </summary>
        public List<PlayerInfo> PlayingPlayersFromLastRaise
        {
            get { return PlayingPlayersFrom(NoSeatLastRaise); }
        }

        // List of the playing Players in order starting from the one who started the round
        public List<PlayerInfo> PlayingPlayersFromFirst
        {
            get
            {
                if (Round == RoundEnum.Preflop)
                {
                    return PlayingPlayersFrom(GetPlayingPlayerNextTo(NoSeatBigBlind).NoSeat);
                }
                return PlayingPlayersFrom(GetPlayingPlayerNextTo(NoSeatDealer).NoSeat);
            }
        }
        #endregion Properties

        #region Ctors & Init
        public TableInfo()
            : this(10)
        {
        }

        public TableInfo(int nbSeats)
            : this("Anonymous Table", 10, nbSeats, BetEnum.NoLimit)
        {
        }

        public TableInfo(string name, int bigBlind, int nbSeats, BetEnum limit)
        {
            NbMaxSeats = nbSeats;
            m_Players = new PlayerInfo[nbSeats];
            Name = name;
            BigBlindAmnt = bigBlind;
            SmallBlindAmnt = bigBlind / 2;
            NoSeatDealer = -1;
            NoSeatSmallBlind = -1;
            NoSeatBigBlind = -1;
            BetLimit = limit;
            Enumerable.Range(1, NbMaxSeats).ToList().ForEach(i => m_RemainingSeats.Push(NbMaxSeats - i));
        }

        public void InitTable()
        {
            Cards = new GameCard[5];
            NbPlayed = 0;
            PlaceButtons();
            TotalPotAmnt = 0;
            m_Pots.Clear();
            m_AllInCaps.Clear();
            m_Pots.Add(new MoneyPot(0));
            m_CurrPotId = 0;
            NbAllIn = 0;
        }
        #endregion

        #region Public Methods
        /// <summary>
        /// Sets the cards on the table (Used by the client)
        /// </summary>
        public void SetCards(GameCard c1, GameCard c2, GameCard c3, GameCard c4, GameCard c5)
        {
            Cards = new GameCard[5] { c1, c2, c3, c4, c5 };
        }

        /// <summary>
        /// Add cards to the board
        /// </summary>
        public void AddCards(params GameCard[] c)
        {
            int firstUnused =  Enumerable.Range(0,m_Cards.Length).First(i => m_Cards[i] == null || m_Cards[i].ToString() == GameCard.NO_CARD.ToString());
            for (int j = firstUnused; j < Math.Min(5, c.Length + firstUnused); ++j)
                m_Cards[j] = c[j - firstUnused];
        }

        /// <summary>
        /// Who is the player for this seat number ?
        /// </summary>
        public PlayerInfo GetPlayer(int seat)
        {
            return m_Players[seat];
        }


        /// <summary>
        /// Return the next playing player next to a seat numberr (Any player is included, GamingStatus Invariant)
        /// </summary>
        public PlayerInfo GetPlayerNextTo(int seat)
        {
            for (int i = 0; i < NbMaxSeats; ++i)
            {
                int j = (seat + 1 + i) % NbMaxSeats;
                if (m_Players[j] != null)
                {
                    return m_Players[j];
                }
            }
            return m_Players[seat];
        }

        /// <summary>
        /// Return the next playing player next to a seat number (All-In not included)
        /// </summary>
        public PlayerInfo GetPlayingPlayerNextTo(int seat)
        {
            for (int i = 0; i < NbMaxSeats; ++i)
            {
                int j = (seat + 1 + i) % NbMaxSeats;
                if (m_Players[j] != null && m_Players[j].IsPlaying)
                {
                    return m_Players[j];
                }
            }
            return m_Players[seat];
        }

        /// <summary>
        /// Is there already a player of that name, seated at the table ?
        /// </summary>
        public bool ContainsPlayer(String name)
        {
            return Players.Any(p => p.Name.Equals(name, StringComparison.InvariantCultureIgnoreCase));
        }

        /// <summary>
        /// Add an AllInCap that will be used when splitting the pot
        /// </summary>
        public void AddAllInCap(int val)
        {
            if (!m_AllInCaps.Contains(val))
                m_AllInCaps.Add(val);
        }

        /// <summary>
        /// Sets how much money is still needed from a specific player as Blind
        /// </summary>
        public void SetBlindNeeded(PlayerInfo p, int amnt)
        {
            if (m_BlindNeeded.ContainsKey(p))
                m_BlindNeeded[p] = amnt;
            else
                m_BlindNeeded.Add(p, amnt);
        }

        /// <summary>
        /// How much money a player needs to put as Blind
        /// </summary>
        public int GetBlindNeeded(PlayerInfo p)
        {
            if (m_BlindNeeded.ContainsKey(p))
                return m_BlindNeeded[p];
            else
                return 0;
        }

        /// <summary>
        /// Sit a player without the validations. This is used here after validation, or on the client side when the game is telling the client where a player is seated
        /// </summary>
        public bool ForceJoinTable(PlayerInfo p, int seat)
        {
            p.IsPlaying = false;
            p.NoSeat = seat;
            m_Players[seat] = p;
            return true;
        }

        /// <summary>
        /// When a player joined the table
        /// </summary>
        public bool JoinTable(PlayerInfo p)
        {
            if (m_RemainingSeats.Count == 0)
            {
                LogManager.Log(LogLevel.Error, "TableInfo.JoinTable", "Not enough seats to join!");
                return false;
            }

            if (ContainsPlayer(p))
            {
                LogManager.Log(LogLevel.Error, "TableInfo.JoinTable", "Already someone with the same name!");
                return false;
            }

            ForceJoinTable(p,  m_RemainingSeats.Pop());
            return true;
        }

        /// <summary>
        /// When a player leaves the table
        /// </summary>
        public bool LeaveTable(PlayerInfo p)
        {
            if (!ContainsPlayer(p))
                return false;

            int seat = p.NoSeat;
            p.IsPlaying = false;
            m_Players[seat] = null;
            m_RemainingSeats.Push(seat);
            return true;
        }

        /// <summary>
        /// At the end of a Round, it's time to separate all the money into one or more pots of money (Depending on when a player wen All-In)
        /// For every cap, we take money from each player that still have money in front of them
        /// </summary>
        public void ManagePotsRoundEnd()
        {
            int currentTaken = 0;
            m_AllInCaps.Sort();

            while (m_AllInCaps.Count > 0)
            {
                MoneyPot pot = m_Pots[m_CurrPotId];
                pot.DetachAllPlayers();

                int aicf = m_AllInCaps[0];
                m_AllInCaps.RemoveAt(0);

                int cap = aicf - currentTaken;
                foreach (PlayerInfo p in Players)
                    AddBet(p, pot, Math.Min(p.MoneyBetAmnt, cap));

                currentTaken += cap;
                m_CurrPotId++;
                m_Pots.Add(new MoneyPot(m_CurrPotId));
            }

            MoneyPot curPot = m_Pots[m_CurrPotId];
            curPot.DetachAllPlayers();
            foreach (PlayerInfo p in Players)
                AddBet(p, curPot, p.MoneyBetAmnt);

            HigherBet = 0;
        }

        /// <summary>
        /// Detach all the players that are not winning this pot
        /// </summary>
        public void CleanPotsForWinning()
        {
            for (int i = 0; i <= m_CurrPotId; ++i)
            {
                MoneyPot pot = m_Pots[i];
                uint bestHand = 0;
                List<PlayerInfo> infos = new List<PlayerInfo>(pot.AttachedPlayers);

                //If there is more than one player attach to the pot, we need to choose who will split it !
                if (infos.Count > 1)
                {
                    foreach (PlayerInfo p in infos)
                    {
                        uint handValue = p.EvaluateCards(Cards);
                        if (handValue > bestHand)
                        {
                            pot.DetachAllPlayers();
                            pot.AttachPlayer(p);
                            bestHand = handValue;
                        }
                        else if (handValue == bestHand)
                            pot.AttachPlayer(p);
                    }
                }
            }
        }

        /// <summary>
        /// Can this user RAISE ?
        /// </summary>
        public bool CanRaise(PlayerInfo p)
        {
            return HigherBet < p.MoneyAmnt;
        }

        /// <summary>
        /// Can this player use the CHECK action ?
        /// </summary>
        public bool CanCheck(PlayerInfo p)
        {
            return HigherBet <= p.MoneyBetAmnt;
        }

        /// <summary>
        /// What is the minimum amount that this player can put on the table to RAISE
        /// </summary>
        public int MinRaiseAmnt(PlayerInfo p)
        {
            return Math.Min(CallAmnt(p) + BigBlindAmnt, MaxRaiseAmnt(p));
        }

        /// <summary>
        /// What is the maximum amount that this player can put on the table ?
        /// </summary>
        /// <param name="p"></param>
        /// <returns></returns>
        public int MaxRaiseAmnt(PlayerInfo p)
        {
            return p.MoneySafeAmnt;
        }

        /// <summary>
        /// What is the needed amount for this player to CALL ?
        /// </summary>
        public int CallAmnt(PlayerInfo p)
        {
            return HigherBet - p.MoneyBetAmnt;
        }
        #endregion Public Methods

        #region Private Methods
        private List<PlayerInfo> PlayingPlayersFrom(int seat)
        {
            return m_Players.Where(p => (p != null && p.IsPlaying)).ToList();
        }

        private bool ContainsPlayer(PlayerInfo p)
        {
            return Players.Contains(p);
        }

        private void AddBet(PlayerInfo p, MoneyPot pot, int bet)
        {
            p.MoneyBetAmnt -= bet;
            pot.AddAmount(bet);

            if (bet >= 0 && (p.IsPlaying || p.IsAllIn))
                pot.AttachPlayer(p);
        }
        private void PlaceButtons()
        {
            NoSeatDealer = GetPlayingPlayerNextTo(NoSeatDealer).NoSeat;
            NoSeatSmallBlind = NbPlaying == 2 ? NoSeatDealer : GetPlayingPlayerNextTo(NoSeatDealer).NoSeat;
            NoSeatBigBlind = GetPlayingPlayerNextTo(NoSeatSmallBlind).NoSeat;
            m_BlindNeeded.Clear();
            m_BlindNeeded.Add(GetPlayer(NoSeatSmallBlind), SmallBlindAmnt);
            m_BlindNeeded.Add(GetPlayer(NoSeatBigBlind), BigBlindAmnt);
        }
        #endregion Private Methods
    }
}