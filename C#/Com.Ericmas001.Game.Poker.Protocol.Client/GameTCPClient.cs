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
using System.Threading;

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
            m_CommandObserver.CommandReceived += OnCommandReceived;
            m_CommandObserver.BetTurnEndedCommandReceived += OnBetTurnEndedCommandReceived;
            m_CommandObserver.BetTurnStartedCommandReceived += OnBetTurnStartedCommandReceived;
            m_CommandObserver.GameEndedCommandReceived += OnGameEndedCommandReceived;
            m_CommandObserver.GameStartedCommandReceived += OnGameStartedCommandReceived;
            m_CommandObserver.PlayerHoleCardsChangedCommandReceived += OnPlayerHoleCardsChangedCommandReceived;
            m_CommandObserver.PlayerJoinedCommandReceived += OnPlayerJoinedCommandReceived;
            m_CommandObserver.SeatUpdatedCommandReceived += OnSeatUpdatedCommandReceived;
            m_CommandObserver.PlayerLeftCommandReceived += OnPlayerLeftCommandReceived;
            m_CommandObserver.PlayerMoneyChangedCommandReceived += OnPlayerMoneyChangedCommandReceived;
            m_CommandObserver.PlayerTurnBeganCommandReceived += OnPlayerTurnBeganCommandReceived;
            m_CommandObserver.PlayerTurnEndedCommandReceived += OnPlayerTurnEndedCommandReceived;
            m_CommandObserver.PlayerWonPotCommandReceived += OnPlayerWonPotCommandReceived;
            m_CommandObserver.TableClosedCommandReceived += OnTableClosedCommandReceived;
            m_CommandObserver.TableInfoCommandReceived += OnTableInfoCommandReceived;

            m_CommandObserver.PlayerSitInResponseReceived += OnPlayerSitInResponseReceived;
            m_CommandObserver.PlayerSitOutResponseReceived += OnPlayerSitOutResponseReceived;
        }

        #endregion Ctors & Init

        #region CommandObserver Event Handling

        void OnPlayerSitOutResponseReceived(object sender, CommandEventArgs<PlayerSitOutResponse> e)
        {
            lock (m_PokerTable)
            {
                Observer.RaiseSitOutResponseReceived(e.Command.Success);
            }
        }

        void OnPlayerSitInResponseReceived(object sender, CommandEventArgs<PlayerSitInResponse> e)
        {
            lock (m_PokerTable)
            {
                Observer.RaiseSitInResponseReceived(e.Command.NoSeat);
            }
        }
        void OnCommandReceived(object sender, StringEventArgs e)
        {
            lock (m_PokerTable)
            {
                LogManager.Log(LogLevel.MessageLow, "GameClient.m_CommandObserver_CommandReceived", "{0} RECV -={1}=-", m_PlayerName, e.Str);
            }
        }

        void OnBetTurnEndedCommandReceived(object sender, CommandEventArgs<BetTurnEndedCommand> e)
        {
            lock (m_PokerTable)
            {
                BetTurnEndedCommand cmd = e.Command;

                InitPotAmounts(cmd.PotsAmounts, 0);
                m_PokerTable.HigherBet = 0;
                m_PokerTable.Players.ForEach(p => p.MoneyBetAmnt = 0);

                Observer.RaiseGameBettingRoundEnded(cmd.Round);
            }
        }

        void OnBetTurnStartedCommandReceived(object sender, CommandEventArgs<BetTurnStartedCommand> e)
        {
            lock (m_PokerTable)
            {
                BetTurnStartedCommand cmd = e.Command;
                SetCards(cmd.CardsID);
                m_PokerTable.Round = cmd.Round;

                Observer.RaiseGameBettingRoundStarted(cmd.Round);
            }
        }

        void OnGameEndedCommandReceived(object sender, CommandEventArgs<GameEndedCommand> e)
        {
            lock (m_PokerTable)
            {
                m_PokerTable.TotalPotAmnt = 0;

                Observer.RaiseGameEnded();
            }
        }

        void OnGameStartedCommandReceived(object sender, CommandEventArgs<GameStartedCommand> e)
        {
            lock (m_PokerTable)
            {
                GameStartedCommand cmd = e.Command;
                
                if (m_TablePosition >= 0 && cmd.NeededBlind > 0)
                    Send(new PlayerPlayMoneyCommand() { Played = cmd.NeededBlind });

                //if (m_TablePosition >= 0 && m_PokerTable.Seats[m_TablePosition].Attributes.Contains(SeatAttributeEnum.BigBlind))
                //    Send(new PlayerPlayMoneyCommand() { Played = m_PokerTable.Params.BlindAmount });

                Observer.RaiseGameBlindNeeded();
            }
        }

        void OnPlayerHoleCardsChangedCommandReceived(object sender, CommandEventArgs<PlayerHoleCardsChangedCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerHoleCardsChangedCommand cmd = e.Command;
                PlayerInfo p = m_PokerTable.Seats[cmd.PlayerPos].Player;

                if (p != null)
                {
                    SetPlayerVisibility(p, cmd.State, cmd.CardsID.Select(id => new GameCard(id)).ToList());

                    Observer.RaisePlayerHoleCardsChanged(p);
                }
            }
        }


        void OnPlayerJoinedCommandReceived(object sender, CommandEventArgs<PlayerJoinedCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerJoinedCommand cmd = e.Command;
                PlayerInfo p = new PlayerInfo() { Name = cmd.PlayerName };

                m_PokerTable.JoinTable(p);

                Observer.RaisePlayerJoined(p);
            }
        }

        void OnSeatUpdatedCommandReceived(object sender, CommandEventArgs<SeatUpdatedCommand> e)
        {
            lock (m_PokerTable)
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

        }

        void OnPlayerLeftCommandReceived(object sender, CommandEventArgs<PlayerLeftCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerLeftCommand cmd = e.Command;
                PlayerInfo p = m_PokerTable.Seats[cmd.PlayerPos].Player;

                if (p != null)
                {
                    m_PokerTable.LeaveTable(p);

                    Observer.RaisePlayerLeft(p);
                }
            }
        }

        void OnPlayerMoneyChangedCommandReceived(object sender, CommandEventArgs<PlayerMoneyChangedCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerMoneyChangedCommand cmd = e.Command;
                PlayerInfo p = m_PokerTable.Seats[cmd.PlayerPos].Player;

                if (p != null)
                {
                    p.MoneySafeAmnt = cmd.PlayerMoney;

                    Observer.RaisePlayerMoneyChanged(p);
                }
            }
        }

        void OnPlayerTurnBeganCommandReceived(object sender, CommandEventArgs<PlayerTurnBeganCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerTurnBeganCommand cmd = e.Command;
                SeatInfo ps = m_PokerTable.Seats[cmd.PlayerPos];
                PlayerInfo l = m_PokerTable.Seats[cmd.LastPlayerNoSeat].Player;

                if (!ps.IsEmpty)
                {
                    m_PokerTable.ChangeCurrentPlayerTo(ps);
                    m_PokerTable.MinimumRaiseAmount = cmd.MinimumRaise;

                    Observer.RaisePlayerActionNeeded(ps.Player, l);
                }
            }
        }

        void OnPlayerTurnEndedCommandReceived(object sender, CommandEventArgs<PlayerTurnEndedCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerTurnEndedCommand cmd = e.Command;
                PlayerInfo p = m_PokerTable.Seats[cmd.PlayerPos].Player;

                m_PokerTable.HigherBet = Math.Max(m_PokerTable.HigherBet, cmd.PlayerBet);
                m_PokerTable.TotalPotAmnt = cmd.TotalPot;

                if (p != null)
                {
                    p.MoneyBetAmnt = cmd.PlayerBet;
                    p.MoneySafeAmnt = cmd.PlayerMoney;
                    p.State = cmd.State;

                    Observer.RaisePlayerActionTaken(p, cmd.ActionType, cmd.ActionAmount);
                }
            }
        }

        void OnPlayerWonPotCommandReceived(object sender, CommandEventArgs<PlayerWonPotCommand> e)
        {
            lock (m_PokerTable)
            {
                PlayerWonPotCommand cmd = e.Command;
                PlayerInfo p = m_PokerTable.Seats[cmd.PlayerPos].Player;

                if (p != null)
                {
                    p.MoneySafeAmnt = cmd.PlayerMoney;

                    Observer.RaisePlayerWonPot(p, cmd.PotID, cmd.Shared);
                }
            }
        }

        void OnTableClosedCommandReceived(object sender, CommandEventArgs<TableClosedCommand> e)
        {
            lock (m_PokerTable)
            {
                Observer.RaiseEverythingEnded();
            }
        }

        void OnTableInfoCommandReceived(object sender, CommandEventArgs<TableInfoCommand> e)
        {
            lock (m_PokerTable)
            {
                TableInfoCommand cmd = e.Command;

                InitPotAmounts(cmd.PotsAmount, cmd.TotalPotAmount);
                SetCards(cmd.BoardCardIDs);
                m_PokerTable.Params = cmd.Params;
                m_PokerTable.People.Clear();

                for (int i = 0; i < cmd.NbPlayers; ++i)
                {
                    m_PokerTable.SetSeat(cmd.Seats[i]);
                    m_PokerTable.People.Add(m_PokerTable.Seats[i].Player);

                }
                Observer.RaiseGameGenerallyUpdated();
            }
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
