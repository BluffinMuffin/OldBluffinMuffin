using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using Com.Ericmas001.Net;
using Com.Ericmas001.Net.Protocol.JSON;
using System.Net.Sockets;
using Com.Ericmas001.Game.Poker.Protocol.Commands.Game;
using Com.Ericmas001.Games;
using System.IO;
using Com.Ericmas001;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.Protocol.Commands;
using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;
using Com.Ericmas001.Util;
using Com.Ericmas001.Net.Protocol;
using Newtonsoft.Json.Linq;
using System.Reflection;
using System.Runtime.Serialization.Formatters;
using Newtonsoft.Json;

namespace Com.Ericmas001.Game.Poker.Protocol.Client
{
    public class GameTCPClient : CommandQueueCommunicator<GameObserver>, IPokerGame
    {
        #region Fields
        private readonly DummyTable m_PokerTable = new DummyTable();
        private int m_TablePosition;
        private readonly string m_PlayerName;
        private readonly int m_NoPort;
        #endregion Fields

        #region Properties
        public PokerGameObserver Observer { get; private set; }
        public TableInfo Table { get { return m_PokerTable; } }
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
        public GameTCPClient(string name, int port)
        {
            Observer = new PokerGameObserver(this);
            m_TablePosition = -1;
            m_PlayerName = name;
            m_NoPort = port;
        }

        protected override void InitializeCommandObserver()
        {
            m_CommandObserver.CommandReceived += m_CommandObserver_CommandReceived;
            m_CommandObserver.BetTurnEndedCommandReceived += m_CommandObserver_BetTurnEndedCommandReceived;
            m_CommandObserver.BetTurnStartedCommandReceived += m_CommandObserver_BetTurnStartedCommandReceived;
            m_CommandObserver.GameEndedCommandReceived += m_CommandObserver_GameEndedCommandReceived;
            m_CommandObserver.GameStartedCommandReceived += m_CommandObserver_GameStartedCommandReceived;
            m_CommandObserver.PlayerHoleCardsChangedCommandReceived += m_CommandObserver_PlayerHoleCardsChangedCommandReceived;
            m_CommandObserver.PlayerJoinedCommandReceived += m_CommandObserver_PlayerJoinedCommandReceived;
            m_CommandObserver.SeatUpdatedCommandReceived += m_CommandObserver_SeatUpdatedCommandReceived;
            m_CommandObserver.PlayerLeftCommandReceived += m_CommandObserver_PlayerLeftCommandReceived;
            m_CommandObserver.PlayerMoneyChangedCommandReceived += m_CommandObserver_PlayerMoneyChangedCommandReceived;
            m_CommandObserver.PlayerTurnBeganCommandReceived += m_CommandObserver_PlayerTurnBeganCommandReceived;
            m_CommandObserver.PlayerTurnEndedCommandReceived += m_CommandObserver_PlayerTurnEndedCommandReceived;
            m_CommandObserver.PlayerWonPotCommandReceived += m_CommandObserver_PlayerWonPotCommandReceived;
            m_CommandObserver.TableClosedCommandReceived += m_CommandObserver_TableClosedCommandReceived;
            m_CommandObserver.TableInfoCommandReceived += m_CommandObserver_TableInfoCommandReceived;

            m_CommandObserver.PlayerSitInResponseReceived += m_CommandObserver_PlayerSitInResponseReceived;
            m_CommandObserver.PlayerSitOutResponseReceived += m_CommandObserver_PlayerSitOutResponseReceived;
        }

        #endregion Ctors & Init

        #region CommandObserver Event Handling

        void m_CommandObserver_PlayerSitOutResponseReceived(object sender, CommandEventArgs<PlayerSitOutResponse> e)
        {
            Observer.RaiseSitOutResponseReceived(e.Command.Success);
        }

        void m_CommandObserver_PlayerSitInResponseReceived(object sender, CommandEventArgs<PlayerSitInResponse> e)
        {
            Observer.RaiseSitInResponseReceived(e.Command.NoSeat);
        }
        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            LogManager.Log(LogLevel.MessageLow, "GameClient.m_CommandObserver_CommandReceived", "{0} RECV -={1}=-", m_PlayerName, e.Str);
        }

        void m_CommandObserver_BetTurnEndedCommandReceived(object sender, CommandEventArgs<BetTurnEndedCommand> e)
        {
            BetTurnEndedCommand cmd = e.Command;

            InitPotAmounts(cmd.PotsAmounts, 0);
            m_PokerTable.HigherBet = 0;
            m_PokerTable.Players.ForEach(p => p.MoneyBetAmnt = 0);

            Observer.RaiseGameBettingRoundEnded(cmd.Round);
        }

