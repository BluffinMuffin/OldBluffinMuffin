using System.Collections.Generic;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;
using BluffinMuffin.Protocol.Lobby;

namespace BluffinMuffin.Protocol.Server.DataTypes
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
