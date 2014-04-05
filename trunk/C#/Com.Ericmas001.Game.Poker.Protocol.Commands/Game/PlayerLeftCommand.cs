using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class PlayerLeftCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_LEFT";

        public int PlayerPos { get; set; }
    }
}
