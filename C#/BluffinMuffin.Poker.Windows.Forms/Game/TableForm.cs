using System;
using System.Drawing;
using System.Windows.Forms;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.EventHandling;
using BluffinMuffin.Protocol.DataTypes;
using Com.Ericmas001.Util;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public partial class TableForm : TableViewerForm
    {
        protected virtual int GetSitInMoneyAmount() { return 1500; }

        protected TableForm()
        {
            InitializeComponent();
            DisableButtons();
            DisableButton(btnSitOut);
        }

        private void btnFold_Click(object sender, EventArgs e)
        {
            DisableButtons();
            var table = m_Game.Table;
            var p = table.Seats[m_NoSeat].Player;
            m_Game.PlayMoney(p, -1);
        }

        private void btnCall_Click(object sender, EventArgs e)
        {
            DisableButtons();
            var table = m_Game.Table;
            var p = table.Seats[m_NoSeat].Player;
            m_Game.PlayMoney(p, table.CallAmnt(p));
        }

        private void btnRaise_Click(object sender, EventArgs e)
        {
            DisableButtons();
            var table = m_Game.Table;
            var p = table.Seats[m_NoSeat].Player;
            m_Game.PlayMoney(p, (int)nudRaise.Value - p.MoneyBetAmnt);
        }

        private void DisableButtons()
        {
            DisableButton(btnCall);
            DisableButton(btnRaise);
            DisableButton(btnFold);
            nudRaise.Enabled = false;
        }

        private void DisableButton(Button btn)
        {
            if (btn.Enabled)
            {
                btn.Enabled = false;
                btn.Tag = btn.BackColor;
                btn.BackColor = Color.DimGray;
            }
        }

        private void EnableButton(Button btn)
        {
            if (!btn.Enabled)
            {
                btn.Enabled = true;
                btn.BackColor = (Color)btn.Tag;
            }
        }
        public override void SetGame(IPokerGame c)
        {
            base.SetGame(c);
            m_Game.Observer.PlayerActionNeeded += OnPlayerActionNeeded;
            m_Game.Observer.SitInResponseReceived += OnSitInResponseReceived;
            m_Game.Observer.SitOutResponseReceived += OnSitOutResponseReceived;
            m_Game.Observer.SeatUpdated += OnGameGenerallyUpdated;
            m_Game.Observer.GameGenerallyUpdated += OnGameGenerallyUpdated;
        }

        void OnSitOutResponseReceived(bool success)
        {
            SitOutEnabled(!success);
            if (success)
            {
                m_NoSeat = -1;
                SitInButtonsShowing(true);
            }
        }

        void OnGameGenerallyUpdated(object sender, EventArgs e)
        {
            SitInButtonsShowing(m_NoSeat == -1);
        }

        void OnPlayerActionNeeded(object sender, HistoricPlayerInfoEventArgs e)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EventHandler<HistoricPlayerInfoEventArgs>(OnPlayerActionNeeded), new[] { sender, e });
                return;
            }
            var p = e.Player;
            var table = m_Game.Table;
            if (p.NoSeat == m_NoSeat)
            {
                EnableButton(btnFold);
                SetCallButtonName(p);
                EnableButton(btnCall);
                if (table.HigherBet < p.MoneyAmnt)
                {
                    var min = table.MinRaiseAmnt(p) + p.MoneyBetAmnt;
                    EnableButton(btnRaise);
                    nudRaise.Enabled = true;
                    nudRaise.Minimum = min;
                    nudRaise.Maximum = p.MoneyAmnt;
                    nudRaise.Value = min;
                    nudRaise.Increment = min;
                }
            }
        }

        private void SetCallButtonName(PlayerInfo p)
        {
            var table = m_Game.Table;
            var s = "CALL";
            if (table.CanCheck(p))
                s = "CHECK";
            else if (table.HigherBet >= p.MoneyAmnt)
                s = "ALL-IN";
            btnCall.Text = s;
        }

        private void btnSitIn_Click(object sender, EventArgs e)
        {
            var money = GetSitInMoneyAmount();
            if (money >= 0)
            {
                SitInButtonsShowing(false);
                var name = ((Control)sender).Name;
                var seatWanted = int.Parse(name.Substring(name.Length - 1));
                m_Game.AfterPlayerSat(null, seatWanted, money);
            }
        }

        void OnSitInResponseReceived(int noSeat)
        {
            m_NoSeat = noSeat;
            SitOutEnabled(noSeat != -1);
            SitInButtonsShowing(noSeat == -1);
        }

        private void SitInButtonsShowing(bool visible)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new BooleanHandler(SitInButtonsShowing), visible);
                return;
            }
            SuspendLayout();
            for (var i = 0; i < 10; ++i )
            {
                var btnSitIn = Controls["btnSitIn" + i] as Button;
                if (i < m_Game.Table.Seats.Count && m_Game.Table.Seats[i].IsEmpty)
                {
                    if (btnSitIn != null) btnSitIn.Visible = visible;
                }
                else if (btnSitIn != null) btnSitIn.Visible = false;
            }
            ResumeLayout();
        }

        private void SitOutEnabled(bool enable)
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new BooleanHandler(SitOutEnabled), enable);
                return;
            }
            SuspendLayout();
            if(enable)
                EnableButton(btnSitOut);
            else
                DisableButton(btnSitOut);
            ResumeLayout();
        }

        private void btnSitOut_Click(object sender, EventArgs e)
        {
            DisableButtons();
            SitOutEnabled(false);
            m_Game.SitOut(null);
        }
    }
}
