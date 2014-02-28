using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerLeftCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAYER_LEFT";

        public int PlayerPos { get; set; }

        public PlayerLeftCommand()
        {
        }

        public PlayerLeftCommand(int pos)
        {
            PlayerPos = pos;
        }
    }
}
