using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerJoinedCommand : AbstractGameCommand
    {
        public string PlayerName { get; set; }
    }
}
