using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PokerWorld.Game;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace Test.PokerWorld
{
    [TestClass]
    public class PokerGameTest
    {


        [TestMethod]
        public void JoinAndLeaveTest()
        {
            PokerGame game = new PokerGame( new PokerTable(new GameRule() { MaxPlayers = 2}));
            PokerPlayer p1 = new PokerPlayer("p1", 5000);
            PokerPlayer p1Dup = new PokerPlayer("p1", 5000);
            PokerPlayer p2 = new PokerPlayer("p2", 5000);
            PokerPlayer p3 = new PokerPlayer("p3", 5000);

            Assert.AreEqual(false, game.JoinGame(p1), "You should not enter a non-started game");

            game.Start();

            Assert.AreEqual(true, game.JoinGame(p1), "You should be able to enter a started game with no players");

            Assert.AreEqual(false, game.JoinGame(p1), "You should not be able to enter a game while you are in it");

            Assert.AreEqual(false, game.JoinGame(p1Dup), "You should not be able to enter a game with the same name as another player");

            Assert.AreEqual(true, game.JoinGame(p2), "You should be able to enter a started game with only 1 player");

            Assert.AreEqual(false, game.JoinGame(p3), "You should not be able to enter a started game with already 2 players (MaxSeats=2)");

            game.LeaveGame(p2);
            Assert.AreEqual(true, game.JoinGame(p3), "You should not take the place of a player that left");

            game.LeaveGame(p1);
            game.LeaveGame(p3);
            Assert.AreEqual(false, game.JoinGame(p2), "You should not enter an ended game");

        }

        [TestMethod]
        public void BlindsTest()
        {
            //start the game with p1 and p2
            PokerGame game = new PokerGame( new PokerTable(new GameRule() { MaxPlayers = 2}));
            game.Start();
            PokerPlayer p1 = new PokerPlayer("p1", 5000);
            SitInGame(game, p1);
            PokerPlayer p2 = new PokerPlayer("p2", 2);
            SitInGame(game, p2);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should now wait for blinds");

            //Get the amount needed from both players
            int needed1 = game.Table.GetBlindNeeded(p1);
            int needed2 = game.Table.GetBlindNeeded(p2);

            Assert.AreNotEqual(0, needed1, "The game should need a blind from p1");
            Assert.AreNotEqual(0, needed2, "The game should need a blind from p2");

            //Try to bet more than what is needed
            Assert.AreEqual(false, game.PlayMoney(p1, needed1 + 1), "The game should not accept any blind that is over what is needed");

            //Try to bet less than what is needed while beaing able to put more
            Assert.AreEqual(false, game.PlayMoney(p1, needed1 - 1), "The game should not accept any blind that is under what is needed unless that is all the player got");

            //Try to bet the exact needed amount
            Assert.AreEqual(true, game.PlayMoney(p1, needed1), "The game should accept a perfect blind");


            //Get the amount still needed from both players
            needed1 = game.Table.GetBlindNeeded(p1);
            needed2 = game.Table.GetBlindNeeded(p2);
            Assert.AreEqual(0, needed1, "The game should not need a blind from p1 anymore");
            Assert.AreNotEqual(0, needed2, "The game should still need a blind from p2");

            //Try to bet more than what the player have
            //Assert.AreEqual(false, game.PlayMoney(p2, 5), "The game should not accept any blind that is over what a player got");
            //This test is useless, the game already change the amount if you are playing over what you can

            //Try to bet more than what the player have
            Assert.AreEqual(true, game.PlayMoney(p2, 2), "The game should accept a blind that is under what is needed if that is all the player got");
        }

        [TestMethod]
        public void StatesTest()
        {
            PokerGame game = new PokerGame(new PokerTable(new GameRule() { MaxPlayers = 2 }));

            Assert.AreEqual(GameStateEnum.Init, game.State, "The game should not be started");

            //start the game
            game.Start();

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should wait for players");

            //make p1 join the game
            PokerPlayer p1 = new PokerPlayer("p1", 5000);
            SitInGame(game, p1);

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should still wait for players to sit in when only 1 is seated");

            //make p2 join the game
            PokerPlayer p2 = new PokerPlayer("p2", 5000);
            SitInGame(game, p2);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should now wait for blinds");

            //Post need blinds for p1
            PutBlinds(game, p1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should still wait for blinds, missing the one from p2");

            //Post need blinds for p2
            PutBlinds(game, p2);

            Assert.AreEqual(GameStateEnum.Playing, game.State, "The game should now be in the playing state");

            //Make the player fold so the other one already win the pot
            game.PlayMoney(game.Table.CurrentPlayer, -1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds sincepot was won and it's starting over");

            //Post blinds again to go back to playing state
            PutBlinds(game, p1);
            PutBlinds(game, p2);

            //Make the playing player leave the game without taking any actions
            PokerPlayer cp = game.Table.CurrentPlayer;
            game.LeaveGame(cp);

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since only one player is left");

            //Go back to play
            SitInGame(game, cp);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since enough players are there to play");

            //let's leave before putting the blind now
            int safeMoneyBefore = p1.Info.MoneySafeAmnt;
            game.LeaveGame(p1);

            Assert.AreEqual(true, p1.Info.MoneySafeAmnt < safeMoneyBefore, "The player should have less money then before, since blinds have been posted before he left");
            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should still be waiting for blinds");

            //p2 will put his blind, and the game should start, p2 will win the pot, and then go back to waiting for players
            PutBlinds(game, p2);

            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should now be waiting for players");

            //Go back to play
            SitInGame(game, p1);

            Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since enough players are there to play");

            //Post blinds again to go back to playing state
            PutBlinds(game, p1);
            PutBlinds(game, p2);

            cp = game.Table.CurrentPlayer;
            PokerPlayer np = game.Table.GetPlayingPlayerNextTo(cp.Info.NoSeat);
            game.LeaveGame(np);
            Assert.AreEqual(GameStateEnum.Playing, game.State, "The game should be still in playing mode since it wasn't the playing player.");

            game.PlayMoney(cp, game.Table.CallAmnt(cp.Info));
            
            Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since only one player is left");

            //Let's end the game
            game.LeaveGame(cp);

            Assert.AreEqual(GameStateEnum.End, game.State, "The game should be ended");
        }

        [TestMethod]
        public void BettingTest()
        {
            //start the game
            PokerGame game = new PokerGame(new PokerTable(new GameRule() { MaxPlayers = 2 }));
            game.Start();

            //make p1 join the game
            PokerPlayer p1 = new PokerPlayer("p1", 100);
            SitInGame(game, p1);

            //make p2 join the game
            PokerPlayer p2 = new PokerPlayer("p2", 1000);
            SitInGame(game, p2);

            //Post needed blinds
            PutBlinds(game, p1);
            PutBlinds(game, p2);

            Assert.AreEqual(GameStateEnum.Playing, game.State, "The game should now be in the playing state");
            Assert.AreEqual(RoundTypeEnum.Preflop, game.Round, "The game should now be in the preflop round");

            //Make the first player call
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer.Info)), "The first player should be allowed to call");
            Assert.AreEqual(RoundTypeEnum.Preflop, game.Round, "The game should still be in the preflop round");

            //Make the second player call
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer.Info)), "The second player should be allowed to call");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should now be in the flop round");

            //Make the first player check
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, 0), "The first player should be allowed to check");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should still be in the flop round");

            //Make the second player bet
            Assert.AreEqual(false, game.PlayMoney(game.Table.CurrentPlayer, game.Table.MinRaiseAmnt(game.Table.CurrentPlayer.Info) - 1), "The player should not be able to raise under the minimum");
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.MinRaiseAmnt(game.Table.CurrentPlayer.Info)), "The player should be able to raise with the minimum");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should still be in the flop round");

            //Make the first player come back and raise even more than he needs to
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.MinRaiseAmnt(game.Table.CurrentPlayer.Info) + 5), "The player should be able to raise over the minimum");
            Assert.AreEqual(RoundTypeEnum.Flop, game.Round, "The game should still be in the flop round");

            //Make the second player stay calm and call, but try to call less the first time
            Assert.AreEqual(false, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer.Info) - 1), "The first player should not be allowed to play under what is needed to call");
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer.Info)), "The first player should be allowed to play what is needed to call");
            Assert.AreEqual(RoundTypeEnum.Turn, game.Round, "The game should now be in the turn round");

            //if this is the player with less money (p1), well just check, i want the other one :)
            if (game.Table.CurrentPlayer == p1)
                game.PlayMoney(p1, 0);

            //make the second player put more than the first one can put
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, p1.Info.MoneySafeAmnt + 10), "The second player should be allowed to play over what the other player have");

            //make the first player call it, so allin
            Assert.AreEqual(true, game.PlayMoney(game.Table.CurrentPlayer, game.Table.CallAmnt(game.Table.CurrentPlayer.Info) - 1), "The first player should be allowed to go all-in");

            //The pot is won, let's start over.
            if (p1.Info.MoneySafeAmnt == 0)
                Assert.AreEqual(GameStateEnum.WaitForPlayers, game.State, "The game should be back waiting for players since the pot was won and there is only one player left with money");
            else
                Assert.AreEqual(GameStateEnum.WaitForBlinds, game.State, "The game should be back waiting for blinds since the pot was won and it's starting over");
        }



        [TestMethod]
        public void SplittingPotsTest()
        {
            Assert.Inconclusive();
        }



        private void PutBlinds(PokerGame game, PokerPlayer p)
        {
            int b = game.Table.GetBlindNeeded(p);
            game.PlayMoney(p, b);
        }

        private static void SitInGame(PokerGame game, PokerPlayer p1)
        {
            game.JoinGame(p1);
            game.SitInGame(p1);
        }
    }
}
