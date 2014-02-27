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
    public class TableTraining : Table
    {
        public TableTraining(int p_noPort, String p_tableName, int p_bigBlind, int p_nbPlayers, int p_nbSeats, BetEnum limit, LobyActionEnum possibleAction)
        : base( p_noPort,  p_tableName,  p_bigBlind,  p_nbPlayers,  p_nbSeats,  limit,  possibleAction)
        {
        }

        public TableTraining()
            :base()
        {
        }
    }
}
