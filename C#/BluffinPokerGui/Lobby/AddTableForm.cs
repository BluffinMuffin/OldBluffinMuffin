using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PokerWorld.Game;
using PokerWorld.Game.Enums;

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
        private BetEnum m_Limit;
        private bool m_TrainingOnly;
        private bool m_Training;
        private int m_TrainingStartingAmount;

        public bool Training
        {
            get { return m_Training; }
        }
        public int TrainingStartingAmount
        {
            get { return m_TrainingStartingAmount; }
        }
        public BetEnum Limit
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
            atcTraining.InitControl(m_PlayerName, nbPlayers);
            atcReal.InitControl(m_PlayerName, nbPlayers);
            if (trainingOnly)
                tabControl1.TabPages.Remove(tabReal);
            else
                tabControl1.TabPages.Remove(tabTraining);
        }

        private void GatherCommonFields(AddTableControl control)
        {
            m_TableName = control.TableName;
            m_BigBlind = control.BigBlind;
            m_NbPlayer = control.NbPlayer;
            m_WaitingTimeAfterPlayerAction = control.WaitingTimeAfterPlayerAction;
            m_WaitingTimeAfterBoardDealed = control.WaitingTimeAfterBoardDealed;
            m_WaitingTimeAfterPotWon = control.WaitingTimeAfterPotWon;
            m_Limit = control.Limit;
        }

        private void btnAddTraining_Click(object sender, EventArgs e)
        {
            GatherCommonFields(atcTraining);
            m_Training = true;
            m_TrainingStartingAmount = (int)nudStartingAmnt.Value;
            m_OK = true;
            Close();
        }

        private void btnAddReal_Click(object sender, EventArgs e)
        {
            GatherCommonFields(atcReal);
            m_Training = false;
            m_OK = true;
            Close();
        }
    }
}
