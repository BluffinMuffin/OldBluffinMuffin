using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using EricUtility.Networking;
using EricUtility.Networking.Commands;
using PokerProtocol.Observer;
using PokerWorld.Game;
using System.Net.Sockets;
using PokerProtocol.Commands.Game;
using EricUtility.Games.CardGame;
using System.IO;

namespace PokerProtocol
{
    public class GameTCPClient : CommandTCPCommunicator<GameClientCommandObserver>, IPokerGame
    {
        private readonly TableInfo m_PokerTable = new TableInfo();
        private readonly int m_TablePosition;
        private readonly string m_PlayerName;
        private readonly int m_NoPort;

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

        public TableInfo Table
        {
            get { return m_PokerTable; }
        }


        public int NoSeat
        {
            get { return m_TablePosition; }
        }

        public int NoPort
        {
            get { return m_NoPort; }
        }
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

        public GameTCPClient(TcpClient socket, int pos, string name, int port)
            : base(socket)
        {
            m_TablePosition = pos;
            m_PlayerName = name;
            m_NoPort = port;
        }

        public void Disconnect()
        {
            if (IsConnected)
            {
                Send(new DisconnectCommand());
                Close();
            }
        }

        public bool PlayMoney(PlayerInfo p, int amnt)
        {
            Send(new PlayerPlayMoneyCommand(amnt));
            return true;
        }

        public bool LeaveGame(PlayerInfo p)
        {
            Send(new DisconnectCommand());
            return true;
        }
        public override void OnReceiveCrashed(Exception e)
        {
            if (e is IOException)
            {
                Console.WriteLine("Table lost connection with server");
                Close();
            }
            else
                base.OnReceiveCrashed(e);
        }
        public override void OnSendCrashed(Exception e)
        {
            if (e is IOException)
            {
                Console.WriteLine("Table lost connection with server");
                Close();
            }
            else
                base.OnSendCrashed(e);
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

        void m_CommandObserver_CommandReceived(object sender, StringEventArgs e)
        {
            Console.WriteLine(m_PlayerName + " RECV -=" + e.Str + "=-");
        }

        void m_CommandObserver_BetTurnEndedCommandReceived(object sender, CommandEventArgs<BetTurnEndedCommand> e)
        {
            BetTurnEndedCommand command = e.Command;
            List<int> amounts = command.PotsAmounts;
            m_PokerTable.Pots.Clear();
            m_PokerTable.TotalPotAmnt = 0;
            m_PokerTable.HigherBet = 0;
            for (int i = 0; i < amounts.Count && amounts[i] > 0; ++i)
            {
                m_PokerTable.Pots.Add(new MoneyPot(i, amounts[i]));
                m_PokerTable.TotalPotAmnt += amounts[i];
            }
            foreach (PlayerInfo p in m_PokerTable.Players)
            {
                p.MoneyBetAmnt = 0;
            }
            GameBettingRoundEnded(this, new RoundEventArgs(command.Round));
        }

        void m_CommandObserver_BetTurnStartedCommandReceived(object sender, CommandEventArgs<BetTurnStartedCommand> e)
        {
            BetTurnStartedCommand command = e.Command;
            GameCard[] cards = new GameCard[5];
            for (int i = 0; i < 5; ++i)
            {
                cards[i] = new GameCard(command.CardsID[i]);
            }
            m_PokerTable.SetCards(cards[0], cards[1], cards[2], cards[3], cards[4]);
            m_PokerTable.Round = command.Round;
            if (m_PokerTable.Round == TypeRound.Preflop)
            {
                m_PokerTable.NoSeatLastRaise = m_PokerTable.GetPlayingPlayerNextTo(m_PokerTable.NoSeatBigBlind).NoSeat;
            }
            else
            {
                m_PokerTable.NoSeatLastRaise = m_PokerTable.GetPlayingPlayerNextTo(m_PokerTable.NoSeatDealer).NoSeat;
            }
            GameBettingRoundStarted(this, new RoundEventArgs(command.Round));
        }

        void m_CommandObserver_GameEndedCommandReceived(object sender, CommandEventArgs<GameEndedCommand> e)
        {
            m_PokerTable.TotalPotAmnt = 0;
            GameEnded(this, new EventArgs());
        }

        void m_CommandObserver_GameStartedCommandReceived(object sender, CommandEventArgs<GameStartedCommand> e)
        {
            GameStartedCommand command = e.Command;
            m_PokerTable.NoSeatDealer = command.NoSeatD;
            m_PokerTable.NoSeatSmallBlind = command.NoSeatSB;
            m_PokerTable.NoSeatBigBlind = command.NoSeatBB;

            // TODO: RICK: This is nice but, si le player passe pas par tcp (direct, hooking, etc) il saura pas quoi faire lors des blinds.
            if (m_PokerTable.NoSeatSmallBlind == m_TablePosition)
                Send(new PlayerPlayMoneyCommand(m_PokerTable.SmallBlindAmnt));
            if (m_PokerTable.NoSeatBigBlind == m_TablePosition)
                Send(new PlayerPlayMoneyCommand(m_PokerTable.BigBlindAmnt));

            GameBlindNeeded(this, new EventArgs());
        }

        void m_CommandObserver_PlayerHoleCardsChangedCommandReceived(object sender, CommandEventArgs<PlayerHoleCardsChangedCommand> e)
        {
            PlayerHoleCardsChangedCommand command = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(command.PlayerPos);
            if (p != null)
            {
                p.IsPlaying = command.IsPlaying;
                List<int> ids = command.CardsID;
                GameCard[] cards = new GameCard[2];
                cards[0] = new GameCard(ids[0]);
                cards[1] = new GameCard(ids[1]);
                p.Cards = cards;
                PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p));
            }
        }

