using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Linq;
using System.Windows.Forms;
using Com.Ericmas001.Games;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;
using Com.Ericmas001.Util;

namespace Com.Ericmas001.Game.Poker.GUI.Game
{
    public partial class TableViewerForm : AbstractTableForm
    {
        protected readonly PlayerHud[] huds = new PlayerHud[10];
        protected readonly Label[] bets = new Label[10];
        protected readonly Label[] potTitles = new Label[10];
        protected readonly Label[] potValues = new Label[10];
        protected readonly CardPictureBox[] board = new CardPictureBox[5];

        public TableViewerForm()
        {
            InitializeComponent();
            huds[0] = playerHud1;
            huds[1] = playerHud2;
            huds[2] = playerHud3;
            huds[3] = playerHud4;
            huds[4] = playerHud5;
            huds[5] = playerHud6;
            huds[6] = playerHud7;
            huds[7] = playerHud8;
            huds[8] = playerHud9;
            huds[9] = playerHud10;
            bets[0] = label1;
            bets[1] = label2;
            bets[2] = label3;
            bets[3] = label4;
            bets[4] = label5;
            bets[5] = label6;
            bets[6] = label7;
            bets[7] = label8;
            bets[8] = label9;
            bets[9] = label10;
            board[0] = cardPictureBox1;
            board[1] = cardPictureBox2;
            board[2] = cardPictureBox3;
            board[3] = cardPictureBox4;
            board[4] = cardPictureBox5;
            potTitles[0] = lblPot0Title;
            potTitles[1] = lblPot1Title;
            potTitles[2] = lblPot2Title;
            potTitles[3] = lblPot3Title;
            potTitles[4] = lblPot4Title;
            potTitles[5] = lblPot5Title;
            potTitles[6] = lblPot6Title;
            potTitles[7] = lblPot7Title;
            potTitles[8] = lblPot8Title;
            potTitles[9] = lblPot9Title;
            potValues[0] = lblPot0;
            potValues[1] = lblPot1;
            potValues[2] = lblPot2;
            potValues[3] = lblPot3;
            potValues[4] = lblPot4;
            potValues[5] = lblPot5;
            potValues[6] = lblPot6;
            potValues[7] = lblPot7;
            potValues[8] = lblPot8;
            potValues[9] = lblPot9;
            logConsole.RelativeSizeChanged += OnConsoleRelativeSizeChanged;
        }

        void OnConsoleRelativeSizeChanged(object sender, IntEventArgs e)
        {
            Height += e.Value;
        }

        private void btnHelp_Click(object sender, EventArgs e)
        {
            new HandStrengthForm().Show();
        }

        public override void SetGame(IPokerGame c, string n)
        {
            base.SetGame(c, n);
            InitializePokerObserverForGUI();
            InitializePokerObserverForConsole();
        }

        public void WriteLine(string line)
        {
            logConsole.WriteLine(line);
        }

        public void Write(string msg)
        {
            logConsole.Write(msg);
        }

        private void InitializePokerObserverForGUI()
        {
            m_Game.Observer.GameBettingRoundEnded += OnGameBettingRoundEnded;
            m_Game.Observer.GameBettingRoundStarted += OnGameBettingRoundStarted;
            m_Game.Observer.GameEnded += OnGameEnded;
            m_Game.Observer.GameGenerallyUpdated += OnGameGenerallyUpdated;
            m_Game.Observer.PlayerActionNeeded += OnPlayerActionNeeded;
            m_Game.Observer.PlayerActionTaken += OnPlayerActionTaken;
            m_Game.Observer.PlayerHoleCardsChanged += OnPlayerHoleCardsChanged;
            m_Game.Observer.SeatUpdated += OnSeatUpdated;
            m_Game.Observer.PlayerLeft += OnPlayerLeft;
            m_Game.Observer.PlayerMoneyChanged += OnPlayerMoneyChanged;
            m_Game.Observer.PlayerWonPot += OnPlayerWonPot;
        }

        private void InitializePokerObserverForConsole()
        {
            m_Game.Observer.EverythingEnded += OnEverythingEnded_Console;
            m_Game.Observer.GameBettingRoundStarted += OnGameBettingRoundStarted_Console;
            m_Game.Observer.GameBlindNeeded += OnGameBlindNeeded_Console;
            m_Game.Observer.GameEnded += OnGameEnded_Console;
            m_Game.Observer.GameGenerallyUpdated += OnGameGenerallyUpdated_Console;
            m_Game.Observer.PlayerActionTaken += OnPlayerActionTaken_Console;
            m_Game.Observer.PlayerHoleCardsChanged += OnPlayerHoleCardsChanged_Console;
            m_Game.Observer.PlayerJoined += OnPlayerJoined_Console;
            m_Game.Observer.SeatUpdated += OnSeatUpdated_Console;
            m_Game.Observer.PlayerLeft += OnPlayerLeft_Console;
            m_Game.Observer.PlayerWonPot += OnPlayerWonPot_Console;
        }

