using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.Commands.Lobby;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinLobby
    {
        bool IsNameUsed(string name);
        void AddName(string name);
        void RemoveName(string name);
        PokerGame GetGame(int id);
        List<TupleTable> ListTables(params LobbyTypeEnum[] lobbyTypes);
        int CreateTable(CreateTableCommand c);
    }
}