        void m_CommandObserver_BetTurnStartedCommandReceived(object sender, CommandEventArgs<BetTurnStartedCommand> e)
        {
            BetTurnStartedCommand cmd = e.Command;
            SetCards(cmd.CardsID);
            m_PokerTable.Round = cmd.Round;
            m_PokerTable.NoSeatLastRaise = m_PokerTable.GetPlayingPlayerNextTo(m_PokerTable.Round == RoundTypeEnum.Preflop ? m_PokerTable.GetPlayingPlayerNextTo(m_PokerTable.NoSeatSmallBlind).NoSeat : m_PokerTable.NoSeatDealer).NoSeat;

            Observer.RaiseGameBettingRoundStarted(cmd.Round);
        }

        void m_CommandObserver_GameEndedCommandReceived(object sender, CommandEventArgs<GameEndedCommand> e)
        {
            m_PokerTable.TotalPotAmnt = 0;

            Observer.RaiseGameEnded();
        }

        void m_CommandObserver_GameStartedCommandReceived(object sender, CommandEventArgs<GameStartedCommand> e)
        {
            GameStartedCommand cmd = e.Command;

            foreach(SeatInfo si in m_PokerTable.Seats)
            {
                si.IsDealer = false;
                si.IsSmallBlind = false;
                si.IsBigBlind = false;
                si.IsCurrentPlayer = false;
            }

            m_PokerTable.Seats[cmd.NoSeatD].IsDealer = true;
            m_PokerTable.NoSeatSmallBlind = cmd.NoSeatSB;
            m_PokerTable.NoSeatBigBlind = cmd.NoSeatBB;

            if (m_PokerTable.NoSeatSmallBlind == m_TablePosition)
                Send(new PlayerPlayMoneyCommand() { Played = m_PokerTable.SmallBlindAmnt });

            if (m_PokerTable.BigBlinds.Any(x => x.NoSeat == m_TablePosition))
                Send(new PlayerPlayMoneyCommand() { Played = m_PokerTable.Params.BlindAmount });

            Observer.RaiseGameBlindNeeded();
        }

