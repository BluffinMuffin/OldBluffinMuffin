using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Protocols.Test.DataTypes
{

    public static class PlayerInfoMock
    {
        public static PlayerInfo Dora()
        {
            return new PlayerInfo()
            {
                State = PlayerStateEnum.Playing,
                NoSeat = 7,
                Name = "Dora",
                HoleCards = new[] { GameCardMock.AceOfClubs(), GameCardMock.JackOfHearts() },
                IsShowingCards = true,
                MoneyBetAmnt = 84,
                MoneySafeAmnt = 126
            };
        }

        public static PlayerInfo Diego()
        {
            return new PlayerInfo()
            {
                State = PlayerStateEnum.Zombie,
                NoSeat = 6,
                Name = "Diego",
                HoleCards = new[] { GameCardMock.TenOfDiamonds(), GameCardMock.TwoOfSpades() },
                IsShowingCards = true,
                MoneyBetAmnt = 21,
                MoneySafeAmnt = 63
            };
        }
    }
}
