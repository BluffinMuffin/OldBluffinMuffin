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
        public void AllIn()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            var game = nfo.Game;
            var poor = game.Table.Players.OrderBy(x => x.MoneySafeAmnt).First();
            if (game.Table.CurrentPlayer == poor)
                GameHelper.CurrentPlayerChecks(game);

            GameHelper.CurrentPlayerPlays(game, poor.MoneySafeAmnt + 10);

            Assert.AreEqual(true, GameHelper.CurrentPlayerCalls(game), "The first player should be allowed to go all-in");
        }
    }
}
