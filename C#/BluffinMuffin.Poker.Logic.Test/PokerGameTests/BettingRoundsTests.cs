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
    public class BettingRoundsTests
    {

        [TestMethod]
        public void AfterBlindsRoundIsPreflop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();

            Assert.AreEqual(RoundTypeEnum.Preflop, nfo.Game.Round, "The game should now be in the preflop round");
        }
        [TestMethod]
        public void AfterPreflopRoundIsFlop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();

            Assert.AreEqual(RoundTypeEnum.Flop, nfo.Game.Round, "The game should now be in the flop round");
        }
        [TestMethod]
        public void AfterFlopRoundIsTurn()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterFlop();

            Assert.AreEqual(RoundTypeEnum.Turn, nfo.Game.Round, "The game should now be in the turn round");
        }
        [TestMethod]
        public void AfterTurnRoundIsRiver()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterTurn();

            Assert.AreEqual(RoundTypeEnum.River, nfo.Game.Round, "The game should now be in the river round");
        }

        [TestMethod]
        public void AfterFirstPlayerCallRoundStillPreflop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameBlindsPosted();
            GameHelper.CurrentPlayerCalls(nfo.Game);

            Assert.AreEqual(RoundTypeEnum.Preflop, nfo.Game.Round, "The game should still be in the preflop round");
        }

        [TestMethod]
        public void AfterBothPlayerCallOnPreflopRoundNowFlop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();

            Assert.AreEqual(RoundTypeEnum.Flop, nfo.Game.Round, "The game should now be in the flop round");
        }

        [TestMethod]
        public void AfterFirstPlayerCheckOnFlopRoundStillFlop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerChecks(nfo.Game);

            Assert.AreEqual(RoundTypeEnum.Flop, nfo.Game.Round, "The game should still be in the flop round");
        }

        [TestMethod]
        public void AfterSecondPlayerBetOnFlopRoundStillFlop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerChecks(nfo.Game);
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));

            Assert.AreEqual(RoundTypeEnum.Flop, nfo.Game.Round, "The game should still be in the flop round");
        }

        [TestMethod]
        public void AfterSecondPlayerChecksNowTurn()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerChecks(nfo.Game);
            GameHelper.CurrentPlayerChecks(nfo.Game);

            Assert.AreEqual(RoundTypeEnum.Turn, nfo.Game.Round, "The game should now be in the Turn round");
        }

        [TestMethod]
        public void AfterSecondPlayerCallsNowTurn()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerCalls(nfo.Game);

            Assert.AreEqual(RoundTypeEnum.Turn, nfo.Game.Round, "The game should now be in the Turn round");
        }

        [TestMethod]
        public void AfterOnlyRaiseShouldStayFlop()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));

            Assert.AreEqual(RoundTypeEnum.Flop, nfo.Game.Round, "The game should still be in the flop round");
        }

        [TestMethod]
        public void AfterRaisesThenCallShouldNowTurn()
        {
            var nfo = GameMock.Simple2PlayersBlindsGameAfterPreflop();
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerPlays(nfo.Game, nfo.Game.Table.MinRaiseAmnt(nfo.Game.Table.CurrentPlayer));
            GameHelper.CurrentPlayerCalls(nfo.Game);

            Assert.AreEqual(RoundTypeEnum.Turn, nfo.Game.Round, "The game should now be in the Turn round");
        }
    }
}
