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
        private bool m_TrainingOnly;
        private bool m_Training;

        public bool Training
        {
            get { return m_Training; }
        }
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
        public AddTableForm(string playerName, int nbPlayers, bool trainingOnly) : base()
        {
            m_OK = false;
            m_PlayerName = playerName;
            m_TrainingOnly = trainingOnly;
            InitializeComponent();
            txtTableName.Text = m_PlayerName + " Table";
            foreach (string s in Enum.GetNames(typeof(TypeBet)))
                clstGameLimit.Items.Add(s);
            clstGameLimit.SelectedItem = TypeBet.NoLimit.ToString();
            nudNbPlayers.Minimum = Math.Max(nbPlayers, 2);
            chkTraining.Checked = trainingOnly;
            chkTraining.Enabled = !trainingOnly;
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            m_TableName = txtTableName.Text;
            m_BigBlind = (int)nudBigBlindAmnt.Value;
            m_NbPlayer = (int)nudNbPlayers.Value;
            m_WaitingTimeAfterPlayerAction = (int)nudWTAPlayerAction.Value;
            m_WaitingTimeAfterBoardDealed = (int)nudWTABoardDealed.Value;
            m_WaitingTimeAfterPotWon = (int)nudWTAPotWon.Value;
            m_Limit = (TypeBet)clstGameLimit.SelectedIndex;
            m_Training = chkTraining.Checked;
            m_OK = true;
            Close();
        }
    }
}
