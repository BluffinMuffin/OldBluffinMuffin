using System;
using System.Windows.Forms;
using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.DataTypes.EventHandling;
using Com.Ericmas001.Util;
using VIBlend.WinForms.Controls;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public partial class TableForm : TableViewerForm
    {
        protected virtual int GetSitInMoneyAmount() { return 1500; }
        public TableForm()
        {
            InitializeComponent();
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
            btnCall.Enabled = false;
            btnRaise.Enabled = false;
            btnFold.Enabled = false;
            nudRaise.Enabled = false;
        }
        public override void SetGame(IPokerGame c, string n)
        {
            base.SetGame(c, n);
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
                btnFold.Enabled = true;
                SetCallButtonName(p);
                btnCall.Enabled = true;
                if (table.HigherBet < p.MoneyAmnt)
                {
                    var min = table.MinRaiseAmnt(p) + p.MoneyBetAmnt;
                    btnRaise.Enabled = true;
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
                m_Game.SitIn(null, seatWanted, money);
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
                var btnSitIn = Controls["btnSitIn" + i] as vButton;
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
            btnSitOut.Enabled = enable;
            ResumeLayout();
        }

        private void btnSitOut_Click(object sender, EventArgs e)
        {
            SitOutEnabled(false);
            m_Game.SitOut(null);
        }
    }
}
