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

namespace Com.Ericmas001.Game.Poker.Protocol.Server
{
    public class GameServer : CommandQueueCommunicator<GameServerCommandObserver>
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
        public GameServer(int id, PokerGame game, string name, int money)
        {
            m_ID = id;
            m_Game = game;
            m_Player = new PlayerInfo(name, money);
            m_UserInfo = null;
            base.SendedSomething += new EventHandler<KeyEventArgs<string>>(GameServer_SendedSomething);

        }

        public GameServer(int id, PokerGame game, UserInfo userInfo)
        {
            m_ID = id;
            m_Game = game;
            m_UserInfo = userInfo;
            int money = (int)m_UserInfo.TotalMoney;
            m_UserInfo.TotalMoney -= money;
            m_Player = new PlayerInfo(m_UserInfo.DisplayName, money);
        }

        private void InitializePokerObserver()
        {
            m_Game.GameBettingRoundEnded += new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundEnded);
            m_Game.PlayerHoleCardsChanged += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerHoleCardsChanged);
            m_Game.GameEnded += new EventHandler(m_Game_GameEnded);
            m_Game.PlayerWonPot += new EventHandler<PotWonEventArgs>(m_Game_PlayerWonPot);
            m_Game.PlayerActionTaken += new EventHandler<PlayerActionEventArgs>(m_Game_PlayerActionTaken);
            m_Game.PlayerMoneyChanged += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerMoneyChanged);
            m_Game.EverythingEnded += new EventHandler(m_Game_EverythingEnded);
            m_Game.PlayerActionNeeded += new EventHandler<HistoricPlayerInfoEventArgs>(m_Game_PlayerActionNeeded);
            m_Game.GameBlindNeeded += new EventHandler(m_Game_GameBlindNeeded);
            m_Game.GameBettingRoundStarted += new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundStarted);
            m_Game.PlayerJoined += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerJoined);
            m_Game.PlayerLeaved += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerLeaved);
        }

        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += new EventHandler<StringEventArgs>(m_CommandObserver_CommandReceived);
            m_CommandObserver.DisconnectCommandReceived += new EventHandler<CommandEventArgs<DisconnectCommand>>(m_CommandObserver_DisconnectCommandReceived);
            m_CommandObserver.PlayMoneyCommandReceived += new EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>>(m_CommandObserver_PlayMoneyCommandReceived);
        }
        #endregion Ctors & Init

        #region GameServer Event Handling
        void GameServer_SendedSomething(object sender, KeyEventArgs<string> e)
        {
            LogManager.Log(LogLevel.MessageLow, "GameServer.GameServer_SendedSomething", "<Game:{0}> SEND [{1}]", m_Player.Name, e.Key);
        }
        #endregion GameServer Event Handling

        #region PokerObserver Event Handling
        void m_Game_GameBettingRoundEnded(object sender, RoundEventArgs e)
        {
            List<MoneyPot> pots = m_Game.Table.Pots;
            List<int> amounts = pots.Select(p => p.Amount).ToList();

            for (int i = pots.Count; i < m_Game.Table.Rules.MaxPlayers; i++)
                amounts.Add(0);

            Send(new BetTurnEndedCommand() 
            { 
                PotsAmounts = amounts, 
                Round = e.Round,
            });
        }

        void m_Game_PlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
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

        void m_Game_GameEnded(object sender, EventArgs e)
        {
            Send(new GameEndedCommand());
        }

        void m_Game_PlayerWonPot(object sender, PotWonEventArgs e)
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

        void m_Game_PlayerActionTaken(object sender, PlayerActionEventArgs e)
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

        void m_Game_PlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerMoneyChangedCommand()
            {
                PlayerPos = p.NoSeat,
                PlayerMoney = p.MoneySafeAmnt,
            });
        }

        void m_Game_EverythingEnded(object sender, EventArgs e)
        {
            Send(new TableClosedCommand());
            m_IsConnected = false;
        }

        void m_Game_PlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            Send(new PlayerTurnBeganCommand()
            {
                PlayerPos = e.Player.NoSeat,
                LastPlayerNoSeat = e.Last.NoSeat,
                MinimumRaise = m_Game.Table.MinimumRaiseAmount,
            });
        }

        void m_Game_GameBlindNeeded(object sender, EventArgs e)
        {
            TableInfo t = m_Game.Table;
            Send(new GameStartedCommand()
            {
                NoSeatD = t.NoSeatDealer,
                NoSeatSB = t.NoSeatSmallBlind,
                NoSeatBB = t.NoSeatBigBlind,
            });
        }

        void m_Game_GameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            Send(new BetTurnStartedCommand()
            {
                Round = e.Round,
                CardsID = m_Game.Table.Cards.Select(x => x.Id).ToList()
            });
        }

        void m_Game_PlayerJoined(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerJoinedCommand()
            {
                PlayerPos = p.NoSeat,
                PlayerName = p.Name,
                PlayerMoney = p.MoneySafeAmnt,
            });
        }

        void m_Game_PlayerLeaved(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerLeftCommand()
            {
                PlayerPos = p.NoSeat,
            });
        }
        #endregion PokerObserver Event Handling

        #region CommandObserver Event Handling
        void m_CommandObserver_PlayMoneyCommandReceived(object sender, CommandEventArgs<PlayerPlayMoneyCommand> e)
        {
            m_Game.PlayMoney(m_Player, e.Command.Played);
        }

        void m_CommandObserver_DisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            if (m_UserInfo != null && m_Game.Rules.CurrentLobby.LobbyType == LobbyTypeEnum.Career)
                m_UserInfo.TotalMoney += m_Player.MoneySafeAmnt;

            LeftTable(this, new KeyEventArgs<int>(m_ID));

            m_IsConnected = false;
            m_Player.State = PlayerStateEnum.Zombie;

            TableInfo t = m_Game.Table;
            LogManager.Log(LogLevel.Message, "GameServer.m_CommandObserver_DisconnectCommandReceived", "> Client '{0}' left table: {2}:{1}", m_Player.Name, t.Rules.TableName, m_ID);

            if (m_Game.State == GameStateEnum.WaitForPlayers)
                m_Game.LeaveGame(m_Player);
            else if (t.NoSeatCurrPlayer == m_Player.NoSeat)
            {
                if (t.CanCheck(m_Player))
                    m_Game.PlayMoney(m_Player, 0);
                else
                    m_Game.PlayMoney(m_Player, -1);
            }
        }

        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageLow, "GameServer.m_CommandObserver_CommandReceived", "<Game:{0}> RECV [{1}]", m_Player.Name, e.Str);
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
        public void SitIn()
        {
            Send(new TableInfoCommand(m_Game.Table, m_Player));
            m_Game.SitInGame(m_Player);
        }

        public bool JoinGame()
        {
            InitializePokerObserver();
            return m_Game.JoinGame(m_Player);
        }
        #endregion Public Methods
    }
}
