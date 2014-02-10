using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using PokerWorld.Game.Enums;

namespace PokerWorld.Game
{
    public class TableInfoTraining: TableInfo
    {
        private readonly int m_StartingMoney = 1500;

        public int StartingMoney
        {
            get { return m_StartingMoney; }
        }

        public TableInfoTraining()
            : base()
        {
        }

        public TableInfoTraining(int nbSeats)
            : base(nbSeats)
        {
        }

        public TableInfoTraining(string name, int bigBlind, int nbSeats, BetEnum limit, int startingMoney) : base(name,  bigBlind,  nbSeats,  limit)
        {
            m_StartingMoney = startingMoney;
        }
    }
}
