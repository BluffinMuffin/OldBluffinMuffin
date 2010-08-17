using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class TableClosedCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "gameTABLE_CLOSED";


        public TableClosedCommand(StringTokenizer argsToken)
        {
        }

        public TableClosedCommand()
        {
        }
    }
}
