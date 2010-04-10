using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;

namespace PokerProtocol.Commands.Game
{
    public class GameEndedCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return GameEndedCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gameENDED";


        public GameEndedCommand(StringTokenizer argsToken)
        {
        }

        public GameEndedCommand()
        {
        }
    }
}
