using System;
using System.Linq;
using System.Windows.Forms;
using BluffinMuffin.Poker.Windows.Forms.Properties;
using BluffinMuffin.Protocol.DataTypes;
using BluffinMuffin.Protocol.DataTypes.Enums;
using Com.Ericmas001.Games;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.EventHandling;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public partial class TableViewerForm : AbstractTableForm
    {
        private readonly PlayerHud[] m_Huds = new PlayerHud[10];
        private readonly Label[] m_Bets = new Label[10];
        private readonly Label[] m_PotTitles = new Label[10];
        private readonly Label[] m_PotValues = new Label[10];
        private readonly CardPictureBox[] m_Board = new CardPictureBox[5];

        protected TableViewerForm()
        {
            InitializeComponent();
            m_Huds[0] = playerHud1;
            m_Huds[1] = playerHud2;
            m_Huds[2] = playerHud3;
            m_Huds[3] = playerHud4;
            m_Huds[4] = playerHud5;
            m_Huds[5] = playerHud6;
            m_Huds[6] = playerHud7;
            m_Huds[7] = playerHud8;
            m_Huds[8] = playerHud9;
            m_Huds[9] = playerHud10;
            m_Bets[0] = label1;
            m_Bets[1] = label2;
            m_Bets[2] = label3;
            m_Bets[3] = label4;
            m_Bets[4] = label5;
            m_Bets[5] = label6;
            m_Bets[6] = label7;
            m_Bets[7] = label8;
            m_Bets[8] = label9;
            m_Bets[9] = label10;
            m_Board[0] = cardPictureBox1;
            m_Board[1] = cardPictureBox2;
            m_Board[2] = cardPictureBox3;
            m_Board[3] = cardPictureBox4;
            m_Board[4] = cardPictureBox5;
            m_PotTitles[0] = lblPot0Title;
            m_PotTitles[1] = lblPot1Title;
            m_PotTitles[2] = lblPot2Title;
            m_PotTitles[3] = lblPot3Title;
            m_PotTitles[4] = lblPot4Title;
            m_PotTitles[5] = lblPot5Title;
            m_PotTitles[6] = lblPot6Title;
            m_PotTitles[7] = lblPot7Title;
            m_PotTitles[8] = lblPot8Title;
            m_PotTitles[9] = lblPot9Title;
            m_PotValues[0] = lblPot0;
            m_PotValues[1] = lblPot1;
            m_PotValues[2] = lblPot2;
            m_PotValues[3] = lblPot3;
            m_PotValues[4] = lblPot4;
            m_PotValues[5] = lblPot5;
            m_PotValues[6] = lblPot6;
            m_PotValues[7] = lblPot7;
            m_PotValues[8] = lblPot8;
            m_PotValues[9] = lblPot9;
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

        public override void SetGame(IPokerGame c)
        {
            base.SetGame(c);
            InitializePokerObserverForGui();
            InitializePokerObserverForConsole();
        }

        private void WriteLine(string line)
        {
            LogManager.Log(LogLevel.Message, "Game", line);
            logConsole.WriteLine(line);
        }

        private void Write(string msg)
        {
            LogManager.Log(LogLevel.Message, "Game", msg);
            logConsole.Write(msg);
        }

        private void InitializePokerObserverForGui()
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
                BeginInvoke(new EventHandler<RoundEventArgs>(OnGameBettingRoundEnded), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var table = m_Game.Table;
            foreach( var p in table.Pots)
            {
                var i = p.Id;
                m_PotTitles[i].Visible = (i == 0 || p.Amount > 0);
                m_PotValues[i].Visible = (i == 0 || p.Amount > 0);
                m_PotValues[i].Text = Resources.PlayerHud_SetMoney_Dollar + p.Amount;
            }
            for (var i = 0; i < m_Huds.Length; ++i)
            {
                m_Huds[i].DoAction(GameActionEnum.DoNothing);
                m_Bets[i].Text = "";
            }
            ResumeLayout();
        }

        void OnGameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(OnGameBettingRoundStarted), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var table = m_Game.Table;
            foreach (var p in table.Players)
                m_Huds[p.NoSeat].Alive = true;
            var i = 0;
            for (; i < 5 && table.Cards[i].Id != GameCard.NoCard.Id; ++i)
                m_Board[i].Card = table.Cards[i];
            for (; i < 5; ++i)
                m_Board[i].Card = GameCard.Hidden;
            ResumeLayout();
        }

        void OnGameEnded(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameEnded), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var table = m_Game.Table;
            foreach (var p in table.Players)
            {
                if (p != null)
                {
                    var php = m_Huds[p.NoSeat];
                    var bet = m_Bets[p.NoSeat];
                    bet.Text = "";
                    for (var i = 1; i < 10; ++i)
                    {
                        m_PotTitles[i].Visible = false;
                        m_PotValues[i].Visible = false;
                        m_PotValues[i].Text = Resources.TableViewerForm_OnGameEnded_ZeroMoney;
                    }
                    m_PotTitles[0].Visible = true;
                    m_PotValues[0].Visible = true;
                    m_PotValues[0].Text = Resources.TableViewerForm_OnGameEnded_ZeroMoney;
                    php.SetMoney(p.MoneySafeAmnt);
                    php.SetDealerButtonVisible(false);
                    php.SetNoBlind();
                    php.SetSleeping();
                    //if (p.MoneySafeAmnt == 0)
                    //{
                        php.SetCards(GameCard.NoCard, GameCard.NoCard);
                        php.Alive = false;
                    //}
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
                BeginInvoke(new EventHandler<EventArgs>(OnGameGenerallyUpdated), new[] { sender, e });
                return;
            }
            lock (m_Game.Table)
            {


                var table = m_Game.Table;
                SuspendLayout();
                lblTotalPot.Text = Resources.PlayerHud_SetMoney_Dollar + table.TotalPotAmnt;
                for (var i = 1; i < 10; ++i)
                {
                    m_PotTitles[i].Visible = false;
                    m_PotValues[i].Visible = false;
                    m_PotValues[i].Text = Resources.TableViewerForm_OnGameEnded_ZeroMoney;
                }
                m_PotTitles[0].Visible = true;
                m_PotValues[0].Visible = true;
                m_PotValues[0].Text = Resources.TableViewerForm_OnGameEnded_ZeroMoney;

                foreach (var p in table.Pots)
                {
                    var i = p.Id;
                    m_PotTitles[i].Visible = (i == 0 || p.Amount > 0);
                    m_PotValues[i].Visible = (i == 0 || p.Amount > 0);
                    m_PotValues[i].Text = Resources.PlayerHud_SetMoney_Dollar + p.Amount;
                }

                var j = 0;
                for (; j < 5 && table.Cards[j].Id != GameCard.NoCard.Id; ++j)
                    m_Board[j].Card = table.Cards[j];
                for (; j < 5; ++j)
                    m_Board[j].Card = m_Game.IsPlaying ? GameCard.Hidden : GameCard.NoCard;

                foreach (var si in table.Seats)
                {
                    var php = m_Huds[si.NoSeat];
                    InstallPlayer(php, si);
                }
                for (var i = 0; i < m_Huds.Length; ++i)
                {
                    m_Huds[i].DoAction(GameActionEnum.DoNothing);
                    m_Bets[i].Text = table.Seats[i].IsEmpty || table.Seats[i].Player.MoneyBetAmnt == 0 ? "" : "$" + table.Seats[i].Player.MoneyBetAmnt;
                }

                //Set Small Blind Icon
                table.Seats.Where(x => x.Attributes.Contains(SeatAttributeEnum.SmallBlind)).ToList().ForEach(x => m_Huds[x.NoSeat].SetSmallBlind());

                //Set Big Blind Icon
                table.Seats.Where(x => x.Attributes.Contains(SeatAttributeEnum.BigBlind)).ToList().ForEach(x => m_Huds[x.NoSeat].SetBigBlind());

                if (table.CurrentPlayerSeat != null)
                    m_Huds[table.NoSeatCurrentPlayer].SetPlaying();


                ResumeLayout();
            }
        }

        void OnPlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<HistoricPlayerInfoEventArgs>(OnPlayerActionNeeded), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var p = e.Player;
            var php = m_Huds[p.NoSeat];
            php.DoAction(GameActionEnum.DoNothing);
            php.SetPlaying();
            ResumeLayout();
        }

        void OnPlayerActionTaken(object sender, PlayerActionEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerActionEventArgs>(OnPlayerActionTaken), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var p = e.Player;
            var php = m_Huds[p.NoSeat];
            var bet = m_Bets[p.NoSeat];
            var table = m_Game.Table;
            php.SetMoney(p.MoneySafeAmnt);
            php.SetSleeping();
            php.DoAction(e.Action, e.AmountPlayed);
            lblTotalPot.Text = Resources.PlayerHud_SetMoney_Dollar + table.TotalPotAmnt;
            if (e.Action == GameActionEnum.Fold)
                php.SetCards(GameCard.NoCard, GameCard.NoCard);
            if (p.MoneyBetAmnt > 0)
                bet.Text = Resources.PlayerHud_SetMoney_Dollar + p.MoneyBetAmnt;
            ResumeLayout();
        }

        void OnPlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerHoleCardsChanged), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var p = e.Player;
            var php = m_Huds[p.NoSeat];
            if(p.HoleCards.Length == 2)
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
                BeginInvoke(new EventHandler<SeatEventArgs>(OnSeatUpdated), new[] { sender, e });
                return;
            }
            SuspendLayout();
            if( e.Seat.IsEmpty)
                m_Huds[e.Seat.NoSeat].Visible = false;
            else
                InstallPlayer(m_Huds[e.Seat.NoSeat], e.Seat);
            ResumeLayout();
        }

        void OnPlayerLeft(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerLeft), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var p = e.Player;
            var php = m_Huds[p.NoSeat];
            php.Visible = false;
            ResumeLayout();
        }

        void OnPlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerMoneyChanged), new[] { sender, e });
                return;
            }
            SuspendLayout();
            var p = e.Player;
            var php = m_Huds[p.NoSeat];
            php.SetMoney(p.MoneySafeAmnt);
            ResumeLayout();
        }

        void OnPlayerWonPot(object sender, PotWonEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PotWonEventArgs>(OnPlayerWonPot), new[] { sender,e });
                return;
            }
            SuspendLayout();
            var p = e.Player;
            var php = m_Huds[p.NoSeat];
            php.SetMoney(p.MoneySafeAmnt);
            php.SetWinning();
            ResumeLayout();
        }


        void OnEverythingEnded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnEverythingEnded_Console), new[] { sender, e });
                return;
            }
            WriteLine("==> Table closed");
        }

        void OnGameBettingRoundStarted_Console(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(OnGameBettingRoundStarted_Console), new[] { sender, e });
                return;
            }
            var table = m_Game.Table;
            WriteLine("==> Beginning of " + e.Round);
            if (e.Round != RoundTypeEnum.Preflop)
            {
                Write("==> Current board cards:");
                for (var i = 0; i < 5 && table.Cards[i].Id != GameCard.NoCard.Id; ++i)
                {
                    Write(" " + table.Cards[i]);
                }
                WriteLine("");
            }
        }

        void OnGameBlindNeeded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameBlindNeeded_Console), new[] { sender, e });
                return;
            }
            WriteLine("==> Game started");
        }

        void OnGameEnded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameEnded_Console), new[] { sender, e });
                return;
            }
            WriteLine("==> End of the Game");
        }

        void OnGameGenerallyUpdated_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(OnGameGenerallyUpdated_Console), new[] { sender, e });
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
                BeginInvoke(new EventHandler<PlayerActionEventArgs>(OnPlayerActionTaken_Console), new[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " did [" + e.Action.ToString() + "]");
        }

        void OnPlayerHoleCardsChanged_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerHoleCardsChanged_Console), new[] { sender, e });
                return;
            }
            var p = e.Player;
            if (p.HoleCards[0].Id >= 0)
                WriteLine("==> Hole Card changed for " + p.Name + ": " + p.HoleCards[0] + " " + p.HoleCards[1]);
        }

        void OnPlayerJoined_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerJoined_Console), new[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " joined the table");
        }

        void OnSeatUpdated_Console(object sender, SeatEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<SeatEventArgs>(OnSeatUpdated_Console), new[] { sender, e });
                return;
            }
            var s = e.Seat;
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
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(OnPlayerLeft_Console), new[] { sender, e });
                return;
            }
            WriteLine(e.Player.Name + " left the table");
        }

        void OnPlayerWonPot_Console(object sender, PotWonEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PotWonEventArgs>(OnPlayerWonPot_Console), new[] { sender, e });
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
                var player = seat.Player;
                php.PlayerName = player.Name;
                php.DoAction(GameActionEnum.DoNothing);
                var cards = player.HoleCards.ToArray();
                php.SetMoney(player.MoneySafeAmnt);
                php.SetSleeping();
                php.Main = (m_NoSeat == player.NoSeat);
                php.Alive = player.State == PlayerStateEnum.Playing;
                if (php.Alive)
                    php.SetCards(cards[0], cards[1]);
                php.Visible = true;
                php.SetDealerButtonVisible(seat.Attributes.Contains(SeatAttributeEnum.Dealer));
            }
        }
    }
}
