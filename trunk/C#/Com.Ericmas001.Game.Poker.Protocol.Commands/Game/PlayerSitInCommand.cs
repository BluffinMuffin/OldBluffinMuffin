using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitInCommand : AbstractJsonCommand
    {
        public int NoSeat { get; set; }
        public int MoneyAmount { get; set; }

        public string EncodeResponse(int noSeat)
        {
            return new PlayerSitInResponse(this) { NoSeat = noSeat }.Encode();
        }
    }
}
