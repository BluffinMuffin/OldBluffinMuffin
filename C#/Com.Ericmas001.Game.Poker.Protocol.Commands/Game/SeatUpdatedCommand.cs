using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class SeatUpdatedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameSEAT_UPDATED";

        public TupleSeat Seat { get; set; }
    }
}
