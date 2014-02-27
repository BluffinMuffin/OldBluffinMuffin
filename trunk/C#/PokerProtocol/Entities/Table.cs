using System;
using System.Collections.Generic;
using System.Text;
using PokerWorld.Game;
using EricUtility;
using PokerProtocol.Entities.Enums;
using PokerWorld.Game.Enums;
using Newtonsoft.Json.Linq;

namespace PokerProtocol.Entities
{
    public abstract class Table : IComparable<Table>
    {
        public int NoPort { get; set; }
        public string TableName { get; set; }
        public int BigBlind { get; set; }
        public int NbPlayers { get; set; }
        public int NbSeats { get; set; }
        public BetEnum Limit { get; set; }
        public LobyActionEnum PossibleAction { get; set; }

        public Table(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, BetEnum limit, LobyActionEnum possibleAction)
        {
            NoPort = p_noPort;
            TableName = p_tableName;
            BigBlind = p_bigBlind;
            NbPlayers = p_nbPlayers;
            NbSeats = p_nbSeats;
            Limit = limit;
            PossibleAction = possibleAction;
        }

        public Table()
        {
        }

        public int CompareTo(Table other)
        {
            return NoPort.CompareTo(other.NoPort);
        }
    }
}
