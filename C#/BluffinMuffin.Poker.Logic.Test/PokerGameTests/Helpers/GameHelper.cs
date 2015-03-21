using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Helpers
{
    public static class GameHelper
    {
        public static bool CurrentPlayerPlays(PokerGame game, int amount)
        {
            return game.PlayMoney(game.Table.CurrentPlayer, amount);
        }
        public static bool CurrentPlayerChecks(PokerGame game)
        {
            return CurrentPlayerPlays(game, 0);
        }
        public static bool CurrentPlayerFolds(PokerGame game)
        {
            return CurrentPlayerPlays(game, -1);
        }
        public static bool CurrentPlayerCalls(PokerGame game)
        {
            return CurrentPlayerPlays(game, game.Table.CallAmnt(game.Table.CurrentPlayer));
        }
    }
}
