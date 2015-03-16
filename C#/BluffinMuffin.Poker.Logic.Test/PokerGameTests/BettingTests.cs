using System.Linq;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Helpers;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests
{
    [TestClass]
    public class BettingTests
    {

        [TestMethod]
        public void BigUglyTest()
        {
            //start the game
            var game = GameMock.GenerateSimple2PlayersBlindsGame();
            game.Start();

            //make p1 join the game
            var p1 = new PlayerInfo("p1", 100);
            PlayerHelper.SitInGame(game, p1);

            //make p2 join the game
            var p2 = new PlayerInfo("p2", 1000);
            PlayerHelper.SitInGame(game, p2);

            //Post needed blinds
            PlayerHelper.PutBlinds(game, p1);
            PlayerHelper.PutBlinds(game, p2);

            Assert.AreEqual(GameStateEnum.Playing, game.State, "The game should now be in the playing state");
            Assert.AreEqual(RoundTypeEnum.Preflop, game.Round, "The game should now be in the preflop round");

            //Make the first player call
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer)), "The first player should be allowed to call");
            Assert.AreEqual(RoundTypeEnum.Preflop, game.Round, "The game should still be in the preflop round");

            //Make the second player call
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer)), "The second player should be allowed to call");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should now be in the flop round");

            //Make the first player check
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, 0), "The first player should be allowed to check");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should still be in the flop round");

            //Make the second player bet
            Assert.AreEqual(false, game.PlayMoney(game.Table.CurrentPlayer, game.Table.MinRaiseAmnt(game.Table.CurrentPlayer) - 1), "The player should not be able to raise under the minimum");
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.MinRaiseAmnt(game.Table.CurrentPlayer)), "The player should be able to raise with the minimum");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should still be in the flop round");

            //Make the first player come back and raise even more than he needs to
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.MinRaiseAmnt(game.Table.CurrentPlayer) + 5), "The player should be able to raise over the minimum");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should still be in the flop round");

            //Make the second player stay calm and call, but try to call less the first time
            Assert.AreEqual(false, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer) - 1), "The first player should not be allowed to play under what is needed to call");
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer)), "The first player should be allowed to play what is needed to call");
            Assert.AreEqual(RoundTypeEnum.Turn, game.Round, "The game should now be in the turn round");

            //if this is the player with less money (p1), well just check, i want the other one :)
            if (game.Table.CurrentPlayer == p1)
                game.PlayMoney(p1, 0);

            //make the second player put more than the first one can put
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, p1.MoneySafeAmnt + 10), "The second player should be allowed to play over what the other player have");

            //make the first player call it, so allin
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer) - 1), "The first player should be allowed to go all-in");

            //The pot is won, let's start over.
            if (p1.MoneySafeAmnt == 0)
                Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since the pot was won and there is only one player left with money");
            else
                Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since the pot was won and it's starting over");
        }
    }
}
