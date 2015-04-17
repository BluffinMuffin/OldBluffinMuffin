using Com.Ericmas001.Net.Protocol.JSON;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class SeatUpdatedCommand : AbstractGameCommand
    {
        public SeatInfo Seat { get; set; }
    }
}
