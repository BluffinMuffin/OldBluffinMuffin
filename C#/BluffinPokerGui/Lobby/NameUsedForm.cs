using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PokerWorld.Game;

namespace BluffinPokerGUI.Lobby
{
    public partial class NameUsedForm : Form
    {
        private string m_PlayerName;

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
            Close();
        }
    }
}
