using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;
using Newtonsoft.Json.Linq;
using PokerWorld.Game.Rules;

namespace PokerProtocol.Commands.Lobby
{
    public class CreateTableCommand : AbstractLobbyCommand
    {
        public static string COMMAND_NAME = "lobbyCREATE_TABLE";
        public GameRule GameRules { get; set; }

        public CreateTableCommand()
        {
        }

        public CreateTableCommand(GameRule rules)
        {
            GameRules = rules;
        }

        public string EncodeResponse(int id)
        {
            return new CreateTableResponse(this, id).Encode();
        }
    }
}
