using System.Linq;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests
{
    [TestClass]
    public class WhoIsUnderTheGunTests
    {
        [TestMethod]
        public void Game2PUtgIsDealerOnPreflop()
        {
            var nfo = Simple2PlayersBlindsGameMock.BlindsPosted();

            Assert.AreEqual(nfo.CurrentPlayer, nfo.Dealer, "Dealer should be under the gun on preflop");
        }
        [TestMethod]
        public void Game2PUtgIsNextToDealerOnFlop()
        {
            var nfo = Simple2PlayersBlindsGameMock.AfterPreflop();

            Assert.AreEqual(nfo.CurrentPlayer, nfo.PlayerNextTo(nfo.Dealer), "Dealer should be under the gun on flop");
        }
        [TestMethod]
        public void Game3PUtgIsNextToBigBlindOnPreflop()
        {
            var nfo = Simple3PlayersBlindsGameMock.BlindsPosted();

            Assert.AreEqual(nfo.CurrentPlayer, nfo.PlayerNextTo(nfo.PlayerNextTo(nfo.PlayerNextTo(nfo.Dealer))), "Dealer should be under the gun on preflop");
        }
        [TestMethod]
        public void Game3PUtgIsNextToDealerOnFlop()
        {
            var nfo = Simple3PlayersBlindsGameMock.AfterPreflop();

            Assert.AreEqual(nfo.CurrentPlayer, nfo.PlayerNextTo(nfo.Dealer), "Dealer should be under the gun on flop");
        }
        [TestMethod]
        public void Game4PUtgIsNextToBigBlindOnPreflop()
        {
            var nfo = Simple4PlayersBlindsGameMock.BlindsPosted();

            Assert.AreEqual(nfo.CurrentPlayer, nfo.PlayerNextTo(nfo.PlayerNextTo(nfo.PlayerNextTo(nfo.Dealer))), "Dealer should be under the gun on preflop");
        }
        [TestMethod]
        public void Game4PUtgIsNextToDealerOnFlop()
        {
            var nfo = Simple4PlayersBlindsGameMock.AfterPreflop();

            Assert.AreEqual(nfo.CurrentPlayer, nfo.PlayerNextTo(nfo.Dealer), "Dealer should be under the gun on flop");
        }
    }
}
