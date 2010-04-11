using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using PokerWorld.Game;

namespace BluffinPokerGUI.Lobby
{
    public partial class AddTableForm : Form
    {
        private readonly string m_PlayerName;

        private bool m_OK;
        private string m_TableName;
        private int m_BigBlind;
        private int m_NbPlayer;
        private int m_WaitingTimeAfterPlayerAction;
        private int m_WaitingTimeAfterBoardDealed;
        private int m_WaitingTimeAfterPotWon;
        private TypeBet m_Limit;

        public TypeBet Limit
        {
            get { return m_Limit; }
        }
        public int WaitingTimeAfterPotWon
        {
            get { return m_WaitingTimeAfterPotWon; }
        }
        public int WaitingTimeAfterBoardDealed
        {
            get { return m_WaitingTimeAfterBoardDealed; }
        }
        public int WaitingTimeAfterPlayerAction
        {
            get { return m_WaitingTimeAfterPlayerAction; }
        }
        public int NbPlayer
        {
            get { return m_NbPlayer; }
        }
        public int BigBlind
        {
            get { return m_BigBlind; }
        }
        public string TableName
        {
            get { return m_TableName; }
        }
        public bool OK
        {
            get { return m_OK; }
        }
        public AddTableForm(string playerName, int nbPlayers) : base()
        {
            m_OK = false;
            m_PlayerName = playerName;
            InitializeComponent();
            txtTableName.Text = m_PlayerName + " Table";
            foreach (string s in Enum.GetNames(typeof(TypeBet)))
                clstGameLimit.Items.Add(s);
            clstGameLimit.SelectedItem = TypeBet.NoLimit.ToString();
            nudNbPlayers.Minimum = Math.Max(nbPlayers,2);
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            m_TableName = txtTableName.Text;
            m_BigBlind = Convert.ToInt32(nudBigBlindAmnt.Value);
            m_NbPlayer = Convert.ToInt32(nudNbPlayers.Value);
            m_WaitingTimeAfterPlayerAction = Convert.ToInt32(nudWTAPlayerAction.Value);
            m_WaitingTimeAfterBoardDealed = Convert.ToInt32(nudWTABoardDealed.Value);
            m_WaitingTimeAfterPotWon = Convert.ToInt32(nudWTAPotWon.Value);
            m_Limit = (TypeBet)clstGameLimit.SelectedIndex;
            m_OK = true;
            Close();
        }
    }
}
