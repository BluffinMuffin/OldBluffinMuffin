using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PokerWorld.Game;
using EricUtility.Games.CardGame;

namespace BluffinPokerGui.Game
{
    public partial class TableViewerForm : AbstractTableForm
    {
        protected readonly PokerPlayerHud[] huds = new PokerPlayerHud[10];
        protected readonly Label[] bets = new Label[10];
        protected readonly Label[] potTitles = new Label[10];
        protected readonly Label[] potValues = new Label[10];
        protected readonly CardPictureBox[] board = new CardPictureBox[5];

        public TableViewerForm()
        {
            InitializeComponent();
            huds[0] = pokerPlayerHud1;
            huds[1] = pokerPlayerHud2;
            huds[2] = pokerPlayerHud3;
            huds[3] = pokerPlayerHud4;
            huds[4] = pokerPlayerHud5;
            huds[5] = pokerPlayerHud6;
            huds[6] = pokerPlayerHud7;
            huds[7] = pokerPlayerHud8;
            huds[8] = pokerPlayerHud9;
            huds[9] = pokerPlayerHud10;
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
            logConsole.RelativeSizeChanged += new EventHandler<IntEventArgs>(logConsole_RelativeSizeChanged);
        }

        void logConsole_RelativeSizeChanged(object sender, IntEventArgs e)
        {
            Height += e.Value;
        }

        private void btnHelp_Click(object sender, EventArgs e)
        {
            new HandStrengthForm().Show();
        }

