using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class GameEndedCommand : AbstractCommand
    {
        public static string COMMAND_NAME = "gameENDED";

        public GameEndedCommand(StringTokenizer argsToken)
        {
        }

        public GameEndedCommand()
        {
        }
    }
}
