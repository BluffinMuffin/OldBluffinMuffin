using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitInResponse : AbstractJsonCommandResponse<PlayerSitInCommand>
    {
        public int NoSeat { get; set; }

        public PlayerSitInResponse()
        {
        }

        public PlayerSitInResponse(PlayerSitInCommand command)
            : base(command)
        {
        }
    }
}
