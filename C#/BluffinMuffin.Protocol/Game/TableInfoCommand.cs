using System.Collections.Generic;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Protocol.Game
{
    public class TableInfoCommand : AbstractGameCommand
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
