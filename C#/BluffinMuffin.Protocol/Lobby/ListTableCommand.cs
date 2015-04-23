using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        public LobbyTypeEnum[] LobbyTypes { get; set; }

        public ListTableResponse Response(List<TupleTable> tables)
        {
            return new ListTableResponse(this) { Tables = tables };
        }
    }
}
