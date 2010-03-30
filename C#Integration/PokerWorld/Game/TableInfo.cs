using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Games;

namespace PokerWorld.Game
{
    //TODO!!!
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
            for (; m_Cards[i] != null && m_Cards[i].ToString() != GameCard.NO_CARD_STRING; ++i) ;
            for (int j = i; j < Math.Min(5, c.Length + i); ++j)
                m_Cards[j] = c[j - i];
        }
    }
}
