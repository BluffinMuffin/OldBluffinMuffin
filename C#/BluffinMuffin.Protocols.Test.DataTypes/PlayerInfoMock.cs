using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.Enums;
using Com.Ericmas001.Games;

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
