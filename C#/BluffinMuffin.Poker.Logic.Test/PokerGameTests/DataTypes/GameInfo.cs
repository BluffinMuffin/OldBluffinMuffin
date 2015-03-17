using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.DataTypes
{
    public class GameInfo
    {
        public PokerGame Game { get; set; }
        public PlayerInfo P1 { get; set; }
        public PlayerInfo P2 { get; set; }
        public PlayerInfo P3 { get; set; }
    }
}
