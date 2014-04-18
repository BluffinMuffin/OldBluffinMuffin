using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Threading;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Game;
using Com.Ericmas001.Games;
using Com.Ericmas001.Net.Protocol.JSON;
using Com.Ericmas001.Util;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.Logic;
using Com.Ericmas001.Net.Protocol;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;

namespace Com.Ericmas001.Game.Poker.Protocol.Server
{
    public class GameTCPServer : CommandQueueCommunicator<GameObserver>
    {
        #region Fields
        private readonly PlayerInfo m_Player;
        private readonly PokerGame m_Game;
        private readonly int m_ID;
        private readonly UserInfo m_UserInfo;
        #endregion Fields

        #region Events
        public event EventHandler<KeyEventArgs<int>> LeftTable = delegate { };
        #endregion Events

        #region Properties
        public int ID { get { return m_ID; } }
        public PlayerInfo Player { get { return m_Player; } }
        public PokerGame Game { get { return m_Game; } }
        #endregion Properties

        #region Ctors & Init
        public GameTCPServer(int id, PokerGame game, string name, int money)
        {
            m_ID = id;
            m_Game = game;
            m_Player = new PlayerInfo(name, 0);
            m_UserInfo = null;
            base.SendedSomething += OnServerSendedSomething;

        }

        public GameTCPServer(int id, PokerGame game, UserInfo userInfo)
        {
            m_ID = id;
            m_Game = game;
            m_UserInfo = userInfo;
            m_Player = new PlayerInfo(m_UserInfo.DisplayName, 0);
        }

        private void InitializePokerObserver()
        {
            m_Game.Observer.GameBettingRoundEnded += OnGameBettingRoundEnded;
            m_Game.Observer.PlayerHoleCardsChanged += OnPlayerHoleCardsChanged;
            m_Game.Observer.GameEnded += OnGameEnded;
            m_Game.Observer.PlayerWonPot += OnPlayerWonPot;
            m_Game.Observer.PlayerActionTaken += OnPlayerActionTaken;
            m_Game.Observer.PlayerMoneyChanged += OnPlayerMoneyChanged;
            m_Game.Observer.EverythingEnded += OnEverythingEnded;
            m_Game.Observer.PlayerActionNeeded += OnPlayerActionNeeded;
            m_Game.Observer.GameBlindNeeded += OnGameBlindNeeded;
            m_Game.Observer.GameBettingRoundStarted += OnGameBettingRoundStarted;
            m_Game.Observer.GameGenerallyUpdated += OnGameGenerallyUpdated;
            m_Game.Observer.PlayerJoined += OnPlayerJoined;
            m_Game.Observer.SeatUpdated += OnSeatUpdated;
            m_Game.Observer.PlayerLeft += OnPlayerLeft;
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
            List<MoneyPot> pots = m_Game.Table.Pots;
            List<int> amounts = pots.Select(p => p.Amount).ToList();

            for (int i = pots.Count; i < m_Game.Table.Params.MaxPlayers; i++)
                amounts.Add(0);

            Send(new BetTurnEndedCommand() 
            { 
                PotsAmounts = amounts, 
                Round = e.Round,
            });
        }

        void OnPlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            GameCard[] holeCards = p.NoSeat == m_Player.NoSeat ? p.Cards : p.RelativeCards;

            Send(new PlayerHoleCardsChangedCommand() 
            {
                PlayerPos = p.NoSeat, 
                State = p.State,
                CardsID = holeCards.Select(c => c.Id).ToList() ,
            });
        }

        void OnGameEnded(object sender, EventArgs e)
        {
            Send(new GameEndedCommand());
        }

