using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Helpers
{
    public static class PlayerHelper
    {
        public static PlayerInfo SitInGame(PokerGame game, PlayerInfo p1)
        {
            game.JoinGame(p1);
            game.GameTable.AskToSitIn(p1, -1);
            game.SitIn(p1);
            return p1;
        }
        public static void PutBlinds(PokerGame game, PlayerInfo p)
        {
            var b = game.GameTable.GetBlindNeeded(p);
            game.PlayMoney(p, b);
        }
    }
}
