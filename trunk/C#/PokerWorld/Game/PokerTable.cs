﻿using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using EricUtility;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.HandEval;

namespace PokerWorld.Game
{
    public class PokerTable : TableInfo
    {
        #region Fields
        protected readonly Stack<int> m_RemainingSeats = new Stack<int>(); // LIFO with the unused seats
        protected readonly List<int> m_AllInCaps = new List<int>(); // All the distincts ALL_IN CAPS of the ROUND
        protected readonly Dictionary<PlayerInfo, int> m_BlindNeeded = new Dictionary<PlayerInfo, int>();
        protected int m_CurrPotId;
        #endregion Fields

        #region Properties

        /// <summary>
        /// Total amount of money still needed as Blinds for the game to start
        /// </summary>
        public int TotalBlindNeeded { get { return m_BlindNeeded.Values.Sum(); } }

        #endregion Properties

        #region Ctors & Init
        public PokerTable()
            : base()
        {
        }

        public PokerTable(GameRule rules)
            :base(rules)
        {
            Enumerable.Range(1, rules.MaxPlayers).ToList().ForEach(i => m_RemainingSeats.Push(rules.MaxPlayers - i));
        }

        public override void InitTable()
        {
            base.InitTable();
            m_AllInCaps.Clear();
            m_CurrPotId = 0;
        }
        #endregion

        #region Public Methods

        /// <summary>
        /// Add cards to the board
        /// </summary>
        public void AddCards(params GameCard[] c)
        {
            int firstUnused =  Enumerable.Range(0,m_Cards.Length).First(i => m_Cards[i] == null || m_Cards[i].ToString() == GameCard.NO_CARD.ToString());
            for (int j = firstUnused; j < Math.Min(5, c.Length + firstUnused); ++j)
                m_Cards[j] = c[j - firstUnused];
        }

        /// <summary>
        /// Add an AllInCap that will be used when splitting the pot
        /// </summary>
        public void AddAllInCap(int val)
        {
            if (!m_AllInCaps.Contains(val))
                m_AllInCaps.Add(val);
        }

        /// <summary>
        /// Sets how much money is still needed from a specific player as Blind
        /// </summary>
        public void SetBlindNeeded(PlayerInfo p, int amnt)
        {
            if (m_BlindNeeded.ContainsKey(p))
                m_BlindNeeded[p] = amnt;
            else
                m_BlindNeeded.Add(p, amnt);
        }

        /// <summary>
        /// How much money a player needs to put as Blind
        /// </summary>
        public int GetBlindNeeded(PlayerInfo p)
        {
            if (m_BlindNeeded.ContainsKey(p))
                return m_BlindNeeded[p];
            else
                return 0;
        }

        /// <summary>
        /// When a player joined the table
        /// </summary>
        public bool JoinTable(PlayerInfo p)
        {
            if (m_RemainingSeats.Count == 0)
            {
                LogManager.Log(LogLevel.Error, "TableInfo.JoinTable", "Not enough seats to join!");
                return false;
            }

            if (ContainsPlayer(p))
            {
                LogManager.Log(LogLevel.Error, "TableInfo.JoinTable", "Already someone with the same name!");
                return false;
            }

            ForceJoinTable(p,  m_RemainingSeats.Pop());
            return true;
        }

        /// <summary>
        /// When a player leaves the table
        /// </summary>
        public override bool LeaveTable(PlayerInfo p)
        {
            if (!ContainsPlayer(p))
                return false;

            int seat = p.NoSeat;

            if (!base.LeaveTable(p))
                return false;

            m_RemainingSeats.Push(seat);

            return true;
        }


        /// <summary>
        /// Put a number on the current "Hand" of a player. The we will use that number to compare who is winning !
        /// </summary>
        /// <param name="playerCards">Player cards</param>
        /// <returns>A unsigned int that we can use to compare with another hand</returns>
        public uint EvaluateCards(List<GameCard> playerCards)
        {
            if (Cards == null || Cards.Length != 5 || playerCards == null || playerCards.Count != 2)
                return 0;

            return new Hand(String.Join<GameCard>(" ", playerCards), String.Join<GameCard>(" ", Cards)).HandValue;
        }

        /// <summary>
        /// At the end of a Round, it's time to separate all the money into one or more pots of money (Depending on when a player wen All-In)
        /// For every cap, we take money from each player that still have money in front of them
        /// </summary>
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
                    AddBet(p, pot, Math.Min(p.MoneyBetAmnt, cap));

                currentTaken += cap;
                m_CurrPotId++;
                m_Pots.Add(new MoneyPot(m_CurrPotId));
            }

            MoneyPot curPot = m_Pots[m_CurrPotId];
            curPot.DetachAllPlayers();
            foreach (PlayerInfo p in Players)
                AddBet(p, curPot, p.MoneyBetAmnt);

            HigherBet = 0;
        }

        /// <summary>
        /// Detach all the players that are not winning this pot
        /// </summary>
        public void CleanPotsForWinning()
        {
            for (int i = 0; i <= m_CurrPotId; ++i)
            {
                MoneyPot pot = m_Pots[i];
                uint bestHand = 0;
                List<PlayerInfo> infos = new List<PlayerInfo>(pot.AttachedPlayers);

                //If there is more than one player attach to the pot, we need to choose who will split it !
                if (infos.Count > 1)
                {
                    foreach (PlayerInfo p in infos)
                    {
                        uint handValue = EvaluateCards(GetPlayer(p.NoSeat).HoleCards);
                        if (handValue > bestHand)
                        {
                            pot.DetachAllPlayers();
                            pot.AttachPlayer(p);
                            bestHand = handValue;
                        }
                        else if (handValue == bestHand)
                            pot.AttachPlayer(p);
                    }
                }
            }
        }
        #endregion Public Methods

        #region Private Methods

        private void AddBet(PlayerInfo p, MoneyPot pot, int bet)
        {
            p.MoneyBetAmnt -= bet;
            pot.AddAmount(bet);

            if (bet >= 0 && (p.IsPlaying || p.IsAllIn))
                pot.AttachPlayer(p);
        }
        protected override void PlaceButtons()
        {
            base.PlaceButtons();
            m_BlindNeeded.Clear();
            m_BlindNeeded.Add(GetPlayer(NoSeatSmallBlind), SmallBlindAmnt);
            m_BlindNeeded.Add(GetPlayer(NoSeatBigBlind), Rules.BlindAmount);
        }
        #endregion Private Methods
    }
}