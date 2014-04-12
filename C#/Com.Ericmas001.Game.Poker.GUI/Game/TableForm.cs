using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.EventHandling;
using System.Threading;
using Com.Ericmas001.Util;
using VIBlend.WinForms.Controls;

namespace Com.Ericmas001.Game.Poker.GUI.Game
{
    public partial class TableForm : TableViewerForm
    {
        public TableForm()
        {
            InitializeComponent();
        }

        private void btnFold_Click(object sender, EventArgs e)
        {
            DisableButtons();
            TableInfo table = m_Game.Table;
            PlayerInfo p = table.Seats[m_NoSeat];
            m_Game.PlayMoney(p, -1);
        }

        private void btnCall_Click(object sender, EventArgs e)
        {
            DisableButtons();
            TableInfo table = m_Game.Table;
            PlayerInfo p = table.Seats[m_NoSeat];
            m_Game.PlayMoney(p, table.CallAmnt(p));
        }

        private void btnRaise_Click(object sender, EventArgs e)
        {
            DisableButtons();
            TableInfo table = m_Game.Table;
            PlayerInfo p = table.Seats[m_NoSeat];
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
            m_Game.PlayerActionNeeded += m_Game_PlayerActionNeeded;
            m_Game.SitInResponseReceived += m_Game_SitInResponseReceived;
            m_Game.SitOutResponseReceived += m_Game_SitOutResponseReceived;
            m_Game.SeatUpdated += m_Game_GameGenerallyUpdated;
            m_Game.GameGenerallyUpdated += m_Game_GameGenerallyUpdated;
        }

        void m_Game_SitOutResponseReceived(bool success)
        {
            SitOutEnabled(!success);
            if (success)
            {
                m_NoSeat = -1;
                SitInButtonsShowing(true);
            }
        }

        void m_Game_GameGenerallyUpdated(object sender, EventArgs e)
        {
            SitInButtonsShowing(m_NoSeat == -1);
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
            TableInfo table = m_Game.Table;
            if (p.NoSeat == m_NoSeat)
            {
                btnFold.Enabled = true;
                SetCallButtonName(p);
                btnCall.Enabled = true;
                if (table.HigherBet < p.MoneyAmnt)
                {
                    int min = table.MinRaiseAmnt(p) + p.MoneyBetAmnt;
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
            TableInfo table = m_Game.Table;
            string s = "CALL";
            if (table.CanCheck(p))
                s = "CHECK";
            else if (table.HigherBet >= p.MoneyAmnt)
                s = "ALL-IN";
            btnCall.Text = s;
        }

        private void btnSitIn_Click(object sender, EventArgs e)
        {
            SitInButtonsShowing(false);
            string name = ((Control)sender).Name;
            int seatWanted = int.Parse(name.Substring(name.Length-1));
            m_Game.SitIn(null, seatWanted);
        }

        void m_Game_SitInResponseReceived(int noSeat)
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
            for (int i = 0; i < 10; ++i )
            {
                vButton btnSitIn = Controls["btnSitIn" + i] as vButton;
                if (i < m_Game.Table.Seats.Count && m_Game.Table.Seats[i] == null)
                    btnSitIn.Visible = visible;
                else
                    btnSitIn.Visible = false;
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
