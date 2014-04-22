using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class SeatUpdatedCommand : AbstractJsonCommand
    {
        public SeatInfo Seat { get; set; }
    }
}
