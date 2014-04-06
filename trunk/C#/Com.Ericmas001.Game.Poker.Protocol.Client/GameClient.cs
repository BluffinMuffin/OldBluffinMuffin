using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using EricUtility.Networking;
using EricUtility.Networking.Commands;
using PokerWorld.Game;
using System.Net.Sockets;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Game;
using EricUtility.Games.CardGame;
using System.IO;
using EricUtility;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Entities;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public class GameClient : CommandQueueCommunicator<GameClientCommandObserver>, IPokerGame
    {
        #region Fields
        private readonly PokerTable m_PokerTable = new PokerTable();
        private readonly int m_TablePosition;
        private readonly string m_PlayerName;
        private readonly int m_NoPort;
        #endregion Fields

        #region Events
        public event EventHandler EverythingEnded = delegate { };
        public event EventHandler GameBlindNeeded = delegate { };
        public event EventHandler GameEnded = delegate { };
        public event EventHandler GameGenerallyUpdated = delegate { };
        public event EventHandler<RoundEventArgs> GameBettingRoundStarted = delegate { };
        public event EventHandler<RoundEventArgs> GameBettingRoundEnded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerJoined = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerLeaved = delegate { };
        public event EventHandler<HistoricPlayerInfoEventArgs> PlayerActionNeeded = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerMoneyChanged = delegate { };
        public event EventHandler<PlayerInfoEventArgs> PlayerHoleCardsChanged = delegate { };
        public event EventHandler<PlayerActionEventArgs> PlayerActionTaken = delegate { };
        public event EventHandler<PotWonEventArgs> PlayerWonPot = delegate { };
        #endregion Events

        #region Properties
        public PokerTable Table { get { return m_PokerTable; } }
        public int NoSeat { get { return m_TablePosition; } }
        public int NoPort { get { return m_NoPort; } }
        public string Encode
        {
            get
            {
                // 0 : Assume que les game sont en real money (1)
                // 1 : Assume que c'est tlt du Texas Hold'em (0)
                // 2 : Assume que c'Est tlt des Ring game (0)
                // 3 : Assume que c'Est tlt du NoLimit (0)
                // 4 : GameRound (0,1,2,3)
                return string.Format("{0}{1}{2}{3}{4}", 1, 0, 0, 0, (int)m_PokerTable.Round);
            }
        }
        #endregion Properties

        #region Ctors & Init
        public GameClient(int pos, string name, int port)
        {
            m_TablePosition = pos;
            m_PlayerName = name;
            m_NoPort = port;
        }

        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += new EventHandler<StringEventArgs>(m_CommandObserver_CommandReceived);
            m_CommandObserver.BetTurnEndedCommandReceived += new EventHandler<CommandEventArgs<BetTurnEndedCommand>>(m_CommandObserver_BetTurnEndedCommandReceived);
            m_CommandObserver.BetTurnStartedCommandReceived += new EventHandler<CommandEventArgs<BetTurnStartedCommand>>(m_CommandObserver_BetTurnStartedCommandReceived);
            m_CommandObserver.GameEndedCommandReceived += new EventHandler<CommandEventArgs<GameEndedCommand>>(m_CommandObserver_GameEndedCommandReceived);
            m_CommandObserver.GameStartedCommandReceived += new EventHandler<CommandEventArgs<GameStartedCommand>>(m_CommandObserver_GameStartedCommandReceived);
            m_CommandObserver.PlayerHoleCardsChangedCommandReceived += new EventHandler<CommandEventArgs<PlayerHoleCardsChangedCommand>>(m_CommandObserver_PlayerHoleCardsChangedCommandReceived);
            m_CommandObserver.PlayerJoinedCommandReceived += new EventHandler<CommandEventArgs<PlayerJoinedCommand>>(m_CommandObserver_PlayerJoinedCommandReceived);
            m_CommandObserver.PlayerLeftCommandReceived += new EventHandler<CommandEventArgs<PlayerLeftCommand>>(m_CommandObserver_PlayerLeftCommandReceived);
            m_CommandObserver.PlayerMoneyChangedCommandReceived += new EventHandler<CommandEventArgs<PlayerMoneyChangedCommand>>(m_CommandObserver_PlayerMoneyChangedCommandReceived);
            m_CommandObserver.PlayerTurnBeganCommandReceived += new EventHandler<CommandEventArgs<PlayerTurnBeganCommand>>(m_CommandObserver_PlayerTurnBeganCommandReceived);
            m_CommandObserver.PlayerTurnEndedCommandReceived += new EventHandler<CommandEventArgs<PlayerTurnEndedCommand>>(m_CommandObserver_PlayerTurnEndedCommandReceived);
            m_CommandObserver.PlayerWonPotCommandReceived += new EventHandler<CommandEventArgs<PlayerWonPotCommand>>(m_CommandObserver_PlayerWonPotCommandReceived);
            m_CommandObserver.TableClosedCommandReceived += new EventHandler<CommandEventArgs<TableClosedCommand>>(m_CommandObserver_TableClosedCommandReceived);
            m_CommandObserver.TableInfoCommandReceived += new EventHandler<CommandEventArgs<TableInfoCommand>>(m_CommandObserver_TableInfoCommandReceived);
        }

        #endregion Ctors & Init

        #region CommandObserver Event Handling
        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageLow, "GameClient.m_CommandObserver_CommandReceived", "{0} RECV -={1}=-", m_PlayerName, e.Str);
        }

        void m_CommandObserver_BetTurnEndedCommandReceived(object sender, CommandEventArgs<BetTurnEndedCommand> e)
        {
            BetTurnEndedCommand cmd = e.Command;

            InitPotAmounts(cmd.PotsAmounts, 0);
            m_PokerTable.HigherBet = 0;
            m_PokerTable.Players.ForEach(p => p.Info.MoneyBetAmnt = 0);

            GameBettingRoundEnded(this, new RoundEventArgs(cmd.Round));
        }

        void m_CommandObserver_BetTurnStartedCommandReceived(object sender, CommandEventArgs<BetTurnStartedCommand> e)
        {
            BetTurnStartedCommand cmd = e.Command;
            SetCards(cmd.CardsID);
            m_PokerTable.Round = cmd.Round;
            m_PokerTable.NoSeatLastRaise = m_PokerTable.GetPlayingPlayerNextTo(m_PokerTable.Round == RoundTypeEnum.Preflop ? m_PokerTable.NoSeatBigBlind : m_PokerTable.NoSeatDealer).Info.NoSeat;

            GameBettingRoundStarted(this, new RoundEventArgs(cmd.Round));
        }

        void m_CommandObserver_GameEndedCommandReceived(object sender, CommandEventArgs<GameEndedCommand> e)
        {
            m_PokerTable.TotalPotAmnt = 0;

            GameEnded(this, new EventArgs());
        }

        void m_CommandObserver_GameStartedCommandReceived(object sender, CommandEventArgs<GameStartedCommand> e)
        {
            GameStartedCommand cmd = e.Command;

            m_PokerTable.NoSeatDealer = cmd.NoSeatD;
            m_PokerTable.NoSeatSmallBlind = cmd.NoSeatSB;
            m_PokerTable.NoSeatBigBlind = cmd.NoSeatBB;

            if (m_PokerTable.NoSeatSmallBlind == m_TablePosition)
                Send(new PlayerPlayMoneyCommand() { Played = m_PokerTable.SmallBlindAmnt });

            if (m_PokerTable.NoSeatBigBlind == m_TablePosition)
                Send(new PlayerPlayMoneyCommand() { Played = m_PokerTable.Rules.BlindAmount });

            GameBlindNeeded(this, new EventArgs());
        }

        void m_CommandObserver_PlayerHoleCardsChangedCommandReceived(object sender, CommandEventArgs<PlayerHoleCardsChangedCommand> e)
        {
            PlayerHoleCardsChangedCommand cmd = e.Command;
            PokerPlayer p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                SetPlayerVisibility(p, cmd.IsPlaying, cmd.CardsID.Select(id => new GameCard(id)).ToList());

                PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p.Info));
            }
        }


        void m_CommandObserver_PlayerJoinedCommandReceived(object sender, CommandEventArgs<PlayerJoinedCommand> e)
        {
            PlayerJoinedCommand cmd = e.Command;
            PokerPlayer p = new PokerPlayer(new PlayerInfo(cmd.PlayerName, cmd.PlayerMoney) { NoSeat = cmd.PlayerPos });

            m_PokerTable.ForceJoinTable(p, cmd.PlayerPos);

            PlayerJoined(this, new PlayerInfoEventArgs(p.Info));

        }

        void m_CommandObserver_PlayerLeftCommandReceived(object sender, CommandEventArgs<PlayerLeftCommand> e)
        {
            PlayerLeftCommand cmd = e.Command;
            PokerPlayer p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                m_PokerTable.LeaveTable(p);

                PlayerLeaved(this, new PlayerInfoEventArgs(p.Info));
            }
        }

        void m_CommandObserver_PlayerMoneyChangedCommandReceived(object sender, CommandEventArgs<PlayerMoneyChangedCommand> e)
        {
            PlayerMoneyChangedCommand cmd = e.Command;
            PokerPlayer p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                p.Info.MoneySafeAmnt = cmd.PlayerMoney;

                PlayerMoneyChanged(this, new PlayerInfoEventArgs(p.Info));
            }
        }

        void m_CommandObserver_PlayerTurnBeganCommandReceived(object sender, CommandEventArgs<PlayerTurnBeganCommand> e)
        {
            PlayerTurnBeganCommand cmd = e.Command;
            PokerPlayer p = m_PokerTable.GetPlayer(cmd.PlayerPos);
            PokerPlayer l = m_PokerTable.GetPlayer(cmd.LastPlayerNoSeat);

            if (p != null)
            {
                m_PokerTable.NoSeatCurrPlayer = cmd.PlayerPos;
                m_PokerTable.MinimumRaiseAmount = cmd.MinimumRaise;

                PlayerActionNeeded(this, new HistoricPlayerInfoEventArgs(p.Info, l.Info));
            }
        }

        void m_CommandObserver_PlayerTurnEndedCommandReceived(object sender, CommandEventArgs<PlayerTurnEndedCommand> e)
        {
            PlayerTurnEndedCommand cmd = e.Command;
            PokerPlayer p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            m_PokerTable.HigherBet = Math.Max(m_PokerTable.HigherBet, cmd.PlayerBet);
            m_PokerTable.TotalPotAmnt = cmd.TotalPot;

            if (p != null)
            {
                if (cmd.ActionType == GameActionEnum.Raise)
                    m_PokerTable.NoSeatLastRaise = p.Info.NoSeat;

                p.Info.MoneyBetAmnt = cmd.PlayerBet;
                p.Info.MoneySafeAmnt = cmd.PlayerMoney;
                p.IsPlaying = cmd.IsPlaying;

                PlayerActionTaken(this, new PlayerActionEventArgs(p.Info, cmd.ActionType, cmd.ActionAmount));
            }
        }

        void m_CommandObserver_PlayerWonPotCommandReceived(object sender, CommandEventArgs<PlayerWonPotCommand> e)
        {
            PlayerWonPotCommand cmd = e.Command;
            PokerPlayer p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                p.Info.MoneySafeAmnt = cmd.PlayerMoney;

                PlayerWonPot(this, new PotWonEventArgs(p.Info, cmd.PotID, cmd.Shared));
            }
        }

        void m_CommandObserver_TableClosedCommandReceived(object sender, CommandEventArgs<TableClosedCommand> e)
        {
            EverythingEnded(this, e);
        }

        void m_CommandObserver_TableInfoCommandReceived(object sender, CommandEventArgs<TableInfoCommand> e)
        {
            TableInfoCommand cmd = e.Command;

            InitPotAmounts(cmd.PotsAmount, cmd.TotalPotAmount);
            SetCards(cmd.BoardCardIDs);
            m_PokerTable.Rules = cmd.Rules;
            m_PokerTable.Players.ForEach(p => m_PokerTable.LeaveTable(p));

            for (int i = 0; i < cmd.NbPlayers; ++i)
            {
                SeatInfo seat = cmd.Seats[i];
                if (seat.IsEmpty)
                {
                    continue;
                }
                int noSeat = seat.NoSeat;
                PokerPlayer p = new PokerPlayer(seat.Player);
                m_PokerTable.ForceJoinTable(p, noSeat);

                SetPlayerVisibility(p, seat.IsPlaying, seat.Player.HoleCards);

                if (seat.IsDealer)
                    m_PokerTable.NoSeatDealer = noSeat;

                if (seat.IsSmallBlind)
                    m_PokerTable.NoSeatSmallBlind = noSeat;

                if (seat.IsBigBlind)
                    m_PokerTable.NoSeatBigBlind = noSeat;

                if (seat.IsCurrentPlayer)
                    m_PokerTable.NoSeatCurrPlayer = noSeat;

                PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p.Info));

            }
            GameGenerallyUpdated(this, new EventArgs());
        }
        #endregion CommandObserver Event Handling

        #region Public Methods
        public void Disconnect()
        {
            if (IsConnected)
            {
                Send(new DisconnectCommand());
            }
        }

        public bool PlayMoney(PokerPlayer p, int amnt)
        {
            Send(new PlayerPlayMoneyCommand() { Played = amnt });
            return true;
        }

        public bool LeaveGame(PokerPlayer p)
        {
            Send(new DisconnectCommand());
            return true;
        }
        #endregion Public Methods

        #region Private Methods
        private void InitPotAmounts(List<int> amounts, int totalPotAmnt)
        {
            m_PokerTable.Pots.Clear();
            m_PokerTable.TotalPotAmnt = totalPotAmnt;

            for (int i = 0; i < amounts.Count && amounts[i] > 0; ++i)
            {
                m_PokerTable.Pots.Add(new MoneyPot(i, amounts[i]));
                m_PokerTable.TotalPotAmnt += amounts[i];
            }
        }

        private void SetCards(List<int> cardsID)
        {
            GameCard[] cards = cardsID.Select(c => new GameCard(c)).ToArray();
            m_PokerTable.SetCards(cards[0], cards[1], cards[2], cards[3], cards[4]);
        }

        private void SetPlayerVisibility(PokerPlayer p, bool isPlaying, List<GameCard> cards)
        {
            p.IsPlaying = isPlaying;
            p.Cards = cards.ToArray();
        }
        #endregion Private Methods
    }
}
