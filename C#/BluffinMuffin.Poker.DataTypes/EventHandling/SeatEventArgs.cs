using System;

namespace BluffinMuffin.Poker.DataTypes.EventHandling
{
    public class SeatEventArgs : EventArgs
    {
        private readonly SeatInfo m_Seat;
        public SeatInfo Seat { get { return m_Seat; } }

        public SeatEventArgs(SeatInfo s)
        {
            m_Seat = s;
        }
    }
}
