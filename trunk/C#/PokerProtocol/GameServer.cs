using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Threading;
using PokerWorld.Game;
using PokerProtocol.Observer;
using PokerProtocol.Commands;
using PokerProtocol.Commands.Game;
using EricUtility.Games.CardGame;
using EricUtility.Networking.Commands;
using PokerWorld.Data;
using EricUtility;

namespace PokerProtocol
{
    public class GameServer : CommandQueueCommunicator<GameServerCommandObserver>
    {
        private readonly PlayerInfo m_Player;
        private readonly PokerGame m_Game;
        private readonly int m_ID;
        private readonly UserInfo m_UserInfo;
        public event EventHandler<KeyEventArgs<int>> LeftTable = delegate { };

        public int ID
        {
            get { return m_ID; }
        }
        public PlayerInfo Player
        {
            get { return m_Player; }
        }
        public PokerGame Game
        {
            get { return m_Game; }
        } 

        public GameServer(int id, PokerGame game, string name, int money)
        {
            m_ID = id;
            m_Game = game;
            m_Player = new PlayerInfo(name, money);
            m_UserInfo = null;
            base.SendedSomething += new EventHandler<EricUtility.KeyEventArgs<string>>(GameServer_SendedSomething);

        }

        public GameServer(int id, PokerGame game, PokerWorld.Data.UserInfo userInfo)
        {
            m_ID = id;
            m_Game = game;
            m_UserInfo = userInfo;
            int money = (int)m_UserInfo.TotalMoney;
            m_UserInfo.TotalMoney -= money;
            m_Player = new PlayerInfo(m_UserInfo.DisplayName,money);
        }

        void GameServer_SendedSomething(object sender, EricUtility.KeyEventArgs<string> e)
        {
            LogManager.Log(LogLevel.MessageLow, "GameServer.GameServer_SendedSomething", "<Game:{0}> SEND [{1}]", m_Player.Name, e.Key);
        }

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

        void m_Game_GameBettingRoundEnded(object sender, RoundEventArgs e)
        {
            List<MoneyPot> pots = new List<MoneyPot>(m_Game.Table.Pots);
            List<int> amounts = new List<int>();
            foreach (MoneyPot pot in pots)
            {
                amounts.Add(pot.Amount);
            }

            for (int i = pots.Count; i < m_Game.Table.NbMaxSeats; i++)
            {
                amounts.Add(0);
            }
            Send(new BetTurnEndedCommand(amounts, e.Round));
        }

        void m_Game_PlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            GameCard[] holeCards;
            if (p.NoSeat == m_Player.NoSeat)
                holeCards = p.Cards;
            else
                holeCards = p.RelativeCards;
            Send(new PlayerHoleCardsChangedCommand(p.NoSeat, p.IsPlaying, holeCards[0].Id, holeCards[1].Id));
        }

        void m_Game_GameEnded(object sender, EventArgs e)
        {
            Send(new GameEndedCommand());
        }

        void m_Game_PlayerWonPot(object sender, PotWonEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerWonPotCommand(p.NoSeat, e.Id, e.AmountWon, p.MoneySafeAmnt));
        }

        void m_Game_PlayerActionTaken(object sender, PlayerActionEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerTurnEndedCommand(p.NoSeat, p.MoneyBetAmnt, p.MoneySafeAmnt, m_Game.Table.TotalPotAmnt, e.Action, e.AmountPlayed, p.IsPlaying));
        }

        void m_Game_PlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerMoneyChangedCommand(p.NoSeat, p.MoneySafeAmnt));
        }

        void m_Game_EverythingEnded(object sender, EventArgs e)
        {
            Send(new TableClosedCommand());
            m_IsConnected = false;
        }

        void m_Game_PlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerTurnBeganCommand(p.NoSeat, e.Last.NoSeat));
        }

        void m_Game_GameBlindNeeded(object sender, EventArgs e)
        {
            TableInfo t = m_Game.Table;
            Send(new GameStartedCommand(t.NoSeatDealer, t.NoSeatSmallBlind, t.NoSeatBigBlind));
        }

        void m_Game_GameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            GameCard[] c = m_Game.Table.Cards;
            Send(new BetTurnStartedCommand(e.Round, c[0].Id, c[1].Id, c[2].Id, c[3].Id, c[4].Id));
        }

        void m_Game_PlayerJoined(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerJoinedCommand(p.NoSeat, p.Name, p.MoneySafeAmnt));
        }

        void m_Game_PlayerLeaved(object sender, PlayerInfoEventArgs e)
        {
            PlayerInfo p = e.Player;
            Send(new PlayerLeftCommand(p.NoSeat));
        }


        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += new EventHandler<StringEventArgs>(m_CommandObserver_CommandReceived);
            m_CommandObserver.DisconnectCommandReceived += new EventHandler<CommandEventArgs<DisconnectCommand>>(m_CommandObserver_DisconnectCommandReceived);
            m_CommandObserver.PlayMoneyCommandReceived += new EventHandler<CommandEventArgs<PlayerPlayMoneyCommand>>(m_CommandObserver_PlayMoneyCommandReceived);
        }

        void m_CommandObserver_PlayMoneyCommandReceived(object sender, CommandEventArgs<PlayerPlayMoneyCommand> e)
        {
            m_Game.PlayMoney(m_Player, e.Command.Played);
        }

        void m_CommandObserver_DisconnectCommandReceived(object sender, CommandEventArgs<DisconnectCommand> e)
        {
            if( m_UserInfo != null && m_Game is PokerGameCareer )
                m_UserInfo.TotalMoney += m_Player.MoneySafeAmnt;
            LeftTable(this, new KeyEventArgs<int>(m_ID));
            m_IsConnected = false;
            m_Player.IsZombie = true;
            TableInfo t = m_Game.Table;
            LogManager.Log(LogLevel.Message, "GameServer.m_CommandObserver_DisconnectCommandReceived", "> Client '{0}' left table: {2}:{1}", m_Player.Name, t.Name, m_ID);
            if (m_Game.State == PokerGame.TypeState.WaitForPlayers)
                m_Game.LeaveGame(m_Player);
            else if (t.NoSeatCurrPlayer == m_Player.NoSeat)
            {
                if( t.CanCheck(m_Player) )
                    m_Game.PlayMoney(m_Player, 0);
                else
                    m_Game.PlayMoney(m_Player, -1);
            }
        }

        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageLow, "GameServer.m_CommandObserver_CommandReceived", "<Game:{0}> RECV [{1}]", m_Player.Name, e.Str);
        }
    }
}
