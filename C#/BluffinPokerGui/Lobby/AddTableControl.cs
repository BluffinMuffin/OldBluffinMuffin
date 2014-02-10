using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using PokerWorld.Game;
using PokerWorld.Game.Enums;

namespace BluffinPokerGUI.Lobby
{
    public partial class AddTableControl : UserControl
    {
        public BetEnum Limit
        {
            get { return (BetEnum)clstGameLimit.SelectedIndex; }
        }
        public int WaitingTimeAfterPotWon
        {
            get { return (int)nudWTAPotWon.Value; }
        }
        public int WaitingTimeAfterBoardDealed
        {
            get { return (int)nudWTABoardDealed.Value; }
        }
        public int WaitingTimeAfterPlayerAction
        {
            get { return (int)nudWTAPlayerAction.Value; }
        }
        public int NbPlayer
        {
            get { return (int)nudNbPlayers.Value; }
        }
        public int BigBlind
        {
            get { return (int)nudBigBlindAmnt.Value; }
        }
        public string TableName
        {
            get { return txtTableName.Text; }
        }
        public AddTableControl()
        {
            InitializeComponent();
        }
        public void InitControl(string playerName, int nbPlayers)
        {
            txtTableName.Text = playerName + " Table";
            foreach (string s in Enum.GetNames(typeof(BetEnum)))
                clstGameLimit.Items.Add(s);
            clstGameLimit.SelectedItem = BetEnum.NoLimit.ToString();
            nudNbPlayers.Minimum = Math.Max(nbPlayers, 2);
        }
    }
}
