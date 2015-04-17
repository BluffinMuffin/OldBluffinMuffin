using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerSitInCommand : AbstractGameCommand
    {
        public int NoSeat { get; set; }
        public int MoneyAmount { get; set; }

        public string EncodeResponse(int noSeat)
        {
            return new PlayerSitInResponse(this) { NoSeat = noSeat }.Encode();
        }
    }
}
