﻿using BluffinMuffin.Poker.Logic.Test.PokerGameTests.DataTypes;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Poker.Logic.Test.PokerGameTests.Mocks
{
    public static class Simple2PlayersBlindsGameMock
    {
        public static GameInfo Empty()
        {
            return new GameInfo()
            {
                Game = new PokerGame(
                    new PokerTable(
                        new TableParams()
                        {
                            MaxPlayers = 2,
                            MoneyUnit = 10,
                            Blind = new BlindOptionsBlinds()
                            {
                                MoneyUnit = 10
                            },
                            Lobby = new LobbyOptionsRegisteredMode()
                            {
                                IsMaximumBuyInLimited = false,
                                MoneyUnit = 0 // Little trick to not get bothered.
                            }
                        }))
            };
        }
        public static GameInfo EmptyWithBuyInsSetted()
        {
            return new GameInfo()
            {
                Game = new PokerGame(
                    new PokerTable(
                        new TableParams()
                        {
                            MaxPlayers = 2,
                            MoneyUnit = 10,
                            Blind = new BlindOptionsBlinds()
                            {
                                MoneyUnit = 10
                            },
                            Lobby = new LobbyOptionsRegisteredMode()
                            {
                                IsMaximumBuyInLimited = true,
                                MoneyUnit=10
                            }
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
        public static GameInfo WithBothPlayersSeated()
        {
            var nfo = WithOnlyP1Seated();
            nfo.P2 = PlayerMock.GenerateP2Seated(nfo);

            return nfo;
        }
        public static GameInfo BlindsPosted()
        {
            var nfo = WithBothPlayersSeated();

            nfo.PutBlinds(nfo.CalculatedSmallBlind);
            nfo.PutBlinds(nfo.CalculatedBigBlind);

            return nfo;
        }
        public static GameInfo AfterPreflop()
        {
            var nfo = BlindsPosted();

            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();

            return nfo;
        }
        public static GameInfo AfterFlop()
        {
            var nfo = AfterPreflop();

            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();

            return nfo;
        }
        public static GameInfo AfterTurn()
        {
            var nfo = AfterFlop();

            nfo.CurrentPlayerCalls();
            nfo.CurrentPlayerCalls();

            return nfo;
        }
    }
}
