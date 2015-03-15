using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class ListTableResponse : AbstractLobbyResponse<ListTableCommand>
    {
        public List<TupleTable> Tables { get; set; }

        public ListTableResponse()
        {
        }

        public ListTableResponse(ListTableCommand command)
            : base(command)
        {
        }
    }
}
