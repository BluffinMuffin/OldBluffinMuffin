using System;
using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class TupleTable : IComparable<TupleTable>
    {
        public int IdTable { get; set; }
        public int BigBlind { get; set; }
        public int NbPlayers { get; set; }
        public LobbyActionEnum PossibleAction { get; set; }
        public TableParams Params { get; set; }

        public int CompareTo(TupleTable other)
        {
            return IdTable.CompareTo(other.IdTable);
        }
    }
}
