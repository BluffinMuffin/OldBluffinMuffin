using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerWonPotCommand : AbstractJsonCommand
    {
        public int PlayerPos { get; set; }
        public int PotId { get; set; }
        public int Shared { get; set; }
        public int PlayerMoney { get; set; }
    }
}
