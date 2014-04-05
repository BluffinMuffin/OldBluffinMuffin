using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerJoinedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_JOINED";

        public int PlayerPos { get; set; }
        public int PlayerMoney { get; set; }
        public string PlayerName { get; set; }
    }
}
