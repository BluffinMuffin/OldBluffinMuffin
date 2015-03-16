using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Parameters;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Helpers;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks
{
    public static class PlayerMock
    {
        public static PlayerInfo GenerateP1()
        {
            return new PlayerInfo("p1", 5000);
        }
        public static PlayerInfo GenerateP2()
        {
            return new PlayerInfo("p2", 5000);
        }
        public static PlayerInfo GenerateP2Poor()
        {
            return new PlayerInfo("p2", 5000);
        }
        public static PlayerInfo GenerateP3()
        {
            return new PlayerInfo("p3", 5000);
        }
        public static PlayerInfo GenerateP1Seated(PokerGame game)
        {
            return PlayerHelper.SitInGame(game, GenerateP1());
        }
        public static PlayerInfo GenerateP2Seated(PokerGame game)
        {
            return PlayerHelper.SitInGame(game, GenerateP2());
        }
        public static PlayerInfo GenerateP2PoorSeated(PokerGame game)
        {
            return PlayerHelper.SitInGame(game, GenerateP2Poor());
        }
    }
}
