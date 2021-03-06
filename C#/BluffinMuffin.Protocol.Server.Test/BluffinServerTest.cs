﻿using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;
using BluffinMuffin.Protocol.Game;
using BluffinMuffin.Protocol.Lobby;
using BluffinMuffin.Protocol.Lobby.QuickMode;
using Com.Ericmas001.Util;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BluffinMuffin.Protocol.Server.Test
{
    [TestClass]
    public class BluffinServerTest
    {
        private StringWriter m_Sw = new StringWriter();
        [TestMethod]
        public void BigUglyTest()
        {
            LogManager.MessageLogged += LogManager_MessageLogged;
            var server = new BluffinServer(42084);
            var tokenServer = new CancellationTokenSource();
            Task.Factory.StartNew(server.Start, tokenServer.Token);

            var client1 = new ClientForTesting();
            var tokenClient1 = new CancellationTokenSource();
            Task.Factory.StartNew(client1.Connect, tokenClient1.Token);

            var c1 = client1.ObtainTcpEntity();
            IdentifyAs(c1, "P1");
            int idTable = CreateTable(c1);
            JoinTable(c1, idTable);
            ReceiveTableInfo(c1);

            var client2 = new ClientForTesting();
            var tokenClient2 = new CancellationTokenSource();
            Task.Factory.StartNew(client2.Connect, tokenClient2.Token);

            var c2 = client2.ObtainTcpEntity();
            IdentifyAs(c2, "P2");
            JoinTable(c2, idTable);
            ReceiveTableInfo(c2);

            BeAwareOfOtherPlayerJoined(c1, c2);

            int s1 = SitInSeat(c1, idTable, 1);
            BeAwareOfOtherPlayerSatIn(c2, idTable, c1, 1);
            int s2 = SitInSeat(c2, idTable, 2);
            BeAwareOfOtherPlayerSatIn(c1, idTable, c2, 2);

            //GameIsStarting !!!
            int b1 = GameIsStarting(c1);
            int b2 = GameIsStarting(c2);

            PlayMoney(c1, idTable, b1);

            BeAwareOfMoneyPlayed(c1, idTable, s1);
            BeAwareOfMoneyPlayed(c2, idTable, s1);

            PlayMoney(c2, idTable, b2);
            BeAwareOfMoneyPlayed(c1, idTable, s2);
            BeAwareOfMoneyPlayed(c2, idTable, s2);

            BeAwareOfHoleCardDistribution(c1, idTable, s1, s2);
            BeAwareOfHoleCardDistribution(c2, idTable, s1, s2);

            BeAwareOfBettingRoundStarted(c1, idTable, RoundTypeEnum.Preflop);
            BeAwareOfBettingRoundStarted(c2, idTable, RoundTypeEnum.Preflop);

            BeAwareOfWhoItIsToPlay(c1, idTable, s1);
            BeAwareOfWhoItIsToPlay(c2, idTable, s1);

            PlayMoney(c1, idTable, 5);

            BeAwareOfMoneyPlayed(c1, idTable, s1);
            BeAwareOfMoneyPlayed(c2, idTable, s1);

            BeAwareOfWhoItIsToPlay(c1, idTable, s2);
            BeAwareOfWhoItIsToPlay(c2, idTable, s2);

            //SitOut(c2, idTable, s2);

            Thread.Sleep(500);

            c1.ReceivedCommands.CompleteAdding();
            c2.ReceivedCommands.CompleteAdding();

            Assert.AreEqual(string.Empty,string.Join(",",c1.ReceivedCommands.GetConsumingEnumerable().Select(x => x.ToString())),m_Sw.ToString());

            Assert.AreEqual(string.Empty, string.Join(",", c2.ReceivedCommands.GetConsumingEnumerable().Select(x => x.ToString())), m_Sw.ToString());

            tokenClient2.Cancel();
            tokenClient1.Cancel();
            tokenServer.Cancel();
        }

        private void SitOut(RemoteTcpServer serverEntity, int tableId, int noSeat)
        {
            var cmd = new PlayerSitOutCommand()
            {
                TableId = tableId
            };
            serverEntity.Send(cmd);
            var response = serverEntity.WaitForNextCommand<PlayerSitOutResponse>();
            Assert.IsTrue(response.Success);
        }

        void LogManager_MessageLogged(string from, string message, int level)
        {
            m_Sw.WriteLine("[{0}] {1}", from, message);
        }

        private void BeAwareOfWhoItIsToPlay(RemoteTcpServer serverEntity, int tableId, int noSeat)
        {
            var response = serverEntity.WaitForNextCommand<PlayerTurnBeganCommand>();
            Assert.AreEqual(tableId, response.TableId);
            Assert.AreEqual(noSeat, response.PlayerPos);
        }

        private void BeAwareOfBettingRoundStarted(RemoteTcpServer serverEntity, int tableId, RoundTypeEnum round)
        {
            var response = serverEntity.WaitForNextCommand<BetTurnStartedCommand>();
            Assert.AreEqual(tableId, response.TableId);
            Assert.AreEqual(round, response.Round);
        }

        private void BeAwareOfHoleCardDistribution(RemoteTcpServer serverEntity, int tableId, params int[] seats)
        {
            HashSet<int> remaining = new HashSet<int>(seats);
            while (remaining.Any())
            {
                var response = serverEntity.WaitForNextCommand<PlayerHoleCardsChangedCommand>();
                Assert.AreEqual(tableId, response.TableId);
                Assert.IsTrue(remaining.Contains(response.PlayerPos));
                remaining.Remove(response.PlayerPos);
            }
        }

        private void BeAwareOfMoneyPlayed(RemoteTcpServer serverEntity, int tableId, int seat)
        {
            //var responseMoneyChanged = serverEntity.WaitForNextCommand<PlayerMoneyChangedCommand>();
            //Assert.AreEqual(tableId, responseMoneyChanged.TableId);
            //Assert.AreEqual(seat, responseMoneyChanged.PlayerPos);

            var responseTurnEnded = serverEntity.WaitForNextCommand<PlayerTurnEndedCommand>();
            Assert.AreEqual(tableId, responseTurnEnded.TableId);
            Assert.AreEqual(seat, responseTurnEnded.PlayerPos);
        }

        private void PlayMoney(RemoteTcpServer serverEntity, int tableId, int money)
        {
            var cmd = new PlayerPlayMoneyCommand()
            {
                TableId = tableId,
                Played = money
            };
            serverEntity.Send(cmd);
        }

        private void BeAwareOfOtherPlayerSatIn(RemoteTcpServer serverEntity, int tableId, RemoteTcpServer other, int noSeat)
        {
            var response = serverEntity.WaitForNextCommand<SeatUpdatedCommand>();
            Assert.AreEqual(other.Name, response.Seat.Player.Name);
            Assert.AreEqual(tableId, response.TableId);
            Assert.AreEqual(noSeat, response.Seat.NoSeat);
        }

        private int SitInSeat(RemoteTcpServer serverEntity, int tableId, int noSeat)
        {
            var cmd = new PlayerSitInCommand()
            {
                TableId = tableId,
                MoneyAmount = 1500,
                NoSeat = noSeat
            };
            serverEntity.Send(cmd);
            var response = serverEntity.WaitForNextCommand<PlayerSitInResponse>();
            Assert.AreEqual(noSeat,response.NoSeat);
            return noSeat;
        }

        private void BeAwareOfOtherPlayerJoined(RemoteTcpServer serverEntity, RemoteTcpServer other)
        {
            var response = serverEntity.WaitForNextCommand<PlayerJoinedCommand>();
            Assert.AreEqual(other.Name,response.PlayerName);
        }

        private int GameIsStarting(RemoteTcpServer serverEntity)
        {
            ReceiveTableInfo(serverEntity);
            var response = serverEntity.WaitForNextCommand<GameStartedCommand>();
            Assert.AreNotEqual(0,response.NeededBlind);
            return response.NeededBlind;
        }

        private void ReceiveTableInfo(RemoteTcpServer serverEntity)
        {
            serverEntity.WaitForNextCommand<TableInfoCommand>();
        }

        private void JoinTable(RemoteTcpServer serverEntity, int table)
        {
            var cmd = new JoinTableCommand()
            {
                TableId=table
            };
            serverEntity.Send(cmd);
            var response = serverEntity.WaitForNextCommand<JoinTableResponse>();
            Assert.IsTrue(response.Success);
        }

        private int CreateTable(RemoteTcpServer serverEntity)
        {
            var cmd = new CreateTableCommand()
            {
                Params = new TableParams()
                {
                    Blind = new BlindOptionsBlinds()
                    {
                        MoneyUnit = 10
                    },
                    GameType = GameTypeEnum.Holdem,
                    MoneyUnit = 10,
                    Limit = new LimitOptionsNoLimit(),
                    Lobby = new LobbyOptionsQuickMode()
                    {
                        StartingAmount = 1500
                    },
                    MaxPlayers = 10,
                    MinPlayersToStart = 2,
                    TableName = "Table One",
                    Variant = "Wtf Is this field",
                    WaitingTimes = new ConfigurableWaitingTimes()
                    {
                        AfterBoardDealed = 0,
                        AfterPlayerAction = 0,
                        AfterPotWon = 0
                    }
                }
            };
            serverEntity.Send(cmd);
            var response = serverEntity.WaitForNextCommand<CreateTableResponse>();
            Assert.AreNotEqual(response.IdTable,-1);
            return response.IdTable;
        }

        private void IdentifyAs(RemoteTcpServer serverEntity, string name)
        {
            var cmd = new IdentifyCommand()
            {
                Name = name
            };
            serverEntity.Send(cmd);
            var response = serverEntity.WaitForNextCommand<IdentifyResponse>();
            Assert.IsTrue(response.Success);
            serverEntity.Name = name;
        }
    }
}
