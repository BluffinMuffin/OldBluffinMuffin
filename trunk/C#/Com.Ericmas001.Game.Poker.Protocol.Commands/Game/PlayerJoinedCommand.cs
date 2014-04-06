using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerJoinedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_JOINED";

        public int PlayerPos { get; set; }
        public int PlayerMoney { get; set; }
        public string PlayerName { get; set; }
    }
}
