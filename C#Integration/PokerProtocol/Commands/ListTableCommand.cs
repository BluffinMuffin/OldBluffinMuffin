using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PokerProtocol.Commands.Lobby.Response;
using EricUtility;

namespace PokerProtocol.Commands.Lobby
{
    public class ListTableCommand : AbstractCommand
    {
        protected override string CommandName
        {
            get { return ListTableCommand.COMMAND_NAME; }
        }
        public static string COMMAND_NAME = "lobbyLIST_TABLES";


        public ListTableCommand(StringTokenizer argsToken)
        {
        }

        public ListTableCommand()
        {
        }

        public string EncodeResponse(List<TupleTableInfo> tables)
        {
            return new ListTableResponse(this, tables).Encode();
        }
    }
}
