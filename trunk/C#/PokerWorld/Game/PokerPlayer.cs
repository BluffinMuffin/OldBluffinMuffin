using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using System.Linq;
using Com.Ericmas001.Game.Poker.HandEval;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace PokerWorld.Game
{
    public class PokerPlayer
    {
        #region Properties

        public PlayerInfo Info { get; private set; }
        
        /// <summary>
        /// Player Cards as viewed by himself
        /// </summary>
        public GameCard[] Cards
        {
            get { return Info.HoleCards.Select(c => (c == null || !(Info.State >= PlayerStateEnum.AllIn)) ? GameCard.NO_CARD : c).ToArray(); }
            set
            {
                if (value != null && value.Length == 2)
                    Info.HoleCards = value.ToList();
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
        /// Is the player Playing ? False if Folded, AllIn or NotPlaying
        /// If set to true, IsAllIn must be false
        /// </summary>
        public bool IsPlaying
        {
            get { return Info.State == PlayerStateEnum.Playing; }
            set { Info.State = value ? PlayerStateEnum.Playing : PlayerStateEnum.SitIn; }
        }

        /// <summary>
        /// Is the player AllIn ?
        /// If set to true, IsPlaying must be false
        /// </summary>
        public bool IsAllIn
        {
            get { return Info.State == PlayerStateEnum.AllIn; }
            set { Info.State = value ? PlayerStateEnum.AllIn : PlayerStateEnum.SitIn; }
        }
        
        /// <summary>
        /// A player who was playing but disconnected is a Zombie. He will remain in place and put blinds / check / fold
        /// </summary>
        public bool IsZombie
        {
            get { return Info.State == PlayerStateEnum.Zombie; }
            set { Info.State = value ? PlayerStateEnum.Zombie : PlayerStateEnum.SitIn; }
        }

        /// <summary>
        /// Montre-il ses cartes ? Vrai si showdown
        /// </summary>
        public bool IsShowingCards { get; set; }

        /// <summary>
        /// A player who can play has money and is seated !
        /// </summary>
        public bool CanPlay
        {
            get { return Info.NoSeat >= 0 && Info.MoneySafeAmnt > 0; }
        }

        #endregion Properties

        #region Ctors & Init
        public PokerPlayer(PlayerInfo info)
        {
            Info = info;
        }
        public PokerPlayer()
            : this(new PlayerInfo())
        {
        }
        public PokerPlayer(String name, int money)
            : this(new PlayerInfo(name, money))
        {
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
            if (boardCards == null || boardCards.Length != 5 || Info.HoleCards == null || Info.HoleCards.Count != 2)
                return 0;

            return new Hand(String.Join<GameCard>(" ", Info.HoleCards), String.Join<GameCard>(" ", boardCards)).HandValue;
        }

        /// <summary>
        /// Check if the player has enough money to bet some amount
        /// </summary>
        public bool CanBet(int amnt)
        {
            return amnt <= Info.MoneySafeAmnt;
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

            Info.MoneySafeAmnt -= amnt;
            Info.MoneyBetAmnt += amnt;
            return true;
        }
        #endregion Public Methods
    }
}