        void m_CommandObserver_PlayerJoinedCommandReceived(object sender, CommandEventArgs<PlayerJoinedCommand> e)
        {
            PlayerJoinedCommand command = e.Command;
            PlayerInfo p = new PlayerInfo(command.PlayerPos, command.PlayerName, command.PlayerMoney);
            if (p != null)
            {
                m_PokerTable.ForceJoinTable(p, command.PlayerPos);
                PlayerJoined(this, new PlayerInfoEventArgs(p));
            }
        }

        void m_CommandObserver_PlayerLeftCommandReceived(object sender, CommandEventArgs<PlayerLeftCommand> e)
        {
            PlayerLeftCommand command = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(command.PlayerPos);
            if (p != null)
            {
                m_PokerTable.LeaveTable(p);
                PlayerLeaved(this, new PlayerInfoEventArgs(p));
            }
        }

        void m_CommandObserver_PlayerMoneyChangedCommandReceived(object sender, CommandEventArgs<PlayerMoneyChangedCommand> e)
        {
            PlayerMoneyChangedCommand command = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(command.PlayerPos);
            if (p != null)
            {
                p.MoneySafeAmnt = command.PlayerMoney;
                PlayerMoneyChanged(this, new PlayerInfoEventArgs(p));
            }
        }

        void m_CommandObserver_PlayerTurnBeganCommandReceived(object sender, CommandEventArgs<PlayerTurnBeganCommand> e)
        {
            PlayerTurnBeganCommand command = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(command.PlayerPos);
            PlayerInfo l = m_PokerTable.GetPlayer(command.LastPlayerNoSeat);
            if (p != null)
            {
                m_PokerTable.NoSeatCurrPlayer = command.PlayerPos;
                PlayerActionNeeded(this, new HistoricPlayerInfoEventArgs(p, l));
            }
        }

