using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocols.Test.DataTypes
{
    public static class SeatInfoMock
    {
        public static List<SeatInfo> AllSeats()
        {
            return new List<SeatInfo>()
            {
                SeatSix(),
                SeatSeven()
            };
        }
        public static SeatInfo SeatSix()
        {
            return new SeatInfo()
            {
                NoSeat = 6,
                Player = PlayerInfoMock.Diego(),
                SerializableAttributes = new[] { SeatAttributeEnum.Dealer  }
            };
        }
        public static SeatInfo SeatSeven()
        {
            return new SeatInfo()
            {
                NoSeat = 7,
                Player = PlayerInfoMock.Dora(),
                SerializableAttributes = new[] { SeatAttributeEnum.CurrentPlayer, SeatAttributeEnum.BigBlind }
            };
        }
    }
}
