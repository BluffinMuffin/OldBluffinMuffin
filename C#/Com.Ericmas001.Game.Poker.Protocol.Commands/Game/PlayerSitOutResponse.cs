using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerSitOutResponse : AbstractJsonCommandResponse<PlayerSitOutCommand>
    {
        public bool Success { get; set; }

        public PlayerSitOutResponse()
        {
        }

        public PlayerSitOutResponse(PlayerSitOutCommand command)
            : base(command)
        {
        }
    }
}
