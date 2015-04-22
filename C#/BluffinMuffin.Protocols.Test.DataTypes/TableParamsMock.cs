using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Protocols.Test.DataTypes
{
    public static class TableParamsMock
    {
        public static TableParams ParamsOne()
        {
            return new TableParams()
            {
                Blind = new BlindOptionsAnte()
                {
                    MoneyUnit = 10
                },
                GameType = GameTypeEnum.Holdem,
                MoneyUnit = 10,
                Limit = new LimitOptionsNoLimit(),
                Lobby = new LobbyOptionsTraining()
                {
                    StartingAmount = 1500
                },
                MaxPlayers = 5,
                MinPlayersToStart = 10,
                TableName = "Table One",
                Variant = "Wtf Is this field",
                WaitingTimes = new ConfigurableWaitingTimes()
                {
                    AfterBoardDealed = 1,
                    AfterPlayerAction = 2,
                    AfterPotWon = 3
                }
            };
        }
        public static TableParams ParamsTwo()
        {
            return new TableParams()
            {
                Blind = new BlindOptionsBlinds()
                {
                    MoneyUnit = 100
                },
                GameType = GameTypeEnum.Holdem,
                MoneyUnit = 100,
                Limit = new LimitOptionsPot(),
                Lobby = new LobbyOptionsCareer()
                {
                    MoneyUnit = 100,
                    IsMaximumBuyInLimited = true
                },
                MaxPlayers = 10,
                MinPlayersToStart = 8,
                TableName = "Table Two",
                Variant = "Wtf Is this field again",
                WaitingTimes = new ConfigurableWaitingTimes()
                {
                    AfterBoardDealed = 1,
                    AfterPlayerAction = 2,
                    AfterPotWon = 3
                }
            };
        }
        public static TableParams ParamsThree()
        {
            return new TableParams()
            {
                Blind = new BlindOptionsNone()
                {
                    MoneyUnit = 100
                },
                GameType = GameTypeEnum.Holdem,
                MoneyUnit = 1000,
                Limit = new LimitOptionsFixed(),
                Lobby = new LobbyOptionsCareer()
                {
                    MoneyUnit = 100,
                    IsMaximumBuyInLimited = true
                },
                MaxPlayers = 6,
                MinPlayersToStart = 3,
                TableName = "Table Three",
                Variant = "Wtf Is this field again!",
                WaitingTimes = new ConfigurableWaitingTimes()
                {
                    AfterBoardDealed = 1,
                    AfterPlayerAction = 2,
                    AfterPotWon = 3
                }
            };
        }
    }
}
