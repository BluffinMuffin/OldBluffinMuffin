using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerLeftCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
    }
}
