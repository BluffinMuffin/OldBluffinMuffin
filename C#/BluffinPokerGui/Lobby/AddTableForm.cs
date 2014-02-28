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

        public bool Training { get; private set; }
        public int TrainingStartingAmount { get; private set; }
        public BetEnum Limit { get; private set; }
        public int WaitingTimeAfterPotWon { get; private set; }
        public int WaitingTimeAfterBoardDealed { get; private set; }
        public int WaitingTimeAfterPlayerAction { get; private set; }
        public int NbPlayer { get; private set; }
        public int NbPlayerMin { get; private set; }
        public int BigBlind { get; private set; }
        public string TableName { get; private set; }
        public bool OK { get; private set; }
        public AddTableForm(string playerName, int nbPlayers, bool isTraining, List<RuleInfo> rules) : base()
        {
            OK = false;
            m_PlayerName = playerName;
            Training = isTraining;
            InitializeComponent();
            atcTraining.InitControl(m_PlayerName, nbPlayers);
            atcReal.InitControl(m_PlayerName, nbPlayers);
            if (isTraining)
                tabControl1.TabPages.Remove(tabReal);
            else
                tabControl1.TabPages.Remove(tabTraining);
        }

        private void GatherCommonFields(AddTableControl control)
        {
            TableName = control.TableName;
            BigBlind = control.BigBlind;
            NbPlayer = control.NbPlayer;
            WaitingTimeAfterPlayerAction = control.WaitingTimeAfterPlayerAction;
            WaitingTimeAfterBoardDealed = control.WaitingTimeAfterBoardDealed;
            WaitingTimeAfterPotWon = control.WaitingTimeAfterPotWon;
            Limit = control.Limit;
            NbPlayerMin = control.NbPlayerMin;
        }

        private void btnAddTraining_Click(object sender, EventArgs e)
        {
            GatherCommonFields(atcTraining);
            Training = true;
            TrainingStartingAmount = (int)nudStartingAmnt.Value;
            OK = true;
            Close();
        }

        private void btnAddReal_Click(object sender, EventArgs e)
        {
            GatherCommonFields(atcReal);
            Training = false;
            OK = true;
            Close();
        }
    }
}
