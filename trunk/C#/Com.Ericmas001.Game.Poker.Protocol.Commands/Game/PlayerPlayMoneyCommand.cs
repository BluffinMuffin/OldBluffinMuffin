using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerPlayMoneyCommand : AbstractJsonCommand
    {
        public int Played { get; set; }
    }
}
