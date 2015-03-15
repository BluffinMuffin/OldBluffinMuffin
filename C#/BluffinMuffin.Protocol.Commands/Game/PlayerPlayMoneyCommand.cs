using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerPlayMoneyCommand : AbstractJsonCommand
    {
        public int Played { get; set; }
    }
}
