using System;
using System.Collections.Generic;
using System.Text;
using EricUtility;
using PokerWorld.Game;
using EricUtility.Networking.Commands;
using PokerWorld.Game.Enums;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Commands.Lobby.Career
{
    public class CreateCareerTableCommand : AbstractCreateTableCommand
    {
        public static string COMMAND_NAME = "lobbyCAREER_CREATE_TABLE";

        public CreateCareerTableCommand()
            : base()
        {
        }

        public CreateCareerTableCommand(string p_tableName, int p_bigBlind, int p_maxPlayers, string p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, BetEnum limit, int minPlayersToStart)
            : base(p_tableName, p_bigBlind, p_maxPlayers, p_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit, minPlayersToStart)
        {
        }

        public string EncodeResponse( int port )
        {
            return new CreateCareerTableResponse(this, port).Encode();
        }
    }
}
