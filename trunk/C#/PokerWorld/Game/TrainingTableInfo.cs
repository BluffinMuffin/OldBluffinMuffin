using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;

namespace PokerWorld.Game
{
    public class TrainingTableInfo: TableInfo
    {
        private readonly int m_StartingMoney = 1500;

        public int StartingMoney
        {
            get { return m_StartingMoney; }
        }

        public TrainingTableInfo()
            : base()
        {
        }

        public TrainingTableInfo(int nbSeats)
            : base(nbSeats)
        {
        }

        public TrainingTableInfo(string name, int bigBlind, int nbSeats, TypeBet limit, int startingMoney) : base(name,  bigBlind,  nbSeats,  limit)
        {
            m_StartingMoney = startingMoney;
        }
    }
}
