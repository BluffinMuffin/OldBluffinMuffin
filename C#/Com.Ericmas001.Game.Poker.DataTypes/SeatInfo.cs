using System.Linq;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
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
                return Attributes.ToArray();
            }
            set
            {
                value.ToList().ForEach(x => Attributes.Add(x));
            }
        }

        public SeatInfo()
        {
            Attributes = new ConcurrentList<SeatAttributeEnum>();
        }

        public SeatInfo Clone()
        {
            if (IsEmpty)
                return new SeatInfo();
            return new SeatInfo()
            {
                Player = Player.Clone(),
                NoSeat = NoSeat,
                SerializableAttributes = SerializableAttributes,
            };
        }
    }
}
