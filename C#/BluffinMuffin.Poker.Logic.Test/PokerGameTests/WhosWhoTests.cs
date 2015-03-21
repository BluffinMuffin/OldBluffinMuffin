using System.Linq;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests
{
    [TestClass]
    public class WhosWhoTests
    {

        [TestMethod]
        public void Game2PSmallIsDealer()
        {
            var nfo = Simple2PlayersBlindsGameMock.WithBothPlayersSeated();
            var players = nfo.Game.GameTable.PlayingPlayers.Where(x => nfo.BlindNeeded(x) > 0).OrderBy(nfo.BlindNeeded);
            Assert.Inconclusive();
            Assert.AreEqual(false, nfo.Game.GameTable.NoSeatDealer == players.First().NoSeat, "Dealer should be the small blind");
        }
        [TestMethod]
        public void Game2PBigIsNotDealer()
        {
            var nfo = Simple2PlayersBlindsGameMock.WithBothPlayersSeated();
            var players = nfo.Game.GameTable.PlayingPlayers.Where(x => nfo.BlindNeeded(x) > 0).OrderBy(nfo.BlindNeeded);
            Assert.Inconclusive();
            Assert.AreEqual(false, nfo.Game.GameTable.NoSeatDealer != players.Last().NoSeat, "Dealer should not be the big blind");
        }
    }
}
