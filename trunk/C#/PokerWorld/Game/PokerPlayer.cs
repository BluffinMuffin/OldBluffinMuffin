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
        #endregion Public Methods
    }
}
