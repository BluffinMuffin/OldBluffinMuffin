using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.Commands.Lobby;
using System.Collections.Generic;

namespace BluffinMuffin.Protocol.Server
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
