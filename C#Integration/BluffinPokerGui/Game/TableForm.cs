using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using PokerWorld.Game;

namespace BluffinPokerGui.Game
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
            PlayerInfo p = table.Players[m_NoSeat];
            m_Game.PlayMoney(p, -1);
        }

        private void btnCall_Click(object sender, EventArgs e)
        {
            DisableButtons();
            TableInfo table = m_Game.Table;
            PlayerInfo p = table.Players[m_NoSeat];
            m_Game.PlayMoney(p, table.CallAmnt(p));
        }

        private void btnRaise_Click(object sender, EventArgs e)
        {
            DisableButtons();
            TableInfo table = m_Game.Table;
            PlayerInfo p = table.Players[m_NoSeat];
            m_Game.PlayMoney(p, (int)nudRaise.Value - p.MoneyBetAmnt);
        }

        private void DisableButtons()
        {
            btnCall.Enabled = false;
            btnRaise.Enabled = false;
            btnFold.Enabled = false;
            nudRaise.Enabled = false;
        }
        public override void SetGame(PokerWorld.Game.IPokerGame c, int s)
        {
            base.SetGame(c, s);
            m_Game.PlayerActionNeeded += new EventHandler<PokerWorld.Game.HistoricPlayerInfoEventArgs>(m_Game_PlayerActionNeeded);
        }

        void m_Game_PlayerActionNeeded(object sender, PokerWorld.Game.HistoricPlayerInfoEventArgs e)
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
    }
}
