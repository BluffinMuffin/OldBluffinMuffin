using System.Collections.Generic;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class TableInfoCommand : AbstractJsonCommand
    {
        public TableParams Params { get; set; }
        public int TotalPotAmount { get; set; }
        public List<int> PotsAmount { get; set; }
        public List<int> BoardCardIDs { get; set; }
        public int NbPlayers { get; set; }
        public List<SeatInfo> Seats { get; set; }
        public bool GameHasStarted { get; set; }
    }
}
