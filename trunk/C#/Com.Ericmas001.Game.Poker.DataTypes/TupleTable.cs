using System;
using Com.Ericmas001.Game.Poker.DataTypes.Annotations;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.DataTypes
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
