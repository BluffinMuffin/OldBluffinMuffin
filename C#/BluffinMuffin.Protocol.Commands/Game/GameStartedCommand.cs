using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class GameStartedCommand : AbstractJsonCommand
    {
        public int NeededBlind { get; set; }
    }
}
