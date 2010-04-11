using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Games.CardGame;

namespace PokerWorld.Game
{
    public class TableInfo
    {
        // INFO
        private readonly string m_Name; // Nom de la table
        private TypeBet m_BetLimit; // Type de betLimit (NO_LIMIT, POT_LIMIT, etc.)

        // CARDS
        private readonly GameCard[] m_Cards = new GameCard[5]; // Cartes du board

        // SEATS
        private readonly int m_NbMaxSeats; // Nombe de siege total autour de la table
        private int m_NbUsedSeats; // Nombre de siege utilises autour de la table
        private readonly Stack<int> m_RemainingSeats = new Stack<int>(); // LIFO contenant les sieges non utilises

        // PLAYERS
        private readonly PlayerInfo[] m_Players; // Joueurs autour de la table

        // POTS
        private readonly List<MoneyPot> m_Pots = new List<MoneyPot>(); // La liste des POTS
        private int m_TotalPotAmnt; // Le montant total en jeu (Tous les pots + l'argent en jeu)
        private readonly List<int> m_AllInCaps = new List<int>(); // Les differents CAPS ALL_IN de la ROUND

        // BLINDS
        private readonly int m_SmallBlindAmnt; // Montant a donner lorsqu'on est small blind
        private readonly int m_BigBlindAmnt; // Montant a donner lorsqu'on est big blind
        private readonly Dictionary<PlayerInfo, int> m_BlindNeeded = new Dictionary<PlayerInfo, int>(); // Hashmap contenant les blinds necessaire pour chaque player
        private int m_TotalBlindNeeded; // Montant total necessaire pour les blinds

        // STATES
        private int m_NbPlayed; // Nombre de joueur ayant joues de cette ROUND
        private int m_NbAllIn; // Nombre de joueurs ALL-IN
        private int m_HigherBet; // Le bet actuel qu'il faut egaler
        private TypeRound m_Round; // La round actuelle
        private int m_NoSeatDealer; // La position actuelle du Dealer
        private int m_NoSeatSmallBlind; // La position actuelle du SmallBlind
        private int m_NoSeatBigBlind; // La position actuelle du BigBlind
        private int m_NoSeatCurrPlayer; // La position du joueur actuel
        private int m_CurrPotId; // L'id du pot qu'on travail actuellement avec
        private int m_NoSeatLastRaise; // L'id du dernier player qui a raiser ou du premier a jouer

        public string Name
        {
            get { return m_Name; }
        }

        public TypeBet BetLimit
        {
            get { return m_BetLimit; }
            set { m_BetLimit = value; }
        }

        public GameCard[] Cards
        {
            get
            {
                GameCard[] cards = new GameCard[5];
                for (int i = 0; i < 5; ++i)
                {
                    if (m_Cards[i] == null)
                        cards[i] = GameCard.NO_CARD;
                    else
                        cards[i] = m_Cards[i];
                }
                return cards;
            }
            set
            {
                if (value != null && value.Length == 5)
                {
                    for (int i = 0; i < 5; ++i)
                        m_Cards[i] = value[i];
                }
            }
        }
        public int NbMaxSeats
        {
            get { return m_NbMaxSeats; }
        }

        public int NbUsedSeats
        {
            get { return m_NbUsedSeats; }
            set { m_NbUsedSeats = value; }
        }

        public List<PlayerInfo> Players
        {
            get
            {
                List<PlayerInfo> list = new List<PlayerInfo>();
                for (int i = 0; i < m_NbMaxSeats; ++i)
                {
                    if (m_Players[i] != null)
                    {
                        list.Add(m_Players[i]);
                    }
                }
                return list;
            }
        }

        public List<PlayerInfo> PlayingPlayers
        {
            get { return PlayingPlayersFrom(0); }
        }

        public List<PlayerInfo> PlayingPlayersFromNext
        {
            get { return PlayingPlayersFrom(GetPlayingPlayerNextTo(m_NoSeatCurrPlayer).NoSeat); }
        }

        public List<PlayerInfo> PlayingPlayersFromCurrent
        {
            get { return PlayingPlayersFrom(m_NoSeatCurrPlayer); }
        }

        public List<PlayerInfo> PlayingPlayersFromLastRaise
        {
            get { return PlayingPlayersFrom(m_NoSeatLastRaise); }
        }

        public List<PlayerInfo> PlayingPlayersFromFirst
        {
            get
            {
                if (m_Round == TypeRound.Preflop)
                {
                    return PlayingPlayersFrom(GetPlayingPlayerNextTo(m_NoSeatBigBlind).NoSeat);
                }
                return PlayingPlayersFrom(GetPlayingPlayerNextTo(m_NoSeatDealer).NoSeat);
            }
        }

        public List<MoneyPot> Pots
        {
            get { return m_Pots; }
        }

