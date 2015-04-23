using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Protocol.Game
{
    public class SeatUpdatedCommand : AbstractGameCommand
    {
        public SeatInfo Seat { get; set; }
    }
}
