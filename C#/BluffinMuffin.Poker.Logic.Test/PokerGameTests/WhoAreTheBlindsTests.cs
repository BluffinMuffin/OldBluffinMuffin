using System.Linq;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests
{
    [TestClass]
    public class WhoAreTheBlindsTests
    {
        [TestMethod]
        public void Game2PSmallIsDealer()
        {
            var nfo = Simple2PlayersBlindsGameMock.WithBothPlayersSeated();
            
            Assert.AreEqual(nfo.CalculatedSmallBlind, nfo.Dealer, "Dealer should be the small blind");
        }
        [TestMethod]
        public void Game2PBigIsNextToDealer()
        {
            var nfo = Simple2PlayersBlindsGameMock.WithBothPlayersSeated();

            Assert.AreEqual(nfo.CalculatedBigBlind, nfo.PlayerNextTo(nfo.Dealer), "Player Next To Dealer should be the big blind");
        }
        [TestMethod]
        public void Game3PSmallIsNextToDealer()
        {
            var nfo = Simple3PlayersBlindsGameMock.WithAllPlayersSeated();

            Assert.AreEqual(nfo.CalculatedSmallBlind, nfo.PlayerNextTo(nfo.Dealer), "Player Next To Dealer should be the small blind");
        }
        [TestMethod]
        public void Game3PBigIsNextToSmall()
        {
            var nfo = Simple3PlayersBlindsGameMock.WithAllPlayersSeated();

            Assert.AreEqual(nfo.CalculatedBigBlind, nfo.PlayerNextTo(nfo.CalculatedSmallBlind), "Player Next To CalculatedSmallBlind should be the big blind");
        }
        [TestMethod]
        public void Game4PSmallIsNextToDealer()
        {
            var nfo = Simple4PlayersBlindsGameMock.WithAllPlayersSeated();

            Assert.AreEqual(nfo.CalculatedSmallBlind, nfo.PlayerNextTo(nfo.Dealer), "Player Next To Dealer should be the small blind");
        }
        [TestMethod]
        public void Game4PBigIsNextToSmall()
        {
            var nfo = Simple4PlayersBlindsGameMock.WithAllPlayersSeated();

            Assert.AreEqual(nfo.CalculatedBigBlind, nfo.PlayerNextTo(nfo.CalculatedSmallBlind), "Player Next To CalculatedSmallBlind should be the big blind");
        }
    }
}
