using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace PokerWorld.Game.PokerEventArgs
{
    public class PotWonEventArgs : PlayerInfoEventArgs
    {
        private readonly int m_Id;
        private readonly int m_AmountWon;

        public int Id { get { return m_Id; } }
        public int AmountWon { get { return m_AmountWon; } }

        public PotWonEventArgs(PokerPlayer p, int id, int amntWon)
            : base(p)
        {
            m_Id = id;
            m_AmountWon = amntWon;
        }
    }
}
