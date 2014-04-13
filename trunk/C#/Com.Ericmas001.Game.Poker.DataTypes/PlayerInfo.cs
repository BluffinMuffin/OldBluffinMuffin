using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Com.Ericmas001;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Games;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Newtonsoft.Json;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class PlayerInfo
    {
        private List<GameCard> m_HoleCards;

        public int NoSeat { get; set; }
        public string Name { get; set; }

        /// <summary>
        /// Current Money Amount of the player that he isn't playing with
        /// </summary>
        public int MoneySafeAmnt { get; set; }

        /// <summary>
        /// Current Money Amount of the player that he played this round
        /// </summary>
        public int MoneyBetAmnt { get; set; }

        public GameCard[] HoleCards
        {
            get
            {
                if (m_HoleCards == null || m_HoleCards.Count != 2)
                    return new GameCard[] { GameCard.NO_CARD, GameCard.NO_CARD };
                return m_HoleCards.ToArray();
            }
            set
            {
                m_HoleCards = value.ToList();
            }
        }

        public PlayerStateEnum State { get; set; }

        /// <summary>
        /// Montre-il ses cartes ? Vrai si showdown
        /// </summary>
        public bool IsShowingCards { get; set; }

        public PlayerInfo()
        {
            Name = "Anonymous Player";
            NoSeat = -1;
            MoneySafeAmnt = 0;
            MoneyBetAmnt = 0;
            State = PlayerStateEnum.Zombie;
        }

        public PlayerInfo(String name, int money)
            : this()
        {

            Name = name;
            MoneySafeAmnt = money;
        }

        /// <summary>
        /// Current Money Amount of the player (Safe + Bet)
        /// </summary>
        public int MoneyAmnt
        {
            get { return MoneyBetAmnt + MoneySafeAmnt; }
        }

        public PlayerInfo Clone()
        {
            return new PlayerInfo()
            {
                NoSeat = this.NoSeat,
                Name = this.Name,
                MoneyBetAmnt = this.MoneyBetAmnt,
                MoneySafeAmnt = this.MoneySafeAmnt,
                HoleCards = this.HoleCards.Select(hc => new GameCard(hc.Id)).ToArray(),
                IsShowingCards = this.IsShowingCards,
                State = this.State,
            };
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

        /// <summary>
        /// Player Cards as viewed by himself
        /// </summary>
        [JsonIgnore]
        public GameCard[] Cards
        {
            get { return HoleCards.Select(c => (c == null || !(State >= PlayerStateEnum.AllIn)) ? GameCard.NO_CARD : c).ToArray(); }
            set
            {
                if (value != null && value.Length == 2)
                    HoleCards = value;
            }
        }

        /// <summary>
        /// Player Cards as viewed by the other players
        /// </summary>
        [JsonIgnore]
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
        [JsonIgnore]
        public bool IsPlaying
        {
            get { return State == PlayerStateEnum.Playing; }
        }

        /// <summary>
        /// Is the player AllIn ?
        /// If set to true, IsPlaying must be false
        /// </summary>
        [JsonIgnore]
        public bool IsAllIn
        {
            get { return State == PlayerStateEnum.AllIn; }
        }

        /// <summary>
        /// A player who was playing but disconnected is a Zombie. He will remain in place and put blinds / check / fold
        /// </summary>
        [JsonIgnore]
        public bool IsZombie
        {
            get { return State == PlayerStateEnum.Zombie; }
        }

        /// <summary>
        /// A player who can play has money and is seated !
        /// </summary>
        [JsonIgnore]
        public bool CanPlay
        {
            get { return NoSeat >= 0 && MoneySafeAmnt > 0; }
        }

        public override string ToString()
        {
            return Name;
        }
    }
}
