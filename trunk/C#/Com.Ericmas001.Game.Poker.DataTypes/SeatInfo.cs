using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Com.Ericmas001;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class SeatInfo
    {
        public bool IsEmpty { get { return Player == null; } }


        public int NoSeat { get; set; }
        public PlayerInfo Player { get; set; }
        public HashSet<SeatAttributeEnum> Attributes { get; set; }
        public bool IsSmallBlind { get; set; }
        public bool IsBigBlind { get; set; }
        public bool IsCurrentPlayer { get; set; }

        public SeatInfo()
        {
            Attributes = new HashSet<SeatAttributeEnum>();
        }

        public SeatInfo Clone()
        {
            if (IsEmpty)
                return new SeatInfo();
            else
                return new SeatInfo()
                {
                    Player = this.Player.Clone(),
                    NoSeat = this.NoSeat,
                    IsSmallBlind = this.IsSmallBlind,
                    IsCurrentPlayer = this.IsCurrentPlayer,
                    IsBigBlind = this.IsBigBlind,
                    Attributes = new HashSet<SeatAttributeEnum>(this.Attributes),
                };
        }
    }
}
