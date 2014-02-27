using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using EricUtility.Networking.Commands;
using System.Globalization;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class GetUserResponse : AbstractLobbyResponse<GetUserCommand>
    {
        public static string COMMAND_NAME = "lobbyCAREER_GET_USER_RESPONSE";

        public string Email { get; private set; }
        public string DisplayName { get; private set; }
        public double Money { get; private set; }

        public GetUserResponse(JObject obj)
            : base(new GetUserCommand((JObject)obj["Command"]))
        {
            Email = (string)obj["Email"];
            DisplayName = (string)obj["DisplayName"];
            Money = (double)obj["Money"];
        }

        public GetUserResponse(GetUserCommand command, string mail, string display, double money)
            : base(command)
        {
            Email = mail;
            DisplayName = display;
            Money = money;
        }
    }
}
