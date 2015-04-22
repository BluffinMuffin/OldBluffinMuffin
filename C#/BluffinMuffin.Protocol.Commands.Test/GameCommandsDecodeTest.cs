using System.Linq;
using BluffinMuffin.Protocol.Commands.Game;
using BluffinMuffin.Protocol.Commands.Test.Comparing;
using BluffinMuffin.Protocol.Commands.Test.Helpers;
using BluffinMuffin.Protocols.Test.DataTypes;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Commands.Test
{
    [TestClass]
    public class GameCommandsDecodeTest
    {
        private T GetDecodedCommand<T>(T c) 
            where T : AbstractGameCommand
        {
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.TableId, dc.TableId);
            return dc;
        }
        private T GetDecodedResponse<T, TC>(T c) 
            where T : AbstractBluffinReponse<TC>
            where TC : AbstractGameCommand
        {
            var dc = EncodeDecodeHelper.GetDecodedCommand(c);
            Assert.AreEqual(c.Command.TableId, dc.Command.TableId);
            return dc;
        }

        [TestMethod]
        public void BetTurnEndedCommand()
        {
            var c = GameCommandMock.BetTurnEndedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.Round, dc.Round);
            Assert.AreEqual(c.PotsAmounts.Count, dc.PotsAmounts.Count);
            Assert.IsFalse(c.PotsAmounts.Except(dc.PotsAmounts).Any());
        }
        [TestMethod]
        public void BetTurnStartedCommand()
        {
            var c = GameCommandMock.BetTurnStartedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.Round, dc.Round);
            Assert.AreEqual(c.CardsId.Count, dc.CardsId.Count);
            Assert.IsFalse(c.CardsId.Except(dc.CardsId).Any());
        }
        [TestMethod]
        public void GameEndedCommand()
        {
            var c = GameCommandMock.GameEndedCommand();
            var dc = GetDecodedCommand(c);
        }
        [TestMethod]
        public void GameStartedCommand()
        {
            var c = GameCommandMock.GameStartedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.NeededBlind, dc.NeededBlind);
        }
        [TestMethod]
        public void PlayerHoleCardsChangedCommand()
        {
            var c = GameCommandMock.PlayerHoleCardsChangedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.PlayerPos, dc.PlayerPos);
            Assert.AreEqual(c.CardsId.Count, dc.CardsId.Count);
            Assert.IsFalse(c.CardsId.Except(dc.CardsId).Any());
            Assert.AreEqual(c.State, dc.State);
        }
        [TestMethod]
        public void PlayerJoinedCommand()
        {
            var c = GameCommandMock.PlayerJoinedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.PlayerName, dc.PlayerName);
        }
        [TestMethod]
        public void PlayerMoneyChangedCommand()
        {
            var c = GameCommandMock.PlayerMoneyChangedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.PlayerPos, dc.PlayerPos);
            Assert.AreEqual(c.PlayerMoney, dc.PlayerMoney);
        }
        [TestMethod]
        public void PlayerPlayMoneyCommand()
        {
            var c = GameCommandMock.PlayerPlayMoneyCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.Played, dc.Played);
        }
        [TestMethod]
        public void PlayerSitInCommand()
        {
            var c = GameCommandMock.PlayerSitInCommand();
            var dc = GetDecodedCommand(c);
            ComparePlayerSitInCommand(c, dc);
        }
        [TestMethod]
        public void PlayerSitInResponse()
        {
            var c = GameCommandMock.PlayerSitInResponse();
            var dc = GetDecodedResponse<PlayerSitInResponse, PlayerSitInCommand>(c);
            Assert.AreEqual(c.NoSeat, dc.NoSeat);
            ComparePlayerSitInCommand(c.Command, dc.Command);
        }

        [TestMethod]
        public void PlayerSitOutCommand()
        {
            var c = GameCommandMock.PlayerSitOutCommand();
            var dc = GetDecodedCommand(c);
            ComparePlayerSitOutCommand(c, dc);
        }
        [TestMethod]
        public void PlayerSitOutResponse()
        {
            var c = GameCommandMock.PlayerSitOutResponse();
            var dc = GetDecodedResponse<PlayerSitOutResponse, PlayerSitOutCommand>(c);
            Assert.AreEqual(c.Success, dc.Success);
            ComparePlayerSitOutCommand(c.Command, dc.Command);
        }
        [TestMethod]
        public void PlayerTurnBeganCommand()
        {
            var c = GameCommandMock.PlayerTurnBeganCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.PlayerPos, dc.PlayerPos);
            Assert.AreEqual(c.LastPlayerNoSeat, dc.LastPlayerNoSeat);
            Assert.AreEqual(c.MinimumRaise, dc.MinimumRaise);
        }
        [TestMethod]
        public void PlayerTurnEndedCommand()
        {
            var c = GameCommandMock.PlayerTurnEndedCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.PlayerPos, dc.PlayerPos);
            Assert.AreEqual(c.PlayerBet, dc.PlayerBet);
            Assert.AreEqual(c.PlayerMoney, dc.PlayerMoney);
            Assert.AreEqual(c.TotalPot, dc.TotalPot);
            Assert.AreEqual(c.ActionType, dc.ActionType);
            Assert.AreEqual(c.ActionAmount, dc.ActionAmount);
            Assert.AreEqual(c.State, dc.State);
        }
        [TestMethod]
        public void PlayerWonPotCommand()
        {
            var c = GameCommandMock.PlayerWonPotCommand();
            var dc = GetDecodedCommand(c);
            Assert.AreEqual(c.PlayerPos, dc.PlayerPos);
            Assert.AreEqual(c.PotId, dc.PotId);
            Assert.AreEqual(c.PlayerMoney, dc.PlayerMoney);
            Assert.AreEqual(c.Shared, dc.Shared);
        }
        [TestMethod]
        public void SeatUpdatedCommand()
        {
            var c = GameCommandMock.SeatUpdatedCommand();
            var dc = GetDecodedCommand(c);
            CompareSeatInfo.Compare(c.Seat, dc.Seat);
        }
        [TestMethod]
        public void TableClosedCommand()
        {
            var c = GameCommandMock.TableClosedCommand();
            var dc = GetDecodedCommand(c);
        }
        [TestMethod]
        public void TableInfoCommand()
        {
            var c = GameCommandMock.TableInfoCommand();
            var dc = GetDecodedCommand(c);
            CompareTableParams.Compare(c.Params, dc.Params);
            Assert.AreEqual(c.TotalPotAmount, dc.TotalPotAmount);
            Assert.AreEqual(c.PotsAmount.Count, dc.PotsAmount.Count);
            Assert.IsFalse(c.PotsAmount.Except(dc.PotsAmount).Any());
            Assert.AreEqual(c.BoardCardIDs.Count, dc.BoardCardIDs.Count);
            Assert.IsFalse(c.BoardCardIDs.Except(dc.BoardCardIDs).Any());
            Assert.AreEqual(c.NbPlayers, dc.NbPlayers);
            Assert.AreEqual(c.Seats.Count, dc.Seats.Count);
            for (int i = 0; i < c.Seats.Count; ++i)
                CompareSeatInfo.Compare(c.Seats[i], dc.Seats[i]);
            Assert.AreEqual(c.GameHasStarted, dc.GameHasStarted);
        }

        private static void ComparePlayerSitInCommand(PlayerSitInCommand c, PlayerSitInCommand dc)
        {
            Assert.AreEqual(c.NoSeat, dc.NoSeat);
            Assert.AreEqual(c.MoneyAmount, dc.MoneyAmount);
        }
        private static void ComparePlayerSitOutCommand(PlayerSitOutCommand c, PlayerSitOutCommand dc)
        {
        }
    }
}
