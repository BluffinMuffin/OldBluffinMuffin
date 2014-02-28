using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Game
{
    public class TableClosedCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameTABLE_CLOSED";

        public TableClosedCommand()
        {
        }
    }
}
