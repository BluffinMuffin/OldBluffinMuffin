using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility;

namespace PokerProtocol.Commands
{
    public class DisconnectCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return DisconnectCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "DISCONNECT";


        public DisconnectCommand(StringTokenizer argsToken)
        {
        }

        public DisconnectCommand()
        {
        }
    }
}
