using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Com.Ericmas001.Games;

namespace BluffinMuffin.Protocol.Commands.Test.Mocking
{
    public static class GameCardMock
    {
        public static GameCard AceOfClubs()
        {
            return new GameCard(GameCardKind.Club, GameCardValue.Ace);
        }
        public static GameCard JackOfHearts()
        {
            return new GameCard(GameCardKind.Heart, GameCardValue.Jack);
        }
        public static GameCard TwoOfSpades()
        {
            return new GameCard(GameCardKind.Spade, GameCardValue.Two);
        }
        public static GameCard TenOfDiamonds()
        {
            return new GameCard(GameCardKind.Diamond, GameCardValue.Ten);
        }
    }
}
