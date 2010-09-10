using System;
using System.Collections.Generic;
using System.Text;
using PokerProtocol.Commands.Lobby.Response;
using EricUtility;
using EricUtility.Networking.Commands;

namespace PokerProtocol.Commands.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        protected override string CommandName
        {
            get { return COMMAND_NAME; }
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
