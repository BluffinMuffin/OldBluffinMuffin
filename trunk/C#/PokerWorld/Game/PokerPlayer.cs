using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using PokerWorld.HandEvaluator;
using System.Linq;

namespace PokerWorld.Game
{
    public class PokerPlayer
    {
        #region Fields
        private readonly GameCard[] m_Cards = new GameCard[2]; // Player Cards
        private bool m_IsPlaying; // Is the player Playing ? False if Folded, AllIn or NotPlaying
        private bool m_IsAllIn; // Is the player All-in ?
        #endregion Fields

        #region Properties

        /// <summary>
        /// Player Name
        /// </summary>
        public string Name { get; private set; }
        
        /// <summary>
        /// Player position around the table
        /// </summary>
        public int NoSeat { get; set; }
        
        /// <summary>
        /// Player Cards as viewed by himself
        /// </summary>
        public GameCard[] Cards
        {
            get { return m_Cards.Select(c => (c == null || !(m_IsPlaying || m_IsAllIn)) ? GameCard.NO_CARD : c).ToArray(); }
            set
            {
                if (value != null && value.Length == 2)
                {
                    m_Cards[0] = value[0];
                    m_Cards[1] = value[1];
                }
            }
        }

        /// <summary>
        /// Player Cards as viewed by the other players
        /// </summary>
        public GameCard[] RelativeCards
        {
            get
            {
                if (!IsShowingCards)
                    return new GameCard[2] { GameCard.HIDDEN, GameCard.HIDDEN };
                return Cards;
            }
        }

        /// <summary>
        /// Initial Money Amount of the Player when he sits at the table
        /// </summary>
        public int MoneyInitAmnt { get; private set; }

        /// <summary>
        /// Current Money Amount of the player that he isn't playing with
        /// </summary>
        public int MoneySafeAmnt { get; set; }

        /// <summary>
        /// Current Money Amount of the player that he played this round
        /// </summary>
        public int MoneyBetAmnt { get; set; }

        /// <summary>
        /// Current Money Amount of the player (Safe + Bet)
        /// </summary>
        public int MoneyAmnt
        {
            get { return MoneyBetAmnt + MoneySafeAmnt; }
        }

        /// <summary>
        /// Is the player Playing ? False if Folded, AllIn or NotPlaying
        /// If set to true, IsAllIn must be false
        /// </summary>
        public bool IsPlaying
        {
            get { return m_IsPlaying; }
            set
            {
                m_IsPlaying = value;
                if (m_IsPlaying)
                    m_IsAllIn = false;
            }
        }

        /// <summary>
        /// Is the player AllIn ?
        /// If set to true, IsPlaying must be false
        /// </summary>
        public bool IsAllIn
        {
            get { return m_IsAllIn; }
            set
            {
                m_IsAllIn = value;
                if (m_IsAllIn)
                    m_IsPlaying = false;
            }
        }
        
        /// <summary>
        /// A player who was playing but disconnected is a Zombie. He will remain in place and put blinds / check / fold
        /// </summary>
        public bool IsZombie { get; set; }

        /// <summary>
        /// Montre-il ses cartes ? Vrai si showdown
        /// </summary>
        public bool IsShowingCards { get; set; }

        /// <summary>
        /// A player who can play has money and is seated !
        /// </summary>
        public bool CanPlay
        {
            get { return NoSeat >= 0 && MoneySafeAmnt > 0; }
        }

        #endregion Properties

        #region Ctors & Init
        public PokerPlayer()
        {
            Name = "Anonymous Player";
            NoSeat = -1;
            MoneySafeAmnt = 0;
            MoneyBetAmnt = 0;
            MoneyInitAmnt = 0;
        }
        public PokerPlayer(String name)
            : this()
        {
            Name = name;
        }
        public PokerPlayer(int seat)
            : this()
        {
            NoSeat = seat;
        }
        public PokerPlayer(String name, int money)
            : this(name)
        {
            MoneySafeAmnt = money;
            MoneyInitAmnt = money;
        }
        public PokerPlayer(int seat, String name, int money):
            this(name, money)
        {
            NoSeat = seat;
        }
        #endregion Ctors & Init

        #region Public Methods

        /// <summary>
        /// Put a number on the current "Hand" of the player. The we will use that number to compare who is winning !
        /// </summary>
        /// <param name="boardCards">Visible cards available to all players</param>
        /// <returns>A unsigned int that we can use to compare with another hand</returns>
        public uint EvaluateCards(GameCard[] boardCards)
        {
            if (boardCards == null || boardCards.Length != 5 || m_Cards == null || m_Cards.Length != 2)
                return 0;

            return new Hand(String.Join<GameCard>(" ", m_Cards), String.Join<GameCard>(" ", boardCards)).HandValue;
        }

        /// <summary>
        /// Check if the player has enough money to bet some amount
        /// </summary>
        public bool CanBet(int amnt)
        {
            return amnt <= MoneySafeAmnt;
        }

        /// <summary>
        /// Tries to put some money on the table
        /// </summary>
        /// <returns>True if the money has been successfully played</returns>
        public bool TryBet(int amnt)
        {
            if (!CanBet(amnt))
            {
                return false;
            }

            MoneySafeAmnt -= amnt;
            MoneyBetAmnt += amnt;
            return true;
        }
        #endregion Public Methods
    }
}
