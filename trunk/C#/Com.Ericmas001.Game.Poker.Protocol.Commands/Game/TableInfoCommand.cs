using System.Collections.Generic;
using System.Text;
using Com.Ericmas001;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using System.Linq;
using Com.Ericmas001.Games;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Commands.Game
{
    public class TableInfoCommand : AbstractJsonCommand
    {
        public static string COMMAND_NAME = "gameTABLE_INFO";

        public TableParams Params { get; set; }
        public int TotalPotAmount { get; set; }
        public List<int> PotsAmount { get; set; }
        public List<int> BoardCardIDs { get; set; }
        public int NbPlayers { get; set; }
        public List<SeatInfo> Seats { get; set; }

        public TableInfoCommand()
        {
        }
    }
}