        public int TotalPotAmnt
        {
            get { return m_TotalPotAmnt; }
            set { m_TotalPotAmnt = value; }
        }

        public int SmallBlindAmnt
        {
            get { return m_SmallBlindAmnt; }
        }

        public int BigBlindAmnt
        {
            get { return m_BigBlindAmnt; }
        }

        public int TotalBlindNeeded
        {
            get { return m_TotalBlindNeeded; }
            set { m_TotalBlindNeeded = value; }
        }

        public int NoSeatDealer
        {
            get { return m_NoSeatDealer; }
            set { m_NoSeatDealer = value; }
        }

        public int NoSeatSmallBlind
        {
            get { return m_NoSeatSmallBlind; }
            set { m_NoSeatSmallBlind = value; }
        }

        public int NoSeatBigBlind
        {
            get { return m_NoSeatBigBlind; }
            set { m_NoSeatBigBlind = value; }
        }

        public int NoSeatCurrPlayer
        {
            get { return m_NoSeatCurrPlayer; }
            set { m_NoSeatCurrPlayer = value; }
        }

        public int NoSeatLastRaise
        {
            get { return m_NoSeatLastRaise; }
            set { m_NoSeatLastRaise = value; }
        }

        public int NbPlayed
        {
            get { return m_NbPlayed; }
            set { m_NbPlayed = value; }
        }

        public int NbAllIn
        {
            get { return m_NbAllIn; }
            set { m_NbAllIn = value; }
        }

        public int NbPlaying
        {
            get { return PlayingPlayers.Count; }
        }

        public int NbPlayingAndAllIn
        {
            get { return NbPlaying + NbAllIn; }
        }
        public int HigherBet
        {
            get { return m_HigherBet; }
            set { m_HigherBet = value; }
        }
        public TypeRound Round
        {
            get { return m_Round; }
            set { m_Round = value; }
        }

        public TableInfo()
            : this(10)
        {
        }

        public TableInfo(int nbSeats)
            : this("Anonymous Table", 10, nbSeats, TypeBet.NoLimit)
        {
        }

        public TableInfo(string name, int bigBlind, int nbSeats, TypeBet limit)
        {
            m_NbMaxSeats = nbSeats;
            m_NbUsedSeats = 0;
            m_Players = new PlayerInfo[nbSeats];
            m_Name = name;
            m_BigBlindAmnt = bigBlind;
            m_SmallBlindAmnt = bigBlind / 2;
            m_NoSeatDealer = -1;
            m_NoSeatSmallBlind = -1;
            m_NoSeatBigBlind = -1;
            m_BetLimit = limit;
            for (int i = 1; i <= m_NbMaxSeats; ++i)
            {
                m_RemainingSeats.Push(m_NbMaxSeats - i);
            }
        }
        public void InitCards()
        {
            Cards = new GameCard[5];
        }
        public void SetCards(GameCard c1, GameCard c2, GameCard c3, GameCard c4, GameCard c5)
        {
            Cards = new GameCard[5] { c1, c2, c3, c4, c5 };
        }
        public void AddCards(params GameCard[] c)
        {
            int i = 0;
            for (; m_Cards[i] != null && m_Cards[i].ToString() != GameCard.NO_CARD.ToString(); ++i) ;
            for (int j = i; j < Math.Min(5, c.Length + i); ++j)
                m_Cards[j] = c[j - i];
        }
        public PlayerInfo GetPlayer(int seat)
        {
            return m_Players[seat];
        }
        public PlayerInfo GetPlayerNextTo(int seat)
        {
            for (int i = 0; i < m_NbMaxSeats; ++i)
            {
                int j = (seat + 1 + i) % m_NbMaxSeats;
                if (m_Players[j] != null)
                {
                    return m_Players[j];
                }
            }
            return m_Players[seat];
        }
        public PlayerInfo GetPlayingPlayerNextTo(int seat)
        {
            for (int i = 0; i < m_NbMaxSeats; ++i)
            {
                int j = (seat + 1 + i) % m_NbMaxSeats;
                if (m_Players[j] != null && m_Players[j].IsPlaying)
                {
                    return m_Players[j];
                }
            }
            return m_Players[seat];
        }
        private bool ContainsPlayer(PlayerInfo p)
        {
            return Players.Contains(p);
        }
        public bool ContainsPlayer(String name)
        {
            foreach (PlayerInfo p in Players)
                if (p.Name.Equals(name, StringComparison.InvariantCultureIgnoreCase))
                    return true;
            return false;
        }
        public void AddAllInCap(int val)
        {
            if (!m_AllInCaps.Contains(val))
                m_AllInCaps.Add(val);
        }
        public void AddBlindNeeded(PlayerInfo p, int amnt)
        {
            if (m_BlindNeeded.ContainsKey(p))
                m_BlindNeeded[p] = amnt;
            else
                m_BlindNeeded.Add(p, amnt);
        }
        public int GetBlindNeeded(PlayerInfo p)
        {
            if (m_BlindNeeded.ContainsKey(p))
                return m_BlindNeeded[p];
            else
                return 0;
        }

