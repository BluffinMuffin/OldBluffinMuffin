using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CheckDisplayExistResponse : AbstractLobbyResponse<CheckDisplayExistCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_CHECK_DISPLAY_EXIST_RESPONSE";
        public bool Exist { get; set; }

        public CheckDisplayExistResponse()
            : base()
        {
        }

        public CheckDisplayExistResponse(CheckDisplayExistCommand command, bool exist)
            : base(command)
        {
            Exist = exist;
        }
    }
}
