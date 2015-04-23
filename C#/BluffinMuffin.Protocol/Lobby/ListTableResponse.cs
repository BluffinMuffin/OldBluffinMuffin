using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Protocol.Lobby
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
