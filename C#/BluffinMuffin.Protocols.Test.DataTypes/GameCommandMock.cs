using System.Collections.Generic;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Protocol.Commands.Game;

namespace BluffinMuffin.Protocols.Test.DataTypes
{
    public static class GameCommandMock
    {
        public static BetTurnEndedCommand BetTurnEndedCommand()
        {
            return new BetTurnEndedCommand() {TableId = 42, PotsAmounts = new List<int>() {5, 10, 15, 20}, Round = RoundTypeEnum.River};
        }

        public static BetTurnStartedCommand BetTurnStartedCommand()
        {
            return new BetTurnStartedCommand() {TableId = 42, CardsId = new List<int>() {7, 21, 42, 63}, Round = RoundTypeEnum.River};
        }

        public static GameEndedCommand GameEndedCommand()
        {
            return new GameEndedCommand() {TableId = 42};
        }

        public static GameStartedCommand GameStartedCommand()
        {
            return new GameStartedCommand() {TableId = 42, NeededBlind = 84};
        }

        public static PlayerHoleCardsChangedCommand PlayerHoleCardsChangedCommand()
        {
            return new PlayerHoleCardsChangedCommand() {TableId = 42, CardsId = new List<int>() {21, 42}, PlayerPos = 7, State = PlayerStateEnum.Playing};
        }

        public static PlayerJoinedCommand PlayerJoinedCommand()
        {
            return new PlayerJoinedCommand() {TableId = 42, PlayerName = "SpongeBob"};
        }

        public static PlayerLeftCommand PlayerLeftCommand()
        {
            return new PlayerLeftCommand() {TableId = 42, PlayerPos = 7};
        }

        public static PlayerMoneyChangedCommand PlayerMoneyChangedCommand()
        {
            return new PlayerMoneyChangedCommand() {TableId = 42, PlayerPos = 7, PlayerMoney = 84};
        }

        public static PlayerPlayMoneyCommand PlayerPlayMoneyCommand()
        {
            return new PlayerPlayMoneyCommand() {TableId = 42, Played = 84};
        }

        public static PlayerSitInCommand PlayerSitInCommand()
        {
            return new PlayerSitInCommand() {TableId = 42, NoSeat = 7, MoneyAmount = 84};
        }

        public static PlayerSitInResponse PlayerSitInResponse()
        {
            return PlayerSitInCommand().Response(7);
        }

        public static PlayerSitOutCommand PlayerSitOutCommand()
        {
            return new PlayerSitOutCommand() {TableId = 42};
        }

        public static PlayerSitOutResponse PlayerSitOutResponse()
        {
            return PlayerSitOutCommand().Response(true);
        }

        public static PlayerTurnBeganCommand PlayerTurnBeganCommand()
        {
            return new PlayerTurnBeganCommand() {TableId = 42, PlayerPos = 7, LastPlayerNoSeat = 6, MinimumRaise = 84};
        }

        public static PlayerTurnEndedCommand PlayerTurnEndedCommand()
        {
            return new PlayerTurnEndedCommand() {TableId = 42, PlayerPos = 7, PlayerBet = 21, PlayerMoney = 84, TotalPot = 126, ActionType = GameActionEnum.Raise, ActionAmount = 63, State = PlayerStateEnum.AllIn};
        }

        public static PlayerWonPotCommand PlayerWonPotCommand()
        {
            return new PlayerWonPotCommand() {TableId = 42, PlayerPos = 7, Shared = 21, PlayerMoney = 84};
        }

        public static SeatUpdatedCommand SeatUpdatedCommand()
        {
            return new SeatUpdatedCommand() {TableId = 42, Seat = SeatInfoMock.SeatSeven()};
        }

        public static TableClosedCommand TableClosedCommand()
        {
            return new TableClosedCommand() {TableId = 42};
        }

        public static TableInfoCommand TableInfoCommand()
        {
            return new TableInfoCommand() {TableId = 42, Params = TableParamsMock.ParamsOne(), TotalPotAmount = 126, PotsAmount = new List<int>() {5, 10, 15, 20}, BoardCardIDs = new List<int>() {7, 21, 42, 63}, NbPlayers = 7, Seats = SeatInfoMock.AllSeats(), GameHasStarted = true};
        }
    }
}