        public void InitTable()
        {
            Cards = new GameCard[5];
            NbPlayed = 0;
            PlaceButtons();
            InitPots();
        }
        public bool ForceJoinTable(PlayerInfo p, int seat)
        {
            p.IsPlaying = false;
            p.NoSeat = seat;
            m_Players[seat] = p;
            return true;
        }
        public bool JoinTable(PlayerInfo p)
        {
            if (m_RemainingSeats.Count == 0)
            {
                Console.WriteLine("Too bad: m_RemainingSeats.size() == 0");
                return false;
            }

            if (ContainsPlayer(p))
            {
                Console.WriteLine("Too bad: containsPlayer(p)");
                return false;
            }

            int seat = m_RemainingSeats.Pop();
            p.IsPlaying = false;
            p.NoSeat = seat;
            m_Players[seat] = p;
            return true;
        }
        public bool LeaveTable(PlayerInfo p)
        {
            if (!ContainsPlayer(p))
                return false;

            int seat = p.NoSeat;
            p.IsPlaying = false;
            m_Players[seat] = null;

            return true;
        }
        public void DecidePlayingPlayers()
        {
            foreach (PlayerInfo p in Players)
                if (p.CanPlay)
                {
                    p.IsPlaying = true;
                    p.IsShowingCards = false;
                }
                else
                    p.IsPlaying = false;
        }
        public void InitPots()
        {
            TotalPotAmnt = 0;
            m_Pots.Clear();
            m_AllInCaps.Clear();
            m_Pots.Add(new MoneyPot(0));
            m_CurrPotId = 0;
            NbAllIn = 0;
        }
        public void PlaceButtons()
        {
            m_NoSeatDealer = GetPlayingPlayerNextTo(m_NoSeatDealer).NoSeat;
            m_NoSeatSmallBlind = NbPlaying == 2 ? m_NoSeatDealer : GetPlayingPlayerNextTo(m_NoSeatDealer).NoSeat;
            m_NoSeatBigBlind = GetPlayingPlayerNextTo(m_NoSeatSmallBlind).NoSeat;
            m_BlindNeeded.Clear();
            m_BlindNeeded.Add(GetPlayer(m_NoSeatSmallBlind), SmallBlindAmnt);
            m_BlindNeeded.Add(GetPlayer(m_NoSeatBigBlind), BigBlindAmnt);
            m_TotalBlindNeeded = SmallBlindAmnt + BigBlindAmnt;
        }
        private void AddBet(PlayerInfo p, MoneyPot pot, int bet)
        {
            p.MoneyBetAmnt -= bet;
            pot.AddAmount(bet);
            if (bet >= 0 && (p.IsPlaying || p.IsAllIn))
            {
                pot.AttachPlayer(p);
            }
        }
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
                {
                    AddBet(p, pot, Math.Min(p.MoneyBetAmnt, cap));
                }
                currentTaken += cap;
                m_CurrPotId++;
                m_Pots.Add(new MoneyPot(m_CurrPotId));
            }

            MoneyPot curPot = m_Pots[m_CurrPotId];
            curPot.DetachAllPlayers();
            foreach (PlayerInfo p in Players)
            {
                AddBet(p, curPot, p.MoneyBetAmnt);
            }
            m_HigherBet = 0;
        }
        public void CleanPotsForWinning()
        {
            for (int i = 0; i <= m_CurrPotId; ++i)
            {
                MoneyPot pot = m_Pots[i];
                uint bestHand = 0;
                List<PlayerInfo> infos = new List<PlayerInfo>(pot.AttachedPlayers);
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
                    {
                        pot.AttachPlayer(p);
                    }
                }
            }
        }
        public bool CanRaise(PlayerInfo p)
        {
            return HigherBet < p.MoneyAmnt;
        }
        public bool CanCheck(PlayerInfo p)
        {
            return HigherBet <= p.MoneyBetAmnt;
        }
        public int MinRaiseAmnt(PlayerInfo p)
        {
            return Math.Min(CallAmnt(p) + BigBlindAmnt, MaxRaiseAmnt(p));
        }
        public int MaxRaiseAmnt(PlayerInfo p)
        {
            return p.MoneySafeAmnt;
        }
        public int CallAmnt(PlayerInfo p)
        {
            return HigherBet - p.MoneyBetAmnt;
        }
        public List<PlayerInfo> PlayingPlayersFrom(int seat)
        {
            List<PlayerInfo> list = new List<PlayerInfo>();
            for (int i = 0; i < m_NbMaxSeats; ++i)
            {
                if (m_Players[i] != null && m_Players[i].IsPlaying)
                {
                    list.Add(m_Players[i]);
                }
            }
            return list;
        }
    }
}
