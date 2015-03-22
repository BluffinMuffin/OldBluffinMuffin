using BluffinMuffin.Poker.DataTypes.Parameters;
using BluffinMuffin.Poker.Logic.Test.PokerGameTests.DataTypes;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks
{
    public static class Simple4PlayersNoBlindsGameMock
    {
        public static GameInfo Empty()
        {
            return new GameInfo()
            {
                Game = new PokerGame(
                    new PokerTable(
                        new TableParams()
                        {
                            MaxPlayers = 4,
                            MinPlayersToStart = 4,
                            Blind = new BlindOptionsNone()
                        }))
            };
        }
        public static GameInfo EmptyButStarted()
        {
            var nfo = Empty();
            nfo.Game.Start();

            return nfo;
        }
        public static GameInfo WithOnlyP1Seated()
        {
            var nfo = EmptyButStarted();
            nfo.P1 = PlayerMock.GenerateP1Seated(nfo);

            return nfo;
        }
        public static GameInfo WithOnlyP1P2Seated()
        {
            var nfo = WithOnlyP1Seated();
            nfo.P2 = PlayerMock.GenerateP2Seated(nfo);

            return nfo;
        }
        public static GameInfo WithOnlyP1P2P3Seated()
        {
            var nfo = WithOnlyP1P2Seated();
            nfo.P3 = PlayerMock.GenerateP3Seated(nfo);

            return nfo;
        }
        public static GameInfo WithAllPlayersSeated()
        {
            var nfo = WithOnlyP1P2P3Seated();
            nfo.P4 = PlayerMock.GenerateP4Seated(nfo);

            return nfo;
        }
        public static GameInfo AfterPreflop()
        {
            var nfo = WithAllPlayersSeated();

            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();

            return nfo;
        }
        public static GameInfo AfterFlop()
        {
            var nfo = AfterPreflop();

            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();

            return nfo;
        }
        public static GameInfo AfterTurn()
        {
            var nfo = AfterFlop();

            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();

            return nfo;
        }
    }
}
