using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using Newtonsoft.Json.Linq;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby.Career
{
    public class GetUserCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_GET_USER";

        public string Username { get; set; }

        public string EncodeResponse(string mail, string display, double money)
        {
            return new GetUserResponse(this)
            {
                Email = mail,
                DisplayName = display,
                Money = money,
            }.Encode();
        }
    }
}
