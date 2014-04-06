using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using EricUtility;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class TableInfo
    {
        #region Fields
        protected readonly GameCard[] m_Cards = new GameCard[5];
        protected readonly PlayerInfo[] m_Players;
        protected readonly List<MoneyPot> m_Pots = new List<MoneyPot>();
        #endregion Fields

        #region Properties
        /// <summary>
        /// Contains all the rules of the current game
        /// </summary>
        public GameRule Rules { get; set; }

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
        public int SmallBlindAmnt { get { return Rules.BlindAmount / 2; } }

        /// <summary>
        /// Minimum amount to Raise
        /// </summary>
        public int MinimumRaiseAmount { get; set; }

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
        /// Who is the current player
        /// </summary>
        public PlayerInfo CurrentPlayer { get { return GetPlayer(NoSeatCurrPlayer); } }

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
        public RoundTypeEnum Round { get; set; }

        /// <summary>
        /// List of the Players currently seated
        /// </summary>
        public List<PlayerInfo> Players { get { return m_Players.Where(p => p != null).ToList(); } }

        /// <summary>
        /// List of the Seats
        /// </summary>
        public List<PlayerInfo> Seats { get { return m_Players.ToList(); } }

        /// <summary>
        /// List of the playing Players in order starting from the first seat
        /// </summary>
        public List<PlayerInfo> PlayingPlayers
        {
            get { return PlayingPlayersFrom(0); }
        }

        /// <summary>
        /// List of the playing Players in order starting from the first seat
        /// </summary>
        public List<PlayerInfo> PlayingAndAllInPlayers
        {
            get { return PlayingAndAllInPlayersFrom(0); }
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
                if (Round == RoundTypeEnum.Preflop)
                {
                    return PlayingPlayersFrom(GetPlayingPlayerNextTo(NoSeatBigBlind).NoSeat);
                }
                return PlayingPlayersFrom(GetPlayingPlayerNextTo(NoSeatDealer).NoSeat);
            }
        }
        #endregion Properties

        #region Ctors & Init
        public TableInfo()
            : this(new GameRule())
        {
        }

        public TableInfo(GameRule rules)
        {
            Rules = rules;
            m_Players = new PlayerInfo[rules.MaxPlayers];
            NoSeatDealer = -1;
            NoSeatSmallBlind = -1;
            NoSeatBigBlind = -1;
        }

        public virtual void InitTable()
        {
            Cards = new GameCard[5];
            NbPlayed = 0;
            PlaceButtons();
            TotalPotAmnt = 0;
            m_Pots.Clear();
            m_Pots.Add(new MoneyPot(0));
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
            for (int i = 0; i < Rules.MaxPlayers; ++i)
            {
                int j = (seat + 1 + i) % Rules.MaxPlayers;
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
            for (int i = 0; i < Rules.MaxPlayers; ++i)
            {
                int j = (seat + 1 + i) % Rules.MaxPlayers;
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
        /// Sit a player without the validations. This is used here after validation, or on the client side when the game is telling the client where a player is seated
        /// </summary>
        public bool ForceJoinTable(PlayerInfo p, int seat)
        {
            p.IsZombie = false;
            p.IsPlaying = false;
            p.NoSeat = seat;
            m_Players[seat] = p;
            return true;
        }

        /// <summary>
        /// When a player leaves the table
        /// </summary>
        public virtual bool LeaveTable(PlayerInfo p)
        {
            if (!ContainsPlayer(p))
                return false;

            int seat = p.NoSeat;
            p.IsPlaying = false;
            p.IsZombie = true;
            m_Players[seat] = null;
            return true;
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
            return Math.Min(CallAmnt(p) + MinimumRaiseAmount, MaxRaiseAmnt(p));
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

        #region Protected Methods
        protected List<PlayerInfo> PlayingPlayersFrom(int seat)
        {
            return m_Players.Where(p => (p != null && p.IsPlaying)).ToList();
        }
        protected List<PlayerInfo> PlayingAndAllInPlayersFrom(int seat)
        {
            return m_Players.Where(p => (p != null && (p.IsPlaying || p.IsAllIn))).ToList();
        }

        protected bool ContainsPlayer(PlayerInfo p)
        {
            return Players.Contains(p) || Players.Count(x => x.Name.ToLower() == p.Name.ToLower()) > 0;
        }
        protected virtual void PlaceButtons()
        {
            NoSeatDealer = GetPlayingPlayerNextTo(NoSeatDealer).NoSeat;
            NoSeatSmallBlind = NbPlaying == 2 ? NoSeatDealer : GetPlayingPlayerNextTo(NoSeatDealer).NoSeat;
            NoSeatBigBlind = GetPlayingPlayerNextTo(NoSeatSmallBlind).NoSeat;
        }
        #endregion Protected Methods
    }
}
