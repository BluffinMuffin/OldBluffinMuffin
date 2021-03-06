﻿using System;
using System.Collections.Generic;
using System.Linq;
using BluffinMuffin.Poker.DataTypes.EventHandling;
using BluffinMuffin.Poker.Logic;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.Game;
using BluffinMuffin.Protocol.Server.DataTypes;
using Com.Ericmas001.Games;

namespace BluffinMuffin.Protocol.Server
{
    public class RemotePlayer
    {
        public PokerGame Game { get; private set; }
        public PlayerInfo Player { get; private set; }
        public IBluffinClient Client { get; private set; }
        public int TableId { get; private set; }

        public RemotePlayer(PokerGame game, PlayerInfo player, IBluffinClient client, int tableId)
        {
            Game = game;
            Player = player;
            Client = client;
            TableId = tableId;
        }

        public bool JoinGame()
        {
            InitializePokerObserver();
            return Game.JoinGame(Player);
        }
        public void SendTableInfo()
        {
            var cmd = new TableInfoCommand
            {
                GameHasStarted = Game.IsPlaying
            };
            var table = Game.GameTable;
            lock (table)
            {
                var playerSendingTo = Player;

                cmd.BoardCards = table.Cards.Select(c => c.ToString()).ToArray();
                cmd.Seats = new List<SeatInfo>();

                cmd.Params = table.Params;

                cmd.TotalPotAmount = table.TotalPotAmnt;

                cmd.PotsAmount = table.PotAmountsPadded.ToList();

                for (var i = 0; i < cmd.Params.MaxPlayers; ++i)
                {
                    var si = new SeatInfo() { NoSeat = i };
                    cmd.Seats.Add(si);
                    var gameSeat = table.Seats[i];
                    if (gameSeat.IsEmpty)
                        continue;
                    si.Player = gameSeat.Player.Clone();

                    //If we are not sending the info about the player who is receiving, don't show the cards unless you can
                    if (i != playerSendingTo.NoSeat && si.Player.IsPlaying)
                        si.Player.HoleCards = gameSeat.Player.RelativeCards.Select(x => x.ToString()).ToArray();

                    if (si.Player.HoleCards.Length != 2)
                        si.Player.HoleCards = new[] { GameCard.NoCard.ToString(), GameCard.NoCard.ToString() };

                    si.Attributes = gameSeat.Attributes;
                }
            }
            Send(cmd);
        }

        private void InitializePokerObserver()
        {
            Game.Observer.GameBettingRoundEnded += OnGameBettingRoundEnded;
            Game.Observer.PlayerHoleCardsChanged += OnPlayerHoleCardsChanged;
            Game.Observer.GameEnded += OnGameEnded;
            Game.Observer.PlayerWonPot += OnPlayerWonPot;
            Game.Observer.PlayerActionTaken += OnPlayerActionTaken;
            Game.Observer.PlayerMoneyChanged += OnPlayerMoneyChanged;
            Game.Observer.EverythingEnded += OnEverythingEnded;
            Game.Observer.PlayerActionNeeded += OnPlayerActionNeeded;
            Game.Observer.GameBlindNeeded += OnGameBlindNeeded;
            Game.Observer.GameBettingRoundStarted += OnGameBettingRoundStarted;
            Game.Observer.GameGenerallyUpdated += OnGameGenerallyUpdated;
            Game.Observer.PlayerJoined += OnPlayerJoined;
            Game.Observer.SeatUpdated += OnSeatUpdated;
        }
        #region PokerObserver Event Handling
        void OnGameBettingRoundEnded(object sender, RoundEventArgs e)
        {
            Send(new BetTurnEndedCommand()
            {
                PotsAmounts = Game.Table.PotAmountsPadded.ToList(),
                Round = e.Round,
            });
        }

        void OnPlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
        {
            var p = e.Player;
            var holeCards = p.NoSeat == Player.NoSeat ? p.Cards : p.RelativeCards;

            Send(new PlayerHoleCardsChangedCommand()
            {
                PlayerPos = p.NoSeat,
                State = p.State,
                Cards = holeCards.Select(c => c.ToString()).ToArray(),
            });
        }

        void OnGameEnded(object sender, EventArgs e)
        {
            Send(new GameEndedCommand());
        }

        void OnPlayerWonPot(object sender, PotWonEventArgs e)
        {
            var p = e.Player;
            Send(new PlayerWonPotCommand()
            {
                PlayerPos = p.NoSeat,
                PotId = e.Id,
                Shared = e.AmountWon,
                PlayerMoney = p.MoneySafeAmnt,
            });
        }

        void OnPlayerActionTaken(object sender, PlayerActionEventArgs e)
        {
            var p = e.Player;
            Send(new PlayerTurnEndedCommand()
            {
                PlayerPos = p.NoSeat,
                PlayerBet = p.MoneyBetAmnt,
                PlayerMoney = p.MoneySafeAmnt,
                TotalPot = Game.Table.TotalPotAmnt,
                ActionType = e.Action,
                ActionAmount = e.AmountPlayed,
                State = p.State,
            });
        }

        void OnPlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            //var p = e.Player;
            //Send(new PlayerMoneyChangedCommand()
            //{
            //    PlayerPos = p.NoSeat,
            //    PlayerMoney = p.MoneySafeAmnt,
            //});
        }

        void OnEverythingEnded(object sender, EventArgs e)
        {
            Send(new TableClosedCommand());
        }

        void OnPlayerActionNeeded(object sender, PlayerInfoEventArgs e)
        {
            Send(new PlayerTurnBeganCommand()
            {
                PlayerPos = e.Player.NoSeat,
                MinimumRaise = Game.Table.MinimumRaiseAmount,
            });
        }

        void OnGameBlindNeeded(object sender, EventArgs e)
        {
            Send(new GameStartedCommand() { NeededBlind = Game.GameTable.GetBlindNeeded(Player) });
        }

        void OnGameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            Send(new BetTurnStartedCommand()
            {
                Round = e.Round,
                Cards = Game.Table.Cards.Select(x => x.ToString()).ToArray()
            });
        }

        private void OnGameGenerallyUpdated(object sender, EventArgs e)
        {
            SendTableInfo();
        }

        void OnPlayerJoined(object sender, PlayerInfoEventArgs e)
        {
            var p = e.Player;
            if(p != Player)
                Send(new PlayerJoinedCommand()
                {
                    PlayerName = p.Name,
                });
        }

        void OnSeatUpdated(object sender, SeatEventArgs e)
        {
            if (e.Seat.IsEmpty || Player.NoSeat != e.Seat.NoSeat)
            {
                if (!e.Seat.IsEmpty && Player.NoSeat != e.Seat.NoSeat)
                    e.Seat.Player.HoleCards = e.Seat.Player.RelativeCards.Select(x => x.ToString()).ToArray();

                Send(new SeatUpdatedCommand()
                {
                    Seat = e.Seat,
                });
            }
        }
        #endregion PokerObserver Event Handling

        private void Send(AbstractGameCommand c)
        {
            c.TableId = TableId;
            Client.SendCommand(c);
        }
    }
}
