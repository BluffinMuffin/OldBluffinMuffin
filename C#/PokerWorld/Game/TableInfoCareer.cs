using System;
using System.Collections.Generic;
using System.Text;
using EricUtility.Games.CardGame;
using PokerWorld.Game.Enums;

namespace PokerWorld.Game
{
    public class TableInfoCareer: TableInfo
    {

        public TableInfoCareer()
            : base()
        {
        }

        public TableInfoCareer(int nbSeats)
            : base(nbSeats)
        {
        }

        public TableInfoCareer(string name, int bigBlind, int nbSeats, BetEnum limit, int minPlayersToStart)
            : base(name, bigBlind, nbSeats, limit, minPlayersToStart)
        {
        }
    }
}