        public override void SetGame(PokerWorld.Game.IPokerGame c, int s)
        {
            base.SetGame(c, s);
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
            m_Game.GameBettingRoundEnded += new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundEnded);
            m_Game.GameBettingRoundStarted += new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundStarted);
            m_Game.GameBlindNeeded += new EventHandler(m_Game_GameBlindNeeded);
            m_Game.GameEnded += new EventHandler(m_Game_GameEnded);
            m_Game.GameGenerallyUpdated += new EventHandler(m_Game_GameGenerallyUpdated);
            m_Game.PlayerActionNeeded += new EventHandler<HistoricPlayerInfoEventArgs>(m_Game_PlayerActionNeeded);
            m_Game.PlayerActionTaken += new EventHandler<PlayerActionEventArgs>(m_Game_PlayerActionTaken);
            m_Game.PlayerHoleCardsChanged += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerHoleCardsChanged);
            m_Game.PlayerJoined += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerJoined);
            m_Game.PlayerLeaved += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerLeaved);
            m_Game.PlayerMoneyChanged += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerMoneyChanged);
            m_Game.PlayerWonPot += new EventHandler<PotWonEventArgs>(m_Game_PlayerWonPot);
        }

        private void InitializePokerObserverForConsole()
        {
            m_Game.EverythingEnded += new EventHandler(m_Game_EverythingEnded_Console);
            m_Game.GameBettingRoundEnded += new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundEnded_Console);
            m_Game.GameBettingRoundStarted += new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundStarted_Console);
            m_Game.GameBlindNeeded += new EventHandler(m_Game_GameBlindNeeded_Console);
            m_Game.GameEnded += new EventHandler(m_Game_GameEnded_Console);
            m_Game.GameGenerallyUpdated += new EventHandler(m_Game_GameGenerallyUpdated_Console);
            m_Game.PlayerActionNeeded += new EventHandler<HistoricPlayerInfoEventArgs>(m_Game_PlayerActionNeeded_Console);
            m_Game.PlayerActionTaken += new EventHandler<PlayerActionEventArgs>(m_Game_PlayerActionTaken_Console);
            m_Game.PlayerHoleCardsChanged += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerHoleCardsChanged_Console);
            m_Game.PlayerJoined += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerJoined_Console);
            m_Game.PlayerLeaved += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerLeaved_Console);
            m_Game.PlayerMoneyChanged += new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerMoneyChanged_Console);
            m_Game.PlayerWonPot += new EventHandler<PotWonEventArgs>(m_Game_PlayerWonPot_Console);
        }

        void m_Game_GameBettingRoundEnded(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundEnded), new object[] { sender, e });
                return;
            }
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
                huds[i].DoAction(TypeAction.DoNothing, 0);
                bets[i].Text = "";
            }
        }

        void m_Game_GameBettingRoundStarted(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundStarted), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            foreach (PlayerInfo p in table.PlayingPlayers)
                huds[p.NoSeat].Alive = true;
            int i = 0;
            for (; i < 5 && table.Cards[i].Id != GameCard.NO_CARD.Id; ++i)
                board[i].Card = table.Cards[i];
            for (; i < 5; ++i)
                board[i].Card = GameCard.HIDDEN;
        }

        void m_Game_GameBlindNeeded(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameBlindNeeded), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            lblTotalPot.Text = "$0";
            for(int i = 1; i < 10; ++i)
            {
                potTitles[i].Visible = false;
                potValues[i].Visible = false;
                potValues[i].Text = "$0";
            }
            potTitles[0].Visible = true;
            potValues[0].Visible = true;
            potValues[0].Text = "$0";
            huds[table.NoSeatDealer].SetDealer();
            huds[table.NoSeatSmallBlind].SetSmallBlind();
            huds[table.NoSeatBigBlind].SetBigBlind();
            for (int i = 0; i < 5; ++i)
                board[i].Card = GameCard.HIDDEN;
        }

        void m_Game_GameEnded(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameEnded), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            foreach (PlayerInfo p in table.Players)
            {
                if (p != null)
                {
                    PokerPlayerHud php = huds[p.NoSeat];
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
                    php.SetNotDealer();
                    php.SetNoBlind();
                    php.SetSleeping();
                    if (p.MoneySafeAmnt == 0)
                    {
                        php.SetCards(GameCard.NO_CARD, GameCard.NO_CARD);
                        php.Alive = false;
                    }
                    php.DoAction(TypeAction.DoNothing);
                }
            }
        }

        void m_Game_GameGenerallyUpdated(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameGenerallyUpdated), new object[] { sender, e });
                return;
            }
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
            foreach (PlayerInfo p in table.Players)
            {
                PokerPlayerHud php = huds[p.NoSeat];
                InstallPlayer(php, p);
            }
        }

        void m_Game_PlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<HistoricPlayerInfoEventArgs>(m_Game_PlayerActionNeeded), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            php.DoAction(TypeAction.DoNothing, 0);
            php.SetPlaying();
        }

        void m_Game_PlayerActionTaken(object sender, PlayerActionEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerActionEventArgs>(m_Game_PlayerActionTaken), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            Label bet = bets[p.NoSeat];
            TableInfo table = m_Game.Table;
            php.SetMoney(p.MoneySafeAmnt);
            php.SetSleeping();
            php.DoAction(e.Action, e.AmountPlayed);
            lblTotalPot.Text = "$" + table.TotalPotAmnt;
            if (e.Action == TypeAction.Fold)
                php.SetCards(GameCard.NO_CARD, GameCard.NO_CARD);
            if (p.MoneyBetAmnt > 0)
                bet.Text = "$" + p.MoneyBetAmnt;
        }

        void m_Game_PlayerHoleCardsChanged(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerHoleCardsChanged), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            php.SetCards (p.Cards[0], p.Cards[1]);
        }

        void m_Game_PlayerJoined(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerJoined), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            InstallPlayer(php, p);
        }

        void m_Game_PlayerLeaved(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerLeaved), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            php.Visible = false;
        }

        void m_Game_PlayerMoneyChanged(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerMoneyChanged), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            php.SetMoney(p.MoneySafeAmnt);
        }

        void m_Game_PlayerWonPot(object sender, PotWonEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PotWonEventArgs>(m_Game_PlayerWonPot), new object[] { sender,e });
                return;
            }
            PlayerInfo p = e.Player;
            PokerPlayerHud php = huds[p.NoSeat];
            php.SetMoney(p.MoneySafeAmnt);
            php.SetWinning();
        }


        void m_Game_EverythingEnded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_EverythingEnded_Console), new object[] { sender, e });
                return;
            }
            WriteLine("==> Table closed");
        }
        void m_Game_GameBettingRoundEnded_Console(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundEnded_Console), new object[] { sender, e });
                return;
            }
            //WriteLine("==> End of " + e.Round.ToString());
        }

        void m_Game_GameBettingRoundStarted_Console(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundStarted_Console), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            WriteLine("==> Beginning of " + e.Round.ToString());
            if (e.Round != TypeRound.Preflop)
            {
                Write("==> Current board cards:");
                for (int i = 0; i < 5 && table.Cards[i].Id != GameCard.NO_CARD.Id; ++i)
                {
                    Write(" " + table.Cards[i].ToString());
                }
                WriteLine("");
            }
        }

        void m_Game_GameBlindNeeded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameBlindNeeded_Console), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            WriteLine("==> Game started");
            PlayerInfo d = table.Players[table.NoSeatDealer];
            PlayerInfo sb = table.Players[table.NoSeatSmallBlind];
            PlayerInfo bb = table.Players[table.NoSeatBigBlind];
            WriteLine("==> " + d.Name + " is the Dealer");
            WriteLine("==> " + sb.Name + " is the SmallBlind");
            WriteLine("==> " + bb.Name + " is the BigBlind");
        }

        void m_Game_GameEnded_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameEnded_Console), new object[] { sender, e });
                return;
            }
            WriteLine("==> End of the Game");
        }

        void m_Game_GameGenerallyUpdated_Console(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameGenerallyUpdated_Console), new object[] { sender, e });
                return;
            }
            WriteLine("==> Table info received");
        }

        void m_Game_PlayerActionNeeded_Console(object sender, HistoricPlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<HistoricPlayerInfoEventArgs>(m_Game_PlayerActionNeeded_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            //WriteLine("Player turn began (" + p.Name + ")");
        }

        void m_Game_PlayerActionTaken_Console(object sender, PlayerActionEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerActionEventArgs>(m_Game_PlayerActionTaken_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            WriteLine(p.Name + " did [" + e.Action.ToString() + "]");
        }

        void m_Game_PlayerHoleCardsChanged_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerHoleCardsChanged_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            if (p.Cards[0].Id >= 0)
            {
                WriteLine("==> Hole Card changed for " + p.Name + ": " + p.Cards[0].ToString() + " " + p.Cards[1].ToString());
            }
        }

        void m_Game_PlayerJoined_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerJoined_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            WriteLine(p.Name + " joined the table");
        }

        void m_Game_PlayerLeaved_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerLeaved_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            WriteLine(p.Name + " left the table");
        }

        void m_Game_PlayerMoneyChanged_Console(object sender, PlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PlayerInfoEventArgs>(m_Game_PlayerMoneyChanged_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            //WriteLine(p.Name + " money changed to " + p.MoneySafeAmnt);
        }

        void m_Game_PlayerWonPot_Console(object sender, PotWonEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<PotWonEventArgs>(m_Game_PlayerWonPot_Console), new object[] { sender, e });
                return;
            }
            PlayerInfo p = e.Player;
            WriteLine(p.Name + " won pot ($" + e.AmountWon + ")");
        }

        private void InstallPlayer(PokerPlayerHud php, PlayerInfo player)
        {
            php.PlayerName = player.Name;
            php.DoAction(TypeAction.DoNothing);
            GameCard[] cards = player.Cards;
            php.SetCards(cards[0], cards[1]);
            php.SetMoney(player.MoneySafeAmnt);
            php.SetSleeping();
            php.Main = (m_NoSeat == player.NoSeat);
            php.Alive = player.IsPlaying;
            php.Visible = true;
        }
    }
}