        void OnPlayerWonPot(object sender, PotWonEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerWonPotCommand()
            {
                PlayerPos = p.NoSeat,
                PotID = e.Id,
                Shared = e.AmountWon,
                PlayerMoney = p.MoneySafeAmnt,
            });
        }

        void OnPlayerActionTaken(object sender, PlayerActionEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerTurnEndedCommand()
            {
                PlayerPos = p.NoSeat,
                PlayerBet = p.MoneyBetAmnt,
                PlayerMoney = p.MoneySafeAmnt,
                TotalPot = m_Game.Table.TotalPotAmnt,
                ActionType = e.Action,
                ActionAmount = e.AmountPlayed,
                State = p.State,
            });
        }

        void OnPlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
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
                MinimumRaise = m_Game.Table.MinimumRaiseAmount,
            });
        }

        void OnGameBlindNeeded(object sender, EventArgs e)
        {
            Send(new GameStartedCommand() { NeededBlind = m_Game.GameTable.GetBlindNeeded(m_Player) });
        }

        void OnGameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            Send(new BetTurnStartedCommand()
            {
                Round = e.Round,
                CardsID = m_Game.Table.Cards.Select(x => x.Id).ToList()
            });
        }

        private void OnGameGenerallyUpdated(object sender, EventArgs e)
        {
            SendTableInfo();
        }

        void OnPlayerJoined(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
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
            PlayerInfo p = e.Player;
            Send(new PlayerLeftCommand()
            {
                PlayerPos = p.NoSeat,
            });
        }
        #endregion PokerObserver Event Handling

        #region CommandObserver Event Handling
        void OnPlayMoneyCommandReceived(object sender, CommandEventArgs<PlayerPlayMoneyCommand> e)
        {
            m_Game.PlayMoney(m_Player, e.Command.Played);
        }

        void OnSitOutCommandReceived(object sender, CommandEventArgs<PlayerSitOutCommand> e)
        {
            Send(e.Command.EncodeResponse(m_Game.SitOut(m_Player)));
        }

        void OnSitInCommandReceived(object sender, CommandEventArgs<PlayerSitInCommand> e)
        {
            TableParams parms = m_Game.Table.Params;
            int money;
            if(parms.Lobby.OptionType == LobbyTypeEnum.Training)
                money = ((LobbyOptionsTraining)parms.Lobby).StartingAmount;
            else
            {
                money = e.Command.MoneyAmount;
                if (m_UserInfo == null || m_UserInfo.TotalMoney < money || money < parms.LimitedMinimumBuyIn || (parms.LimitMaximumBuyIn && money > parms.LimitedMaximumBuyIn))
                {
                    Send(e.Command.EncodeResponse(-1));
                    return;
                }
                m_UserInfo.TotalMoney -= money;
            }

            m_Player.MoneySafeAmnt = money;
            SeatInfo seat = m_Game.GameTable.AskToSitIn(m_Player, e.Command.NoSeat);
            Send(e.Command.EncodeResponse(seat == null ? -1 : seat.NoSeat));
            if( seat != null)
                m_Game.SitIn(m_Player);
        }

        void OnDisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            if (m_UserInfo != null && m_Game.Params.Lobby.OptionType == LobbyTypeEnum.Career)
                m_UserInfo.TotalMoney += m_Player.MoneySafeAmnt;

            LeftTable(this, new KeyEventArgs<int>(m_ID));

            m_IsConnected = false;
            m_Player.State = PlayerStateEnum.Zombie;

            TableInfo t = m_Game.Table;
            LogManager.Log(LogLevel.Message, "GameServer.m_CommandObserver_DisconnectCommandReceived", "> Client '{0}' left table: {2}:{1}", m_Player.Name, t.Params.TableName, m_ID);

            if (m_Game.State == GameStateEnum.WaitForPlayers)
                m_Game.LeaveGame(m_Player);
            else if (t.NoSeatCurrentPlayer == m_Player.NoSeat)
            {
                if (t.CanCheck(m_Player))
                    m_Game.PlayMoney(m_Player, 0);
                else
                    m_Game.PlayMoney(m_Player, -1);
            }
        }

        void OnCommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageLow, "OnCommandReceived", "<Game:{0}> RECV [{1}]", m_Player.Name, e.Str);
        }
        #endregion CommandObserver Event Handling

        #region Public Methods
        public bool CanStartGame
        {
            get
            {
                return m_IsConnected && m_Player.CanPlay;
            }
        }
        public void SendTableInfo()
        {
            TableInfoCommand cmd = new TableInfoCommand();
            cmd.GameHasStarted = m_Game.IsPlaying;
            PokerTable table = m_Game.GameTable;
            lock (table)
            {
                PlayerInfo playerSendingTo = m_Player;

                cmd.BoardCardIDs = table.Cards.Select(c => c.Id).ToList();
                cmd.Seats = new List<SeatInfo>();

                cmd.Params = table.Params;

                cmd.TotalPotAmount = table.TotalPotAmnt;
                cmd.NbPlayers = cmd.Params.MaxPlayers;

                cmd.PotsAmount = table.Pots.Select(pot => pot.Amount).ToList();
                cmd.PotsAmount.AddRange(Enumerable.Repeat(0, cmd.Params.MaxPlayers - table.Pots.Count));

                for (int i = 0; i < cmd.Params.MaxPlayers; ++i)
                {
                    SeatInfo si = new SeatInfo() { NoSeat = i };
                    cmd.Seats.Add(si);
                    SeatInfo gameSeat = table.Seats[i];
                    if (gameSeat.IsEmpty)
                        continue;
                    si.Player = gameSeat.Player.Clone();

                    //If we are not sending the info about the player who is receiving, don't show the cards unless you can
                    if (i != playerSendingTo.NoSeat && si.Player.IsPlaying)
                        si.Player.HoleCards = gameSeat.Player.RelativeCards;

                    if (si.Player.HoleCards.Length != 2)
                        si.Player.HoleCards = new GameCard[] { GameCard.NO_CARD, GameCard.NO_CARD };

                    si.Attributes = gameSeat.Attributes;
                }
            }
            Send(cmd);
        }

        public bool JoinGame()
        {
            InitializePokerObserver();
            return m_Game.JoinGame(m_Player);
        }
        #endregion Public Methods
    }
}
