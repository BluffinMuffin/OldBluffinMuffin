using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BluffinMuffin.Poker.DataTypes.Parameters;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.DataTypes;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.Helpers;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks
{
    public static class GameMock
    {
        public static GameInfo Simple2PlayersBlindsGameEmpty()
        {
            return new GameInfo()
            {
                Game = new PokerGame(
                    new PokerTable(
                        new TableParams()
                        {
                            MaxPlayers = 2,
                            Blind = new BlindOptionsBlinds()
                            {
                                MoneyUnit = 10
                            }
                        }))
            };
        }
        public static GameInfo Simple2PlayersBlindsGameEmptyButStarted()
        {
            var nfo = Simple2PlayersBlindsGameEmpty();
            nfo.Game.Start();

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameWithP1Seated()
        {
            var nfo = Simple2PlayersBlindsGameEmptyButStarted();
            nfo.P1 = PlayerMock.GenerateP1Seated(nfo.Game);

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameWithBothSeated()
        {
            var nfo = Simple2PlayersBlindsGameWithP1Seated();
            nfo.P2 = PlayerMock.GenerateP2Seated(nfo.Game);

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameBlindsPosted()
        {
            var nfo = Simple2PlayersBlindsGameWithBothSeated();

            PlayerHelper.PutBlinds(nfo.Game, nfo.P1);
            PlayerHelper.PutBlinds(nfo.Game, nfo.P2);

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameAfterPreflop()
        {
            var nfo = Simple2PlayersBlindsGameBlindsPosted();

            GameHelper.CurrentPlayerCalls(nfo.Game);
            GameHelper.CurrentPlayerCalls(nfo.Game);

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameAfterFlop()
        {
            var nfo = Simple2PlayersBlindsGameAfterPreflop();

            GameHelper.CurrentPlayerCalls(nfo.Game);
            GameHelper.CurrentPlayerCalls(nfo.Game);

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameAfterTurn()
        {
            var nfo = Simple2PlayersBlindsGameAfterFlop();

            GameHelper.CurrentPlayerCalls(nfo.Game);
            GameHelper.CurrentPlayerCalls(nfo.Game);

            return nfo;
        }
        public static GameInfo Simple2PlayersBlindsGameAfterRiver()
        {
            var nfo = Simple2PlayersBlindsGameAfterTurn();

            GameHelper.CurrentPlayerCalls(nfo.Game);
            GameHelper.CurrentPlayerCalls(nfo.Game);

            return nfo;
        }
    }
}
