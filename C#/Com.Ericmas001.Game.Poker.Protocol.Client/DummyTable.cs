using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Games;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    class DummyTable : TableInfo
    {
        /// <summary>
        /// Sets the cards on the table
        /// </summary>
        public void SetCards(GameCard c1, GameCard c2, GameCard c3, GameCard c4, GameCard c5)
        {
            Cards = new GameCard[5] { c1, c2, c3, c4, c5 };
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
