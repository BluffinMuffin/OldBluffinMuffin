using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerJoinedCommand : AbstractJsonCommand
    {
        public string PlayerName { get; set; }
    }
}
