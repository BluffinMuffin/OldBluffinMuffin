using System.Collections.Generic;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
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
