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
    public class GameStateTests
    {
        [TestMethod]
        public void NotStartedStateIsInit()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameEmpty();

            Assert.AreEqual(GameStateEnum.Init, nfo.Game.State, "The game should not be started");
        }
        [TestMethod]
        public void AfterStartedStateIsWaitForPlayers()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameEmptyButStarted();

            Assert.AreEqual(GameStateEnum.WaitForPlayers, nfo.Game.State, "The game should wait for players");
        }
        [TestMethod]
        public void After1PlayerSeatedStateIsStillWaitForPlayers()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithP1Seated();

            Assert.AreEqual(GameStateEnum.WaitForPlayers, nfo.Game.State, "The game should still wait for players to sit in when only 1 is seated");
        }
        [TestMethod]
        public void AfterBothPlayerSeatedStateIsWaitForBlinds()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();

            Assert.AreEqual(GameStateEnum.WaitForBlinds, nfo.Game.State, "The game should now wait for blinds");
        }
        [TestMethod]
        public void AfterFirstBlindStateIsStillWaitForBlinds()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            PlayerHelper.PutBlinds(nfo.Game, nfo.P1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, nfo.Game.State, "The game should still wait for blinds, missing the one from p2");
        }

        [TestMethod]
        public void AfterBlindsGameStateIsPlaying()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();

            Assert.AreEqual(GameStateEnum.Playing, nfo.Game.State, "The game should now be in the playing state");
        }
        [TestMethod]
        public void AfterPlayerFoldStateIsWaitForBlinds()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            GameHelper.CurrentPlayerFolds(nfo.Game);
            Assert.AreEqual(GameStateEnum.WaitForBlinds, nfo.Game.State, "The game should be back waiting for blinds sincepot was won and it's starting over");
        }
        [TestMethod]
        public void AfterPlayerLeftStateIsWaitForPlayers()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            nfo.Game.LeaveGame(nfo.Game.GameTable.CurrentPlayer);
            Assert.AreEqual(GameStateEnum.WaitForPlayers, nfo.Game.State, "The game should be back waiting for players since only one player is left");
        }
        [TestMethod]
        public void AfterPlayerLeftThenJoinedStateIsWaitForBlinds()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            var curPlayer = nfo.Game.GameTable.CurrentPlayer;
            nfo.Game.LeaveGame(curPlayer);
            PlayerHelper.SitInGame(nfo.Game, curPlayer);
            Assert.AreEqual(GameStateEnum.WaitForBlinds, nfo.Game.State, "The game should be back waiting for blinds since enough players are there to play");
        }
        [TestMethod]
        public void IfOnelayerLeftDuringBlindsStateIsStillWaitForBlinds()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            nfo.Game.LeaveGame(nfo.P1);
            Assert.AreEqual(GameStateEnum.WaitForBlinds, nfo.Game.State, "The game should still be waiting for blinds waiting for P2 blind");
        }
        [TestMethod]
        public void IfOnePlayerLeftDuringBlindsAndP2PostBlindStateIsWaitForPlayers()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameWithBothSeated();
            nfo.Game.LeaveGame(nfo.P1);
            PlayerHelper.PutBlinds(nfo.Game, nfo.P2);
            Assert.AreEqual(GameStateEnum.WaitForPlayers, nfo.Game.State, "The game should now be waiting for players: p2 put his blind, the game started, p2 wins the pot, and the game goes back to waiting for players");
        }
        [TestMethod]
        public void IfPlayingPlayerLeftStateIsWaitForPlayers()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            var cp = nfo.Game.Table.CurrentPlayer;
            nfo.Game.LeaveGame(cp);
            Assert.AreEqual(GameStateEnum.WaitForPlayers, nfo.Game.State, "The game should now be waiting for players: cp left (folded), other player wins the pot, and the game goes back to waiting for players");
        }
        [TestMethod]
        public void IfNotPlayingPlayerLeftStateIsStillPlaying()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            var np = nfo.Game.Table.GetSeatOfPlayingPlayerNextTo(nfo.Game.Table.Seats[nfo.Game.Table.CurrentPlayer.NoSeat]).Player;
            nfo.Game.LeaveGame(np);
            Assert.AreEqual(GameStateEnum.Playing, nfo.Game.State, "The game should be still in playing mode since it wasn't the playing player.");
        }
        [TestMethod]
        public void IfNotPlayingPlayerLeftThenOtherPlaysStateIsNowWaitingForPlayers()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            var np = nfo.Game.Table.GetSeatOfPlayingPlayerNextTo(nfo.Game.Table.Seats[nfo.Game.Table.CurrentPlayer.NoSeat]).Player;
            nfo.Game.LeaveGame(np);
            GameHelper.CurrentPlayerCalls(nfo.Game);
            Assert.AreEqual(GameStateEnum.WaitForPlayers, nfo.Game.State, "The game should be back waiting for players: cp plays, np folds since he left, player wins the pot, and the game goes back to waiting for players");
        }
        [TestMethod]
        public void IfEverybodyLeaveStateIsNowEnded()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            nfo.Game.LeaveGame(nfo.P1);
            nfo.Game.LeaveGame(nfo.P2);
            Assert.AreEqual(GameStateEnum.End, nfo.Game.State, "The game should be ended");
        }
    }
}
