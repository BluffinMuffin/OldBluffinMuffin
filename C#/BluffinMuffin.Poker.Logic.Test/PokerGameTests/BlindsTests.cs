using System.Linq;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests
{
    [TestClass]
    public class BlindsTests
    {
        [TestMethod]
        public void StartGameAndWaitForBlinds()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();

            Assert.AreEqual(GameStateEnum.WaitForBlinds, nfo.Game.State, "The game should now wait for blinds");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP1()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            Assert.AreNotEqual(0, nfo.Game.GameTable.GetBlindNeeded(nfo.P1), "The game should need a blind from p1");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP2()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            Assert.AreNotEqual(0, nfo.Game.GameTable.GetBlindNeeded(nfo.P2), "The game should need a blind from p2");
        }

        [TestMethod]
        public void StartGameAndTryPutBlindMoreThanNeeded()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            Assert.AreEqual(false, nfo.Game.PlayMoney(nfo.P1, nfo.Game.GameTable.GetBlindNeeded(nfo.P1) + 1), "The game should not accept any blind that is over what is needed");
        }

        [TestMethod]
        public void StartGameAndTryPutBlindLessThanNeeded()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            Assert.AreEqual(false, nfo.Game.PlayMoney(nfo.P1, nfo.Game.GameTable.GetBlindNeeded(nfo.P1) - 1), "The game should not accept any blind that is under what is needed unless that is all the player got");
        }

        [TestMethod]
        public void StartGameAndTryPutBlindLessThanNeededWithPoorPlayer()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithP1Seated();
            nfo.P2 = PlayerMock.GenerateP2PoorSeated(nfo.Game);

            Assert.AreEqual(true, nfo.Game.PlayMoney(nfo.P2, nfo.P2.MoneySafeAmnt), "The game should accept a blind that is under what is needed if that is all the player got");
        }

        [TestMethod]
        public void StartGameAndTryPutBlind()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();

            Assert.AreEqual(true, nfo.Game.PlayMoney(nfo.P1, nfo.Game.GameTable.GetBlindNeeded(nfo.P1)), "The game should accept a perfect blind");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP1AfterP1PutHis()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();

            nfo.Game.PlayMoney(nfo.P1, nfo.Game.GameTable.GetBlindNeeded(nfo.P1));

            Assert.AreEqual(0, nfo.Game.GameTable.GetBlindNeeded(nfo.P1), "The game should not need a blind from p1 anymore");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP2AfterP1PutHis()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();

            nfo.Game.PlayMoney(nfo.P1, nfo.Game.GameTable.GetBlindNeeded(nfo.P1));

            Assert.AreNotEqual(0, nfo.Game.GameTable.GetBlindNeeded(nfo.P2), "The game should still need a blind from p2");
        }

        [TestMethod]
        public void LeaveGameBeforePuttingBlindShouldStillMinusTheAmountFromMoney()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            var safeMoneyBefore = nfo.P1.MoneySafeAmnt;
            nfo.Game.LeaveGame(nfo.P1);
            Assert.AreEqual(true, nfo.P1.MoneySafeAmnt < safeMoneyBefore, "The player should have less money then before, since blinds were posted automatically before he left");
        }
    }
}
