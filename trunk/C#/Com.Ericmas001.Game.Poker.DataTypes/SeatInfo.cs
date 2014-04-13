using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Com.Ericmas001;
using Newtonsoft.Json.Linq;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Collections.Concurrent;
using Com.Ericmas001.Collections;
using Newtonsoft.Json;

namespace Com.Ericmas001.Game.Poker.DataTypes
{
    public class SeatInfo
    {
        public bool IsEmpty { get { return Player == null; } }

        public int NoSeat { get; set; }
        public PlayerInfo Player { get; set; }

        [JsonIgnore]
        public ConcurrentList<SeatAttributeEnum> Attributes
        {
            get;
            set;
        }

        public SeatAttributeEnum[] SerializableAttributes
        {
            get
            {
                return Attributes.ToList().ToArray();
            }
            set
            {
                value.ToList().ForEach(x => Attributes.Add(x));
            }
        }

        public bool IsSmallBlind { get; set; }
        public bool IsBigBlind { get; set; }

        public SeatInfo()
        {
            Attributes = new ConcurrentList<SeatAttributeEnum>();
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
                    IsBigBlind = this.IsBigBlind,
                    Attributes = new ConcurrentList<SeatAttributeEnum>(this.Attributes),
                };
        }
    }
}
