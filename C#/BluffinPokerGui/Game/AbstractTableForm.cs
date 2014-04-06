using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using EricUtility;
using Com.Ericmas001.Game.Poker.DataTypes;

namespace BluffinPokerGui.Game
{
    public partial class AbstractTableForm : Form, IPokerViewer
    {
        private bool m_IsBeingKilled = false;
        protected IPokerGame m_Game;
        protected int m_NoSeat;
        public AbstractTableForm()
        {
            InitializeComponent();
        }

        public virtual void SetGame(IPokerGame c, int s)
        {
            m_Game = c;
            m_NoSeat = s;
        }

        public virtual void Start()
        {
            Show();
        }

        public bool DirectKill{ get{ return m_IsBeingKilled;}}

        public delegate void EmptyDelegate();
        public void ForceKill()
        {
            if (InvokeRequired)
            {
                // We're not in the UI thread, so we need to call BeginInvoke
                BeginInvoke(new EmptyDelegate(ForceKill), new object[] {});
                return;
            }
            m_IsBeingKilled = true;
            Close();
        }
    }
}
