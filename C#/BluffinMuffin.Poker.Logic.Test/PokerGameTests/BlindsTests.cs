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
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should now wait for blinds");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP1()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var p1 = PlayerMock.GenerateP1Seated(game);
            PlayerMock.GenerateP2Seated(game);

            Assert.AreNotEqual(0, game.GameTable.GetBlindNeeded(p1), "The game should need a blind from p1");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP2()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1();
            var p2 = PlayerMock.GenerateP2Seated(game);

            Assert.AreNotEqual(0, game.GameTable.GetBlindNeeded(p2), "The game should need a blind from p2");
        }

        [TestMethod]
        public void StartGameAndTryPutBlindMoreThanNeeded()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = game.GameTable.Players.First();

            Assert.AreEqual(false, game.PlayMoney(player, game.GameTable.GetBlindNeeded(player) + 1), "The game should not accept any blind that is over what is needed");
        }

        [TestMethod]
        public void StartGameAndTryPutBlindLessThanNeeded()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = game.GameTable.Players.First();

            Assert.AreEqual(false, game.PlayMoney(player, game.GameTable.GetBlindNeeded(player) - 1), "The game should not accept any blind that is under what is needed unless that is all the player got");
        }

        [TestMethod]
        public void StartGameAndTryPutBlindLessThanNeededWithPoorPlayer()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1();
            var p2 = PlayerMock.GenerateP2PoorSeated(game);

            Assert.AreEqual(false, game.PlayMoney(p2, p2.MoneySafeAmnt), "The game should accept a blind that is under what is needed if that is all the player got");
        }

        [TestMethod]
        public void StartGameAndTryPutBlind()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = game.GameTable.Players.First();

            Assert.AreEqual(true, game.PlayMoney(player, game.GameTable.GetBlindNeeded(player)), "The game should accept a perfect blind");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP1AfterP1PutHis()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var p1 = PlayerMock.GenerateP1Seated(game);
            PlayerMock.GenerateP2Seated(game);
            game.PlayMoney(p1, game.GameTable.GetBlindNeeded(p1));

            Assert.AreEqual(0, game.GameTable.GetBlindNeeded(p1), "The game should not need a blind from p1 anymore");
        }

        [TestMethod]
        public void StartGameAndCheckNeededBlindP2AfterP1PutHis()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var p1 = PlayerMock.GenerateP1Seated(game);
            var p2 = PlayerMock.GenerateP2Seated(game);
            game.PlayMoney(p1, game.GameTable.GetBlindNeeded(p1));

            Assert.AreNotEqual(0, game.GameTable.GetBlindNeeded(p2), "The game should still need a blind from p2");
        }
    }
}
