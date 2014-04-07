using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System;
using System.Collections.Generic;
using System.Text;

namespace Com.Ericmas001.Game.Poker.DataTypes.EventHandling
{
    public class SeatEventArgs : EventArgs
    {
        private readonly TupleSeat m_Seat;
        public TupleSeat Seat { get { return m_Seat; } }

        public SeatEventArgs(TupleSeat s)
        {
            m_Seat = s;
        }
    }
}