        void OnGameBettingRoundEnded(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(OnGameBettingRoundEnded), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            TableInfo table = m_Game.Table;
            foreach( MoneyPot p in table.Pots)
            {
                int i = p.Id;
                potTitles[i].Visible = (i == 0 || p.Amount > 0);
                potValues[i].Visible = (i == 0 || p.Amount > 0);
                potValues[i].Text = "$" + p.Amount;
            }
            for (int i = 0; i < huds.Length; ++i)
            {
                huds[i].DoAction(GameActionEnum.DoNothing, 0);
                bets[i].Text = "";
            }
            ResumeLayout();
        }

        void OnGameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(OnGameBettingRoundStarted), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            TableInfo table = m_Game.Table;
            foreach (PlayerInfo p in table.Players)
                huds[p.NoSeat].Alive = true;
            int i = 0;
            for (; i < 5 && table.Cards[i].Id != GameCard.NO_CARD.Id; ++i)
                board[i].Card = table.Cards[i];
            for (; i < 5; ++i)
                board[i].Card = GameCard.HIDDEN;
            ResumeLayout();
        }

        void OnGameEnded(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameEnded), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            TableInfo table = m_Game.Table;
            foreach (PlayerInfo p in table.Players)
            {
                if (p != null)
                {
                    PlayerHud php = huds[p.NoSeat];
                    Label bet = bets[p.NoSeat];
                    bet.Text = "";
                    for (int i = 1; i < 10; ++i)
                    {
                        potTitles[i].Visible = false;
                        potValues[i].Visible = false;
                        potValues[i].Text = "$0";
                    }
                    potTitles[0].Visible = true;
                    potValues[0].Visible = true;
                    potValues[0].Text = "$0";
                    php.SetMoney(p.MoneySafeAmnt);
                    php.SetDealerButtonVisible(false);
                    php.SetNoBlind();
                    php.SetSleeping();
                    if (p.MoneySafeAmnt == 0)
                    {
                        php.SetCards(GameCard.NO_CARD, GameCard.NO_CARD);
                        php.Alive = false;
                    }
                    php.DoAction(GameActionEnum.DoNothing);
                }
            }
            ResumeLayout();
        }

        void OnGameGenerallyUpdated(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameGenerallyUpdated), new object[] { sender, e });
                return;
            }
            lock (m_Game.Table)
            {

                SuspendLayout();
                lblTotalPot.Text = "$0";
                for (int i = 1; i < 10; ++i)
                {
                    potTitles[i].Visible = false;
                    potValues[i].Visible = false;
                    potValues[i].Text = "$0";
                }
                potTitles[0].Visible = true;
                potValues[0].Visible = true;
                potValues[0].Text = "$0";

                TableInfo table = m_Game.Table;

                for (int i = 0; i < 5; ++i)
                    board[i].Card = m_Game.Table.Cards[i];

                foreach (SeatInfo si in table.Seats)
                {
                    PlayerHud php = huds[si.NoSeat];
                    InstallPlayer(php, si);
                }

                //Set Small Blind Icon
                m_Game.Table.Seats.Where(x => x.Attributes.Contains(SeatAttributeEnum.SmallBlind)).ToList().ForEach(x => huds[x.NoSeat].SetSmallBlind());

                //Set Big Blind Icon
                m_Game.Table.Seats.Where(x => x.Attributes.Contains(SeatAttributeEnum.BigBlind)).ToList().ForEach(x => huds[x.NoSeat].SetBigBlind());

                ResumeLayout();
            }
        }

        void OnPlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<HistoricPlayerInfoEventArgs>(OnPlayerActionNeeded), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            PlayerInfo p = e.Player;
            PlayerHud php = huds[p.NoSeat];
            php.DoAction(GameActionEnum.DoNothing, 0);
            php.SetPlaying();
            ResumeLayout();
        }

        void OnPlayerActionTaken(object sender, PlayerActionEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerActionEventArgs>(OnPlayerActionTaken), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            PlayerInfo p = e.Player;
            PlayerHud php = huds[p.NoSeat];
            Label bet = bets[p.NoSeat];
            TableInfo table = m_Game.Table;
            php.SetMoney(p.MoneySafeAmnt);
            php.SetSleeping();
            php.DoAction(e.Action, e.AmountPlayed);
            lblTotalPot.Text = "$" + table.TotalPotAmnt;
            if (e.Action == GameActionEnum.Fold)
                php.SetCards(GameCard.NO_CARD, GameCard.NO_CARD);
            if (p.MoneyBetAmnt > 0)
                bet.Text = "$" + p.MoneyBetAmnt;
            ResumeLayout();
        }

        void OnPlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerHoleCardsChanged), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            PlayerInfo p = e.Player;
            PlayerHud php = huds[p.NoSeat];
            if(p.HoleCards.Count == 2)
                php.SetCards (p.HoleCards[0], p.HoleCards[1]);
            else
                php.SetCards(null, null);
            ResumeLayout();
        }

        void OnSeatUpdated(object sender, SeatEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<SeatEventArgs>(OnSeatUpdated), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            if( e.Seat.IsEmpty)
                huds[e.Seat.NoSeat].Visible = false;
            else
                InstallPlayer(huds[e.Seat.NoSeat], e.Seat);
            ResumeLayout();
        }

        void OnPlayerLeft(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerLeft), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            PlayerInfo p = e.Player;
            PlayerHud php = huds[p.NoSeat];
            php.Visible = false;
            ResumeLayout();
        }

        void OnPlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerMoneyChanged), new object[] { sender, e });
                return;
            }
            SuspendLayout();
            PlayerInfo p = e.Player;
            PlayerHud php = huds[p.NoSeat];
            php.SetMoney(p.MoneySafeAmnt);
            ResumeLayout();
        }

        void OnPlayerWonPot(object sender, PotWonEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PotWonEventArgs>(OnPlayerWonPot), new object[] { sender,e });
                return;
            }
            SuspendLayout();
            PlayerInfo p = e.Player;
            PlayerHud php = huds[p.NoSeat];
            php.SetMoney(p.MoneySafeAmnt);
            php.SetWinning();
            ResumeLayout();
        }


        void OnEverythingEnded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnEverythingEnded_Console), new object[] { sender, e });
                return;
            }
            WriteLine("==> Table closed");
        }

        void OnGameBettingRoundStarted_Console(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(OnGameBettingRoundStarted_Console), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            WriteLine("==> Beginning of " + e.Round.ToString());
            if (e.Round != RoundTypeEnum.Preflop)
            {
                Write("==> Current board cards:");
                for (int i = 0; i < 5 && table.Cards[i].Id != GameCard.NO_CARD.Id; ++i)
                {
                    Write(" " + table.Cards[i].ToString());
                }
                WriteLine("");
            }
        }

        void OnGameBlindNeeded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameBlindNeeded_Console), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            WriteLine("==> Game started");
        }

        void OnGameEnded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameEnded_Console), new object[] { sender, e });
                return;
            }
            WriteLine("==> End of the Game");
        }

        void OnGameGenerallyUpdated_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameGenerallyUpdated_Console), new object[] { sender, e });
                return;
            }
            WriteLine("==> Table info received");
            if (m_Game.Table.NoSeatDealer >= 0)
                WriteLine("==> " + m_Game.Table.Seats[m_Game.Table.NoSeatDealer].Player.Name + " is the Dealer");

            m_Game.Table.Seats.Where(x => x.Attributes.Contains(SeatAttributeEnum.SmallBlind)).ToList().ForEach(x => WriteLine("==> " + x.Player.Name + " is the SmallBlind"));
            m_Game.Table.Seats.Where(x => x.Attributes.Contains(SeatAttributeEnum.BigBlind)).ToList().ForEach(x => WriteLine("==> " + x.Player.Name + " is the BigBlind"));
        }

        void OnPlayerActionTaken_Console(object sender, PlayerActionEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerActionEventArgs>(OnPlayerActionTaken_Console), new object[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " did [" + e.Action.ToString() + "]");
        }

        void OnPlayerHoleCardsChanged_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerHoleCardsChanged_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            if (p.HoleCards[0].Id >= 0)
                WriteLine("==> Hole Card changed for " + p.Name + ": " + p.HoleCards[0].ToString() + " " + p.HoleCards[1].ToString());
        }

        void OnPlayerJoined_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerJoined_Console), new object[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " joined the table");
        }

        void OnSeatUpdated_Console(object sender, SeatEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<SeatEventArgs>(OnSeatUpdated_Console), new object[] { sender, e });
                return;
            }
            SeatInfo s = e.Seat;
            if(e.Seat.IsEmpty)
                WriteLine("The seat #" + s.NoSeat + " is now inoccupied");
            else
                WriteLine(s.Player.Name + " sat in at seat #" + s.NoSeat);
        }

        void OnPlayerLeft_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerLeft_Console), new object[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " left the table");
        }

        void OnPlayerWonPot_Console(object sender, PotWonEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PotWonEventArgs>(OnPlayerWonPot_Console), new object[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " won pot ($" + e.AmountWon + ")");
        }

        private void InstallPlayer(PlayerHud php, SeatInfo seat)
        {
            if (seat.IsEmpty)
                php.Visible = false;
            else
            {
                PlayerInfo player = seat.Player;
                php.PlayerName = player.Name;
                php.DoAction(GameActionEnum.DoNothing);
                GameCard[] cards = player.HoleCards.ToArray();
                php.SetCards(cards[0], cards[1]);
                php.SetMoney(player.MoneySafeAmnt);
                php.SetSleeping();
                php.Main = (m_NoSeat == player.NoSeat);
                php.Alive = player.State == PlayerStateEnum.Playing;
                php.Visible = true;
                php.SetDealerButtonVisible(seat.Attributes.Contains(SeatAttributeEnum.Dealer));
            }
        }
    }
}
