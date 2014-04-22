using System.Collections.Generic;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Lobby
{
    public class ListTableCommand : AbstractLobbyCommand
    {
        public LobbyTypeEnum[] LobbyTypes { get; set; }

        public string EncodeResponse(List<TupleTable> tables)
        {
            return new ListTableResponse(this) { Tables = tables }.Encode();
        }
    }
}
