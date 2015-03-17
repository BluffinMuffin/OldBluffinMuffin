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
        public void AfterBlindsGameStateIsPlaying()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();

            Assert.AreEqual(GameStateEnum.Playing, nfo.Game.State, "The game should now be in the playing state");
        }


        [TestMethod]
        public void BigUglyTest()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameEmpty();
            var game = nfo.Game;

            Assert.AreEqual(GameStateEnum.Init, game.State, "The game should not be started");

            //start the game
            game.Start();

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should wait for players");

            //make p1 join the game
            var p1 = new PlayerInfo("p1", 5000);
            PlayerHelper.SitInGame(game, p1);

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should still wait for players to sit in when only 1 is seated");

            //make p2 join the game
            var p2 = new PlayerInfo("p2", 5000);
            PlayerHelper.SitInGame(game, p2);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should now wait for blinds");

            //Post need blinds for p1
            PlayerHelper.PutBlinds(game, p1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should still wait for blinds, missing the one from p2");

            //Post need blinds for p2
            PlayerHelper.PutBlinds(game, p2);

            //Make the player fold so the other one already win the pot
            game.PlayMoney(game.Table.CurrentPlayer, -1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds sincepot was won and it's starting over");

            //Post blinds again to go back to playing state
            PlayerHelper.PutBlinds(game, p1);
            PlayerHelper.PutBlinds(game, p2);

            //Make the playing player leave the game without taking any actions
            var cp = game.Table.CurrentPlayer;
            game.LeaveGame(cp);

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since only one player is left");

            //Go back to play
            PlayerHelper.SitInGame(game, cp);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since enough players are there to play");

            //let's leave before putting the blind now
            var safeMoneyBefore = p1.MoneySafeAmnt;
            game.LeaveGame(p1);

            Assert.AreEqual(true, p1.MoneySafeAmnt < safeMoneyBefore, "The player should have less money then before, since blinds have been posted before he left");
            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should still be waiting for blinds");

            //p2 will put his blind, and the game should start, p2 will win the pot, and then go back to waiting for players
            PlayerHelper.PutBlinds(game, p2);

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should now be waiting for players");

            //Go back to play
            PlayerHelper.SitInGame(game, p1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since enough players are there to play");

            //Post blinds again to go back to playing state
            PlayerHelper.PutBlinds(game, p1);
            PlayerHelper.PutBlinds(game, p2);

            cp = game.Table.CurrentPlayer;
            var np = game.Table.GetSeatOfPlayingPlayerNextTo(game.Table.Seats[cp.NoSeat]).Player;
            game.LeaveGame(np);
            Assert.AreEqual(GameStateEnum.Playing, game.State, "The game should be still in playing mode since it wasn't the playing player.");

            game.PlayMoney(cp, game.Table.CallAmnt(cp));

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since only one player is left");

            //Let's end the game
            game.LeaveGame(cp);

            Assert.AreEqual(GameStateEnum.End, game.State, "The game should be ended");
        }
    }
}
