using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        public LobbyTypeEnum[] LobbyTypes { get; set; }

        public string EncodeResponse(List<TupleTable> tables)
        {
            return Response(tables).Encode();
        }
        public ListTableResponse Response(List<TupleTable> tables)
        {
            return new ListTableResponse(this) { Tables = tables };
        }
    }
}
