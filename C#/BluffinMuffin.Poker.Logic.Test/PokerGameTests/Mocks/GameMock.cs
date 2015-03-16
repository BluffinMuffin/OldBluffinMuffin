using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks
{
    public static class GameMock
    {
        public static PokerGame GenerateSimple2PlayersBlindsGame()
        {
            return new PokerGame(
                new PokerTable(
                    new TableParams()
                    {
                        MaxPlayers = 2,
                        Blind = new BlindOptionsBlinds()
                        {
                            MoneyUnit = 10
                        }
                    }));
        }
        public static PokerGame StartSimple2PlayersBlindsGame()
        {
            var game = GenerateSimple2PlayersBlindsGame();
            game.Start();

            return game;
        }
        public static PokerGame StartSimple2PlayersBlindsGameAndSitsP1()
        {
            var game = StartSimple2PlayersBlindsGame();
            PlayerMock.GenerateP1Seated(game);

            return game;
        }
        public static PokerGame StartSimple2PlayersBlindsGameAndSitsP1P2()
        {
            var game = StartSimple2PlayersBlindsGameAndSitsP1();
            PlayerMock.GenerateP2Seated(game);

            return game;
        }
    }
}
