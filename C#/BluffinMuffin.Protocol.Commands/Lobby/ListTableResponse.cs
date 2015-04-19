using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Protocol.Commands.Lobby
{
    public class ListTableResponse : AbstractBluffinReponse<ListTableCommand>
    {
        public List<TupleTable> Tables { get; set; }

        public ListTableResponse(ListTableCommand command)
            : base(command)
        {
        }
    }
}
