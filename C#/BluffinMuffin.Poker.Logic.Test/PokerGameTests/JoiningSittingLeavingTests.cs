using System.Linq;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests
{
    [TestClass]
    public class JoiningSittingLeavingTests
    {

        [TestMethod]
        public void EnterNonStartedGame()
        {
            var game = GameMock.GenerateSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1();

            Assert.AreEqual(false, game.JoinGame(player), "You should not enter a non-started game");
        }

        [TestMethod]
        public void EnterStartedGame()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1();

            Assert.AreEqual(true, game.JoinGame(player), "You should be able to enter a started game with no players");
        }

        [TestMethod]
        public void EnterStartedGameTwice()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1();
            game.JoinGame(player);

            Assert.AreEqual(false, game.JoinGame(player), "You should not be able to enter a game while you are in it");
        }

        [TestMethod]
        public void EnterStartedGameWithPlayerThatHaveMyNameAlreadyInIt()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1();
            game.JoinGame(player);
            var duplicate = PlayerMock.GenerateP1();
            Assert.AreEqual(false, game.JoinGame(duplicate), "You should not be able to enter a game while you are in it");
        }

        [TestMethod]
        public void ObtainSeatWhenFirst()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1();
            game.JoinGame(player);

            Assert.AreNotEqual(null, game.GameTable.AskToSitIn(player, -1), "You should be able to obtain a seat in a game with all the seats available");
        }

        [TestMethod]
        public void SitWhenFirst()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1();
            game.JoinGame(player);
            game.GameTable.AskToSitIn(player, -1);

            Assert.AreNotEqual(-1, game.SitIn(player), "You should be able to sit in a game with all the seats available");
        }

        [TestMethod]
        public void ObtainSeatWhenAlreadySeated()
        {
            var game = GameMock.StartSimple2PlayersBlindsGame();
            var player = PlayerMock.GenerateP1Seated(game);

            Assert.AreEqual(null, game.GameTable.AskToSitIn(player, -1), "You should not be able to obtain a seat in twice");
        }

        [TestMethod]
        public void EnterStartedGameWith1PSat()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1();
            var player = PlayerMock.GenerateP2();

            Assert.AreEqual(true, game.JoinGame(player), "You should be able to enter a started game with only 1 player");
        }

        [TestMethod]
        public void ObtainSeatWhenOnly1P()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1();
            var player = PlayerMock.GenerateP2();
            game.JoinGame(player);

            Assert.AreNotEqual(null, game.GameTable.AskToSitIn(player, -1), "You should be able to obtain a seat in a game with only 1 seated player");
        }

        [TestMethod]
        public void SitWhenOnly1P()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1();
            var player = PlayerMock.GenerateP2();
            game.JoinGame(player);
            game.GameTable.AskToSitIn(player, -1);

            Assert.AreNotEqual(-1, game.SitIn(player), "You should be able to sit in a game with only 1 seated player");
        }

        [TestMethod]
        public void EnterStartedGameWith2PSatWithMaxSeat2()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = PlayerMock.GenerateP3();

            Assert.AreEqual(true, game.JoinGame(player), "You should always be able to enter a started game even if full (MaxSeats=2)");
        }

        [TestMethod]
        public void ObtainSeatWhen2PSatWithMaxSeat2()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = PlayerMock.GenerateP3();
            game.JoinGame(player);

            Assert.AreEqual(null, game.GameTable.AskToSitIn(player, -1), "You should not be able to obtain a seat in a game that is full (MaxSeats=2)");
        }

        [TestMethod]
        public void SitWhen2PSatWithMaxSeat2()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = PlayerMock.GenerateP3();
            game.JoinGame(player);
            game.GameTable.AskToSitIn(player, -1);

            Assert.AreEqual(-1, game.SitIn(player), "You should not be able to sit in a game that is full (MaxSeats=2)");
        }

        [TestMethod]
        public void ObtainSeatWhen2PSatWithMaxSeat2But1PLeft()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = PlayerMock.GenerateP3();
            game.JoinGame(player);
            game.LeaveGame(game.GameTable.Players.First());

            Assert.AreNotEqual(null, game.GameTable.AskToSitIn(player, -1), "You should be able to obtain a seat a game that is now with only 1 seated player");
        }

        [TestMethod]
        public void ObtainSeatWhen2PSatWithMaxSeat2But1PSatOut()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = PlayerMock.GenerateP3();
            game.JoinGame(player);
            game.SitOut(game.GameTable.Players.First());

            Assert.AreNotEqual(null, game.GameTable.AskToSitIn(player, -1), "You should be able to obtain a seat a game that is now with only 1 seated player");
        }

        [TestMethod]
        public void EnterStartedGameThatEverybodyLeft()
        {
            var game = GameMock.StartSimple2PlayersBlindsGameAndSitsP1P2();
            var player = PlayerMock.GenerateP3();
            game.LeaveGame(game.GameTable.Players.First());
            game.LeaveGame(game.GameTable.Players.First());
            Assert.AreEqual(false, game.JoinGame(player), "You should not enter an ended game");
        }
    }
}
