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
        public void AfterBlindsFirstPlayerCanCall()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();

            Assert.AreEqual(true, GameHelper.CurrentPlayerCalls(nfo.Game), "The first player should be allowed to call");
        }

        [TestMethod]
        public void AfterFirstPlayerCallSecondPlayerCanCall()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            GameHelper.CurrentPlayerCalls(nfo.Game);

            Assert.AreEqual(true, GameHelper.CurrentPlayerCalls(nfo.Game), "The second player should be allowed to call");  
        }

        [TestMethod]
        public void AtStartOfBettingFirstPlayerChecks()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();

            Assert.AreEqual(true, GameHelper.CurrentPlayerChecks(nfo.Game), "The first player should be allowed to call");
        }

        [TestMethod]
        public void AtStartOfBettingFirstPlayerBetsUnderMinimum()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();

            Assert.AreEqual(false, GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer) - 1), "The player should not be able to raise under the minimum");
        }

        [TestMethod]
        public void AtStartOfBettingFirstPlayerBetsMinimum()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerChecks(nfo.Game);

            Assert.AreEqual(true, GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer)), "The player should be able to raise with the minimum");
        }

        [TestMethod]
        public void AtStartOfBettingFirstPlayerBetsOverMinimum()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerChecks(nfo.Game);

            Assert.AreEqual(true, GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer) + 1), "The player should be able to raise with more than the minimum");
        }

        [TestMethod]
        public void AfterPlayerBetShouldNotBeAbleToCheck()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerChecks(nfo.Game);

            Assert.AreEqual(false, GameHelper.CurrentPlayerChecks(nfo.Game), "The player should not be able to check after a bet");
        }


        [TestMethod]
        public void BigUglyTest()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerChecks(nfo.Game);
            var game = nfo.Game;

            //if this is the player with less money (p1), well just check, i want the other one :)
            if (game.Table.CurrentPlayer == nfo.P1)
                game.PlayMoney(nfo.P1, 0);

            //make the second player put more than the first one can put
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, nfo.P1.MoneySafeAmnt + 10), "The second player should be allowed to play over what the other player have");

            //make the first player call it, so allin
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer) - 1), "The first player should be allowed to go all-in");

            //The pot is won, let's start over.
            if (nfo.P1.MoneySafeAmnt == 0)
                Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since the pot was won and there is only one player left with money");
            else
                Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since the pot was won and it's starting over");
        }
    }
}
