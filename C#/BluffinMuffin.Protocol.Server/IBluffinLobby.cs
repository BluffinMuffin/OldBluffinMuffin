using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Lobby;

namespace BluffinMuffin.Protocol.Server
{
    public interface IBluffinLobby
    {
        bool IsNameUsed(string name);
        void AddName(string name);
        void RemoveName(string name);
        List<TupleTable> ListTables(params LobbyTypeEnum[] lobbyTypes);
        int CreateTable(CreateTableCommand c);
    }
}
