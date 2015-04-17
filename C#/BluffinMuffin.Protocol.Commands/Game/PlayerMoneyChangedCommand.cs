using Com.Ericmas001.Net.Protocol.JSON;

namespace BluffinMuffin.Protocol.Commands.Game
{
    public class PlayerMoneyChangedCommand : AbstractGameCommand
    {
        public int PlayerPos { get; set; }
        public int PlayerMoney { get; set; }
    }
}
