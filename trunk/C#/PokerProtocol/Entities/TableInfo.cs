using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;
using PokerWorld.Game.Enums;
using Newtonsoft.Json.Linq;
using PokerWorld.Game.Rules;

namespace PokerProtocol.Entities
{
    public class TableInfo : IComparable<TableInfo>
    {
        public int IdTable { get; set; }
        public int BigBlind { get; set; }
        public int NbPlayers { get; set; }
        public LobbyActionEnum PossibleAction { get; set; }
        public GameRule Rules { get; set; }

        public TableInfo(int idTable, GameRule rules, int nbPlayers, LobbyActionEnum possibleAction)
        {
            IdTable = idTable;
            Rules = rules;
            NbPlayers = nbPlayers;
            PossibleAction = possibleAction;
        }

        public TableInfo()
        {
        }

        public int CompareTo(TableInfo other)
        {
            return IdTable.CompareTo(other.IdTable);
        }
    }
}
