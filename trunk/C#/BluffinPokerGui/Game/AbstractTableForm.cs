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
    public partial class AbstractTableForm : Form, IPokerViewer
    {
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
    }
}
