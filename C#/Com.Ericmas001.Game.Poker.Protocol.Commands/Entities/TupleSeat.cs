using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using EricUtility;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Entities
{
    public class TupleSeat
    {
        public int NoSeat { get; set; }
        public PlayerInfo Player { get; set; }
        public bool IsEmpty { get; set; } //SeatInfo
        public bool IsDealer { get; set; } //SeatInfo
        public bool IsSmallBlind { get; set; } //SeatInfo
        public bool IsBigBlind { get; set; } //SeatInfo
        public bool IsCurrentPlayer { get; set; } //SeatInfo
        public bool IsPlaying { get; set; } //SeatInfo

        public TupleSeat()
        {
            IsEmpty = true;
        }
    }
}
