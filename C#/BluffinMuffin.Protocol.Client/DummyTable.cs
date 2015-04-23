using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Protocol.DataTypes;
using Com.Ericmas001.Games;

namespace BluffinMuffin.Protocol.Client
{
    class DummyTable : TableInfo
    {
        /// <summary>
        /// Sets the cards on the table
        /// </summary>
        public void SetCards(GameCard c1, GameCard c2, GameCard c3, GameCard c4, GameCard c5)
        {
            Cards = new[] { c1, c2, c3, c4, c5 };
        }

        public void ClearSeat(int noSeat)
        {
            Seats[noSeat].Player = null;
        }

        public void SetSeat(SeatInfo seat)
        {
            m_Seats[seat.NoSeat] = seat;
        }
    }
}
