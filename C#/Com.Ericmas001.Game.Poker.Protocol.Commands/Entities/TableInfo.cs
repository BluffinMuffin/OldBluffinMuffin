using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;
using PokerWorld.Game.Enums;
using Newtonsoft.Json.Linq;
using PokerWorld.Game.Rules;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Entities
{
    public class TableInfo : IComparable<TableInfo>
    {
        public int IdTable { get; set; }
        public int BigBlind { get; set; }
        public int NbPlayers { get; set; }
        public LobbyActionEnum PossibleAction { get; set; }
        public GameRule Rules { get; set; }

        public int CompareTo(TableInfo other)
        {
            return IdTable.CompareTo(other.IdTable);
        }
    }
}
