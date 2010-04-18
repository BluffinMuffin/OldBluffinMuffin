using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
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
        }

        private void btnHelp_Click(object sender, EventArgs e)
        {

        }

        public override void SetGame(PokerWorld.Game.IPokerGame c, int s)
        {
            base.SetGame(c, s);
            InitializePokerObserverForGUI();
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

        void m_Game_GameBettingRoundEnded(object sender, RoundEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<RoundEventArgs>(m_Game_GameBettingRoundEnded), new object[] { sender, e });
                return;
            }
            TableInfo table = m_Game.Table;
            // TODO: RICK: update POTS
            foreach (PlayerInfo p in table.Players)
            {
                huds[p.NoSeat].DoAction(TypeAction.DoNothing, 0);
                bets[p.NoSeat].Text = "";
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
                PokerPlayerHud php = huds[p.NoSeat];
                Label bet = bets[p.NoSeat];
                bet.Text = "";
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

        void m_Game_GameGenerallyUpdated(object sender, EventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<EventArgs>(m_Game_GameGenerallyUpdated), new object[] { sender, e });
                return;
            }
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
