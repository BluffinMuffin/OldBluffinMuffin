using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitInCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameSIT_IN";

        public int NoSeat { get; set; }

        public string EncodeResponse(int noSeat)
        {
            return new PlayerSitInResponse(this) { NoSeat = noSeat }.Encode();
        }
    }
}
