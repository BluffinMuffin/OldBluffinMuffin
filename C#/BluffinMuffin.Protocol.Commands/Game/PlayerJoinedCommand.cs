using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerJoinedCommand : AbstractJsonCommand
    {
        public string PlayerName { get; set; }
    }
}
