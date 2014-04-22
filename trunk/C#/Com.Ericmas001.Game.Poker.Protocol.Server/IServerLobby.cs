using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.Logic;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby;
using System.Collections.Generic;

namespace Com.Ericmas001.Game.Poker.Protocol.Server
{
    public interface IServerLobby
    {
        void AddName(string name);
        int CreateTable(CreateTableCommand c);
        PokerGame GetGame(int id);
        List<TupleTable> ListTables(params LobbyTypeEnum[] lobbyTypes);
        bool NameUsed(string name);
        void RemoveName(string name);
    }
}
