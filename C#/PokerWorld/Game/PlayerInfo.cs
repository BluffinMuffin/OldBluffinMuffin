using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Games.CardGame;
using PokerWorld.HandEvaluator;

namespace PokerWorld.Game
{
    public class PlayerInfo
    {
        // INFO
        private String m_Name; // Nom du joueur
        private int m_NoSeat; // Position du joueur autour de la table
        
        // CARDS
        private readonly GameCard[] m_Cards = new GameCard[2]; // Cartes du joueur
        
        // MONEY
        private int m_MoneyInitAmnt; // Argent du joueur au moment ou il s'installe a la table
        private int m_MoneySafeAmnt; // Argent du joueur qu'il a en sa pocession, non-jouee
        private int m_MoneyBetAmnt; // Argent du joueur qu'il a jouee depuis le debut de la round
        
        // STATES
        private bool m_IsPlaying; // Est-il en train de jouer ? Faux si Folded, AllIn or NotPlaying
        private bool m_IsAllIn; // Est-il All-in ? Vrai si All-in
        private bool m_IsShowingCards; // Montre-il ses cartes ? Vrai si showdown
        private bool m_Zombie; // Est-ce que le vrai player a quitté la partie ? Vrai si zombie

        public string Name
        {
            get { return m_Name; }
        }
        public int NoSeat
        {
            get { return m_NoSeat; }
            set { m_NoSeat = value; }
        }
        public GameCard[] Cards
        {
            get
            {
                GameCard[] cards = new GameCard[2] { m_Cards[0], m_Cards[1] };
                for (int i = 0; i < 2; ++i)
                {
                    if (cards[i] == null || !(m_IsPlaying || m_IsAllIn))
                    {
                        cards[i] = GameCard.NO_CARD;
                    }
                }

                return cards;
            }
            set
            {
                if (value != null && value.Length == 2)
                {
                    m_Cards[0] = value[0];
                    m_Cards[1] = value[1];
                }
            }
        }
        public GameCard[] RelativeCards
        {
            get
            {
                if (!m_IsShowingCards)
                    return new GameCard[2] { GameCard.HIDDEN, GameCard.HIDDEN };
                return Cards;
            }
        }
        public int MoneyInitAmnt
        {
            get { return m_MoneyInitAmnt; }
        }
        public int MoneySafeAmnt
        {
            get { return m_MoneySafeAmnt; }
            set { m_MoneySafeAmnt = value; }
        }
        public int MoneyBetAmnt
        {
            get { return m_MoneyBetAmnt; }
            set { m_MoneyBetAmnt = value; }
        }
        public int MoneyAmnt
        {
            get { return m_MoneyBetAmnt + m_MoneySafeAmnt; }
        }
        public bool IsPlaying
        {
            get { return m_IsPlaying; }
            set
            {
                m_IsPlaying = value;
                m_IsAllIn = false;
            }
        }
        public bool IsAllIn
        {
            get { return m_IsAllIn; }
            set
            {
                m_IsPlaying = false;
                m_IsAllIn = value;
            }
        }
        public bool IsZombie
        {
            get { return m_Zombie; }
            set { m_Zombie = value; }
        }
        public bool IsShowingCards
        {
            get { return m_IsShowingCards; }
            set { m_IsShowingCards = value; }
        }
        public bool CanPlay
        {
            get { return m_NoSeat >= 0 && m_MoneySafeAmnt > 0; }
        }

        public PlayerInfo()
        {
            m_Name = "Anonymous Player";
            m_NoSeat = -1;
            m_MoneySafeAmnt = 0;
            m_MoneyBetAmnt = 0;
            m_MoneyInitAmnt = 0;
        }
        public PlayerInfo(String name)
            : this()
        {
            m_Name = name;
        }
        public PlayerInfo(int seat)
            : this()
        {
            m_NoSeat = seat;
        }
        public PlayerInfo(String name, int money)
            : this(name)
        {
            m_MoneySafeAmnt = money;
            m_MoneyInitAmnt = money;
        }
        public PlayerInfo(int seat, String name, int money):
            this(name, money)
        {
            m_NoSeat = seat;
        }

        public uint EvaluateCards(GameCard[] boardCards)
        {
            if (boardCards == null || boardCards.Length != 5 || m_Cards == null || m_Cards.Length != 2)
                return 0;
            string pocket = String.Format("{0} {1}",m_Cards[0],m_Cards[1]);
            string board = String.Format("{0} {1} {2} {3} {4}", boardCards[0], boardCards[1], boardCards[2], boardCards[3], boardCards[4]);

            return new Hand(pocket, board).HandValue;
        }

        public bool CanBet(int amnt)
        {
            return amnt <= m_MoneySafeAmnt;
        }

        public bool TryBet(int amnt)
        {
            if (!CanBet(amnt))
            {
                return false;
            }
            m_MoneySafeAmnt -= amnt;
            m_MoneyBetAmnt += amnt;
            return true;
        }
    }
}
