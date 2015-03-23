using System;
using System.Collections.Generic;
using System.Linq;
using BluffinMuffin.Protocol.Commands;
using BluffinMuffin.Protocol.Commands.Game;
using Com.Ericmas001.Games;
using Com.Ericmas001.Util;
using BluffinMuffin.Poker.DataTypes.Enums;
using BluffinMuffin.Poker.DataTypes.EventHandling;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Logic;
using Com.Ericmas001.Net.Protocol;
using BluffinMuffin.Poker.DataTypes.Parameters;

namespace BluffinMuffin.Protocol.Server
{
    public class GameTcpServer : CommandQueueCommunicator<GameObserver>
    {
        #region Fields
        private readonly PlayerInfo m_Player;
        private readonly PokerGame m_Game;
        private readonly int m_Id;
        #endregion Fields

        #region Events
        public event EventHandler<KeyEventArgs<int>> LeftTable = delegate { };
        #endregion Events

        #region Properties
        public int Id { get { return m_Id; } }
        public PlayerInfo Player { get { return m_Player; } }

        protected PokerGame Game
        {
            get { return m_Game; }
        }

        #endregion Properties

        #region Ctors & Init
        public GameTcpServer(int id, PokerGame game, string name)
        {
            m_Id = id;
            m_Game = game;
            m_Player = new PlayerInfo(name, 0);
            SendedSomething += OnServerSendedSomething;

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
            Game.Observer.PlayerLeft += OnPlayerLeft;
        }

        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += OnCommandReceived;
            m_CommandObserver.DisconnectCommandReceived += OnDisconnectCommandReceived;
            m_CommandObserver.PlayMoneyCommandReceived += OnPlayMoneyCommandReceived;
            m_CommandObserver.SitInCommandReceived += OnSitInCommandReceived;
            m_CommandObserver.SitOutCommandReceived += OnSitOutCommandReceived;
        }
        #endregion Ctors & Init

        #region GameServer Event Handling
        void OnServerSendedSomething(object sender, KeyEventArgs<string> e)
        {
            LogManager.Log(LogLevel.MessageLow, "OnServerSendedSomething", "<Game:{0}> SEND [{1}]", m_Player.Name, e.Key);
        }
        #endregion GameServer Event Handling

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
            var holeCards = p.NoSeat == m_Player.NoSeat ? p.Cards : p.RelativeCards;

            Send(new PlayerHoleCardsChangedCommand() 
            {
                PlayerPos = p.NoSeat, 
                State = p.State,
                CardsId = holeCards.Select(c => c.Id).ToList() ,
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
            var p = e.Player;
            Send(new PlayerMoneyChangedCommand()
            {
                PlayerPos = p.NoSeat,
                PlayerMoney = p.MoneySafeAmnt,
            });
        }

        void OnEverythingEnded(object sender, EventArgs e)
        {
            Send(new TableClosedCommand());
            m_IsConnected = false;
        }

        void OnPlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            Send(new PlayerTurnBeganCommand()
            {
                PlayerPos = e.Player.NoSeat,
                LastPlayerNoSeat = e.Last.NoSeat,
                MinimumRaise = Game.Table.MinimumRaiseAmount,
            });
        }

        void OnGameBlindNeeded(object sender, EventArgs e)
        {
            Send(new GameStartedCommand() { NeededBlind = Game.GameTable.GetBlindNeeded(m_Player) });
        }

        void OnGameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            Send(new BetTurnStartedCommand()
            {
                Round = e.Round,
                CardsId = Game.Table.Cards.Select(x => x.Id).ToList()
            });
        }

        private void OnGameGenerallyUpdated(object sender, EventArgs e)
        {
            SendTableInfo();
        }

        void OnPlayerJoined(object sender, PlayerInfoEventArgs e)
        {
            var p = e.Player;
            Send(new PlayerJoinedCommand()
            {
                PlayerName = p.Name,
            });
        }

        void OnSeatUpdated(object sender, SeatEventArgs e)
        {
            if (!e.Seat.IsEmpty && m_Player.NoSeat != e.Seat.NoSeat)
                e.Seat.Player.HoleCards = e.Seat.Player.RelativeCards;

            Send(new SeatUpdatedCommand()
            {
                Seat = e.Seat,
            });
        }

        void OnPlayerLeft(object sender, PlayerInfoEventArgs e)
        {
            var p = e.Player;
            Send(new PlayerLeftCommand()
            {
                PlayerPos = p.NoSeat,
            });
        }
        #endregion PokerObserver Event Handling

        #region CommandObserver Event Handling
        void OnPlayMoneyCommandReceived(object sender, CommandEventArgs<PlayerPlayMoneyCommand> e)
        {
            Game.PlayMoney(m_Player, e.Command.Played);
        }

        void OnSitOutCommandReceived(object sender, CommandEventArgs<PlayerSitOutCommand> e)
        {
            Send(e.Command.EncodeResponse(Game.SitOut(m_Player)));
        }

        protected virtual int GetStartingMoney(CommandEventArgs<PlayerSitInCommand> e)
        {
            return ((LobbyOptionsTraining) Game.Params.Lobby).StartingAmount;
        }

        void OnSitInCommandReceived(object sender, CommandEventArgs<PlayerSitInCommand> e)
        {
            m_Player.MoneySafeAmnt = GetStartingMoney(e);
            var seat = Game.GameTable.SitIn(m_Player, e.Command.NoSeat);
            if (seat == null)
            {
                Send(e.Command.EncodeResponse(-1));
                WhatToDoWithExtraMoneyWhenLeaving(m_Player.MoneySafeAmnt);
            }
            else
            {
                Send(e.Command.EncodeResponse(seat.NoSeat));
                Game.AfterPlayerSat(m_Player);
            }
        }

        protected virtual void WhatToDoWithExtraMoneyWhenLeaving( int money)
        {
        }

        void OnDisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            WhatToDoWithExtraMoneyWhenLeaving(m_Player.MoneySafeAmnt);

            LeftTable(this, new KeyEventArgs<int>(m_Id));

            m_IsConnected = false;
            m_Player.State = PlayerStateEnum.Zombie;

            var t = Game.Table;
            LogManager.Log(LogLevel.Message, "GameServer.m_CommandObserver_DisconnectCommandReceived", "> Client '{0}' left table: {2}:{1}", m_Player.Name, t.Params.TableName, m_Id);

            Game.LeaveGame(m_Player);
        }

        void OnCommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageLow, "OnCommandReceived", "<Game:{0}> RECV [{1}]", m_Player.Name, e.Str);
        }
        #endregion CommandObserver Event Handling

        #region Public Methods
        public void SendTableInfo()
        {
            var cmd = new TableInfoCommand
            {
                GameHasStarted = Game.IsPlaying
            };
            var table = Game.GameTable;
            lock (table)
            {
                var playerSendingTo = m_Player;

                cmd.BoardCardIDs = table.Cards.Select(c => c.Id).ToList();
                cmd.Seats = new List<SeatInfo>();

                cmd.Params = table.Params;

                cmd.TotalPotAmount = table.TotalPotAmnt;
                cmd.NbPlayers = cmd.Params.MaxPlayers;

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
                        si.Player.HoleCards = gameSeat.Player.RelativeCards;

                    if (si.Player.HoleCards.Length != 2)
                        si.Player.HoleCards = new[] { GameCard.NoCard, GameCard.NoCard };

                    si.Attributes = gameSeat.Attributes;
                }
            }
            Send(cmd);
        }

        public bool JoinGame()
        {
            InitializePokerObserver();
            return Game.JoinGame(m_Player);
        }
        #endregion Public Methods
    }
}
