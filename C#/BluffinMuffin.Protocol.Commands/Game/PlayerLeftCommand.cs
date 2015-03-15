using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerLeftCommand : AbstractJsonCommand
    {
        public int PlayerPos { get; set; }
    }
}
