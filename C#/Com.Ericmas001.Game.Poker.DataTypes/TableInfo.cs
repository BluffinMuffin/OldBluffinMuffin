using System;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001.Games;
using Com.Ericmas001;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class TableInfo
    {
        #region Fields
        protected readonly GameCard[] m_Cards = new GameCard[5];
        private SeatInfo[] m_Seats;
        private readonly List<PlayerInfo> m_People = new List<PlayerInfo>();
        protected readonly List<MoneyPot> m_Pots = new List<MoneyPot>();
        protected TableParams m_Params;
        #endregion Fields

        #region Properties
        /// <summary>
        /// Contains all the rules of the current game
        /// </summary>
        public TableParams Params
        {
            get
            {
                return m_Params;
            }
            set
            {
                m_Params = value;
                m_Seats = new SeatInfo[value.MaxPlayers];
                for (int i = 0; i < value.MaxPlayers; ++i)
                    m_Seats[i] = new SeatInfo() { NoSeat = i };
            }
        }
        /// <summary>
        /// Contains all the People that are watching anbd playing the game. Everybody in the room.
        /// </summary>
        public List<PlayerInfo> People { get { return m_People; } }

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
        public int SmallBlindAmnt { get { return Params.BlindAmount / 2; } }

        /// <summary>
        /// Minimum amount to Raise
        /// </summary>
        public int MinimumRaiseAmount { get; set; }

        /// <summary>
        /// Where is the Dealer
        /// </summary>
        public SeatInfo DealerSeat
        {
            get
            {
                return m_Seats.FirstOrDefault(s => s.Attributes.Contains(SeatAttributeEnum.Dealer));
            }
        }
        public int NoSeatDealer
        {
            get
            {
                return DealerSeat == null ? -1 : DealerSeat.NoSeat;
            }
        }

        /// <summary>
        /// Where is the Small Blind
        /// </summary>
        public int NoSeatSmallBlind { get; set; }

        /// <summary>
        /// Where is the Big Blind
        /// </summary>
        public int NoSeatBigBlind { private get; set; }
        public IEnumerable<SeatInfo> BigBlinds 
        { 
            get
            {
                if (NoSeatBigBlind == -1)
                    return new SeatInfo[0];
                else
                    return new SeatInfo[] { m_Seats[NoSeatBigBlind] };
            }
        }

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
        public List<PlayerInfo> Players { get { return m_Seats.Where(s => !s.IsEmpty).Select(s => s.Player).ToList(); } }

        /// <summary>
        /// List of the Seats
        /// </summary>
        public List<SeatInfo> Seats { get { return m_Seats.ToList(); } }

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
            : this(new TableParams())
        {
        }

        public TableInfo(TableParams parms)
        {
            Params = parms;
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
        /// Who is the player for this seat number ?
        /// </summary>
        public PlayerInfo GetPlayer(int seat)
        {
            return m_Seats[seat].Player;
        }

        /// <summary>
        /// Return the next playing player next to a seat number (All-In not included)
        /// </summary>
        public PlayerInfo GetPlayingPlayerNextTo(int seat)
        {
            for (int i = 0; i < Params.MaxPlayers; ++i)
            {
                SeatInfo si = m_Seats[(seat + 1 + i) % Params.MaxPlayers];
                if (!si.IsEmpty && si.Player.IsPlaying)
                    return si.Player;
            }
            return m_Seats[seat].Player;
        }

        public virtual bool JoinTable(PlayerInfo p)
        {
            People.Add(p);
            p.State = PlayerStateEnum.Joined;
            return true;
        }

        /// <summary>
        /// Sit a player without the validations. This is used here after validation, or on the client side when the game is telling the client where a player is seated
        /// </summary>
        public SeatInfo SitInToTable(PlayerInfo p, int seat)
        {
            p.State = PlayerStateEnum.SitIn;
            p.NoSeat = seat;
            m_Seats[seat].Player = p;
            return m_Seats[seat];
        }

        /// <summary>
        /// When a player leaves the table
        /// </summary>
        public virtual bool LeaveTable(PlayerInfo p)
        {
            if (!PeopleContainsPlayer(p))
                return false;
            People.Remove(p);

            return SitOut(p);
        }

        public virtual bool SitOut(PlayerInfo p)
        {
            if (!SeatsContainsPlayer(p))
                return true;

            int seat = p.NoSeat;
            p.State = PlayerStateEnum.Zombie;
            p.NoSeat = -1;
            m_Seats[seat].Player = null;
            return true;
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
            return m_Seats.Where(s => (!s.IsEmpty && s.Player.IsPlaying)).Select(s => s.Player).ToList();
        }
        protected List<PlayerInfo> PlayingAndAllInPlayersFrom(int seat)
        {
            return m_Seats.Where(s => (!s.IsEmpty && (s.Player.IsPlaying || s.Player.IsAllIn))).Select(s => s.Player).ToList();
        }

        protected bool SeatsContainsPlayer(PlayerInfo p)
        {
            return Players.Contains(p) || Players.Count(x => x.Name.ToLower() == p.Name.ToLower()) > 0;
        }
        protected bool PeopleContainsPlayer(PlayerInfo p)
        {
            return People.Contains(p) || People.Count(x => x.Name.ToLower() == p.Name.ToLower()) > 0;
        }
        protected virtual void PlaceButtons()
        {
            NoSeatSmallBlind = NbPlaying == 2 ? NoSeatDealer : GetPlayingPlayerNextTo(NoSeatDealer).NoSeat;
            NoSeatBigBlind = GetPlayingPlayerNextTo(NoSeatSmallBlind).NoSeat;
        }
        #endregion Protected Methods
    }
}
