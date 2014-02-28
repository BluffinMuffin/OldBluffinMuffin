using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class PlayerPlayMoneyCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gamePLAY_MONEY";

        public int Played { get; set; }

        public PlayerPlayMoneyCommand()
        {
        }

        public PlayerPlayMoneyCommand(int played)
        {
            Played = played;
        }
    }
}
