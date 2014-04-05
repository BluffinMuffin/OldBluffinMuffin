using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using Newtonsoft.Json.Linq;
using EricUtility.Games.CardGame;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class PlayerInfo
    {
        public int NoSeat { get; set; }
        public string Name { get; private set; }

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
        public List<GameCard> HoleCards { get; set; }

        public PlayerInfo()
        {
            HoleCards = new List<GameCard>();

            Name = "Anonymous Player";
            NoSeat = -1;
            MoneySafeAmnt = 0;
            MoneyBetAmnt = 0;
            MoneyInitAmnt = 0;
        }

        public PlayerInfo(String name, int money)
            : this()
        {

            Name = name;
            MoneySafeAmnt = money;
            MoneyInitAmnt = money;
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
                MoneyInitAmnt = this.MoneyInitAmnt,
                MoneyBetAmnt = this.MoneyBetAmnt,
                MoneySafeAmnt = this.MoneySafeAmnt,
                HoleCards = this.HoleCards.Select(hc => new GameCard(hc.Id)).ToList()
            };
        }

    }
}
