using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerLeftCommand : AbstractJsonCommand
    {
        public int PlayerPos { get; set; }
    }
}