        void m_CommandObserver_PlayerHoleCardsChangedCommandReceived(object sender, CommandEventArgs<PlayerHoleCardsChangedCommand> e)
        {
            PlayerHoleCardsChangedCommand cmd = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                SetPlayerVisibility(p, cmd.State, cmd.CardsID.Select(id => new GameCard(id)).ToList());

                Observer.RaisePlayerHoleCardsChanged(p);
            }
        }


        void m_CommandObserver_PlayerJoinedCommandReceived(object sender, CommandEventArgs<PlayerJoinedCommand> e)
        {
            PlayerJoinedCommand cmd = e.Command;
            PlayerInfo p = new PlayerInfo() { Name = cmd.PlayerName };

            m_PokerTable.JoinTable(p);

            Observer.RaisePlayerJoined(p);

        }

        void m_CommandObserver_SeatUpdatedCommandReceived(object sender, CommandEventArgs<SeatUpdatedCommand> e)
        {
            SeatInfo s = e.Command.Seat;
            if (!s.IsEmpty)
            {
                m_PokerTable.SitInToTable(s.Player, s.NoSeat);
                if (m_PlayerName == s.Player.Name)
                    m_TablePosition = s.NoSeat;
            }
            else
                m_PokerTable.ClearSeat(s.NoSeat);

            Observer.RaiseSeatUpdated(s);

        }

        void m_CommandObserver_PlayerLeftCommandReceived(object sender, CommandEventArgs<PlayerLeftCommand> e)
        {
            PlayerLeftCommand cmd = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                m_PokerTable.LeaveTable(p);

                Observer.RaisePlayerLeft(p);
            }
        }

        void m_CommandObserver_PlayerMoneyChangedCommandReceived(object sender, CommandEventArgs<PlayerMoneyChangedCommand> e)
        {
            PlayerMoneyChangedCommand cmd = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                p.MoneySafeAmnt = cmd.PlayerMoney;

                Observer.RaisePlayerMoneyChanged(p);
            }
        }

        void m_CommandObserver_PlayerTurnBeganCommandReceived(object sender, CommandEventArgs<PlayerTurnBeganCommand> e)
        {
            PlayerTurnBeganCommand cmd = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(cmd.PlayerPos);
            PlayerInfo l = m_PokerTable.GetPlayer(cmd.LastPlayerNoSeat);

            if (p != null)
            {
                m_PokerTable.NoSeatCurrPlayer = cmd.PlayerPos;
                m_PokerTable.MinimumRaiseAmount = cmd.MinimumRaise;

                Observer.RaisePlayerActionNeeded(p, l);
            }
        }

        void m_CommandObserver_PlayerTurnEndedCommandReceived(object sender, CommandEventArgs<PlayerTurnEndedCommand> e)
        {
            PlayerTurnEndedCommand cmd = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            m_PokerTable.HigherBet = Math.Max(m_PokerTable.HigherBet, cmd.PlayerBet);
            m_PokerTable.TotalPotAmnt = cmd.TotalPot;

            if (p != null)
            {
                if (cmd.ActionType == GameActionEnum.Raise)
                    m_PokerTable.NoSeatLastRaise = p.NoSeat;

                p.MoneyBetAmnt = cmd.PlayerBet;
                p.MoneySafeAmnt = cmd.PlayerMoney;
                p.State = cmd.State;

                Observer.RaisePlayerActionTaken(p, cmd.ActionType, cmd.ActionAmount);
            }
        }

        void m_CommandObserver_PlayerWonPotCommandReceived(object sender, CommandEventArgs<PlayerWonPotCommand> e)
        {
            PlayerWonPotCommand cmd = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(cmd.PlayerPos);

            if (p != null)
            {
                p.MoneySafeAmnt = cmd.PlayerMoney;

                Observer.RaisePlayerWonPot(p, cmd.PotID, cmd.Shared);
            }
        }

        void m_CommandObserver_TableClosedCommandReceived(object sender, CommandEventArgs<TableClosedCommand> e)
        {
            Observer.RaiseEverythingEnded();
        }

        void m_CommandObserver_TableInfoCommandReceived(object sender, CommandEventArgs<TableInfoCommand> e)
        {
            TableInfoCommand cmd = e.Command;

            InitPotAmounts(cmd.PotsAmount, cmd.TotalPotAmount);
            SetCards(cmd.BoardCardIDs);
            m_PokerTable.Params = cmd.Params;
            m_PokerTable.Players.ForEach(p => m_PokerTable.LeaveTable(p));

            for (int i = 0; i < cmd.NbPlayers; ++i)
            {
                SeatInfo seat = cmd.Seats[i];
                if (seat.IsEmpty)
                {
                    continue;
                }
                int noSeat = seat.NoSeat;
                PlayerInfo p = seat.Player;
                m_PokerTable.JoinTable(p);
                m_PokerTable.SitInToTable(p, noSeat);

                SetPlayerVisibility(p, p.State, seat.Player.HoleCards);

                m_PokerTable.Seats[i].IsDealer = seat.IsDealer;

                if (seat.IsSmallBlind)
                    m_PokerTable.NoSeatSmallBlind = noSeat;

                if (seat.IsBigBlind)
                    m_PokerTable.NoSeatBigBlind = noSeat;

                if (seat.IsCurrentPlayer)
                    m_PokerTable.NoSeatCurrPlayer = noSeat;

                Observer.RaisePlayerHoleCardsChanged(p);

            }
            Observer.RaiseGameGenerallyUpdated();
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

        public bool PlayMoney(PlayerInfo p, int amnt)
        {
            Send(new PlayerPlayMoneyCommand() { Played = amnt });
            return true;
        }

        public int SitIn(PlayerInfo p, int noSeat = -1)
        {
            Send(new PlayerSitInCommand() { NoSeat = noSeat });
            return -1;
        }

        public bool SitOut(PlayerInfo p)
        {
            Send(new PlayerSitOutCommand());
            return true;
        }

        public bool LeaveGame(PlayerInfo p)
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

        private void SetPlayerVisibility(PlayerInfo p, PlayerStateEnum pState, List<GameCard> cards)
        {
            p.State = pState;
            p.Cards = cards.ToArray();
        }
        //protected T WaitAndReceive<T>() where T : AbstractCommand
        //{
        //    string expected = (string)typeof(T).GetField(AbstractCommand.CommandNameField, (BindingFlags.Static | BindingFlags.FlattenHierarchy | BindingFlags.Public)).GetValue(null);
        //    string s;
        //    string commandName;

        //    JObject jObj;

        //    do
        //    {
        //        s = m_Incoming.Dequeue();
        //        jObj = JsonConvert.DeserializeObject<dynamic>(s);
        //        commandName = (string)jObj["CommandName"];
        //    }
        //    while (s != null && commandName != expected);

        //    return JsonConvert.DeserializeObject<T>(s, new JsonSerializerSettings() { TypeNameHandling = TypeNameHandling.All, TypeNameAssemblyFormat = FormatterAssemblyStyle.Simple });
        //}
        #endregion Private Methods
    }
}
