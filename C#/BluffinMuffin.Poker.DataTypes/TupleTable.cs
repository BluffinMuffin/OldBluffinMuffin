using System;
using BluffinMuffin.Poker.DataTypes.Annotations;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Poker.DataTypes
{
    public class TupleTable : IComparable<TupleTable>
    {
        public int IdTable { get; set; }
        public int BigBlind { get; [UsedImplicitly] set; }
        public int NbPlayers { get; set; }
        public LobbyActionEnum PossibleAction { [UsedImplicitly] get; set; }
        public TableParams Params { get; set; }

        public int CompareTo(TupleTable other)
        {
            return IdTable.CompareTo(other.IdTable);
        }
    }
}
