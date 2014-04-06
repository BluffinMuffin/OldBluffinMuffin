using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    public partial class NameUsedForm : Form
    {
        private string m_PlayerName;
        private bool m_OK = false;

        public bool OK
        {
            get { return m_OK; }
        }
        public string PlayerName
        {
            get { return m_PlayerName; }
        } 

        public NameUsedForm(string playerName) : base()
        {
            m_PlayerName = playerName;
            InitializeComponent();
            txtPlayerName.Text = m_PlayerName;
        }

        private void btnModify_Click(object sender, EventArgs e)
        {
            m_PlayerName = txtPlayerName.Text;
            m_OK = true;
            Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
