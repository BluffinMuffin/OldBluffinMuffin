using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Com.Ericmas001;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class TupleSeat
    {
        public int NoSeat { get; set; }
        public PlayerInfo Player { get; set; }
        public bool IsEmpty { get; set; }
        public bool IsDealer { get; set; }
        public bool IsSmallBlind { get; set; }
        public bool IsBigBlind { get; set; }
        public bool IsCurrentPlayer { get; set; }

        public TupleSeat()
        {
            IsEmpty = true;
        }
    }
}