        void m_CommandObserver_PlayerTurnEndedCommandReceived(object sender, CommandEventArgs<PlayerTurnEndedCommand> e)
        {
            PlayerTurnEndedCommand command = e.Command;
            if (m_PokerTable.HigherBet < command.PlayerBet)
            {
                m_PokerTable.HigherBet = command.PlayerBet;
            }
            m_PokerTable.TotalPotAmnt = command.TotalPot;
            PlayerInfo p = m_PokerTable.GetPlayer(command.PlayerPos);
            if (p != null)
            {
                if (command.ActionType == TypeAction.Raise)
                {
                    m_PokerTable.NoSeatLastRaise = p.NoSeat;
                }
                p.MoneyBetAmnt = command.PlayerBet;
                p.MoneySafeAmnt = command.PlayerMoney;
                p.IsPlaying = command.IsPlaying;
                PlayerActionTaken(this, new PlayerActionEventArgs(p, command.ActionType, command.ActionAmount));
            }
        }

        void m_CommandObserver_PlayerWonPotCommandReceived(object sender, CommandEventArgs<PlayerWonPotCommand> e)
        {
            PlayerWonPotCommand command = e.Command;
            PlayerInfo p = m_PokerTable.GetPlayer(command.PlayerPos);
            if (p != null)
            {
                p.MoneySafeAmnt = command.PlayerMoney;
                PlayerWonPot(this, new PotWonEventArgs(p, command.PotID, command.Shared));
            }
        }

        void m_CommandObserver_TableClosedCommandReceived(object sender, CommandEventArgs<TableClosedCommand> e)
        {
            EverythingEnded(this, new EventArgs());
        }

        void m_CommandObserver_TableInfoCommandReceived(object sender, CommandEventArgs<TableInfoCommand> e)
        {
            TableInfoCommand command = e.Command;
            m_PokerTable.TotalPotAmnt = command.TotalPotAmount;
            m_PokerTable.BetLimit = command.Limit;
            List<int> amounts = command.PotsAmount;
            m_PokerTable.Pots.Clear();
            for (int i = 0; i < amounts.Count && amounts[i] > 0; ++i)
            {
                m_PokerTable.Pots.Add(new MoneyPot(i, amounts[i]));
                m_PokerTable.TotalPotAmnt += amounts[i];
            } GameCard[] cards = new GameCard[5];
            for (int i = 0; i < 5; ++i)
            {
                cards[i] = new GameCard(command.BoardCardIDs[i]);
            }
            m_PokerTable.SetCards(cards[0], cards[1], cards[2], cards[3], cards[4]);

            foreach (PlayerInfo p in m_PokerTable.Players)
            {
                m_PokerTable.LeaveTable(p);
            }
            for (int i = 0; i < command.NbPlayers; ++i)
            {
                TuplePlayerInfo seat = command.Seats[i];
                if (seat.IsEmpty)
                {
                    continue;
                }
                int noSeat = seat.NoSeat;
                PlayerInfo p = new PlayerInfo(noSeat, seat.PlayerName, seat.Money);
                m_PokerTable.ForceJoinTable(p, noSeat);

                List<int> ids = seat.HoleCardIDs;
                GameCard[] c = new GameCard[2];
                c[0] = new GameCard(ids[0]);
                c[1] = new GameCard(ids[1]);
                p.Cards = c;
                p.IsPlaying = seat.IsPlaying;

                if (seat.IsDealer)
                    m_PokerTable.NoSeatDealer = noSeat;
                if (seat.IsSmallBlind)
                    m_PokerTable.NoSeatSmallBlind = noSeat;
                if (seat.IsBigBlind)
                    m_PokerTable.NoSeatBigBlind = noSeat;
                if (seat.IsCurrentPlayer)
                    m_PokerTable.NoSeatCurrPlayer = noSeat;

                p.MoneyBetAmnt = seat.Bet;

                PlayerHoleCardsChanged(this, new PlayerInfoEventArgs(p));

            }
            GameGenerallyUpdated(this, new EventArgs());
        }
    }
}
