using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;
using Com.Ericmas001.Games;
using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.GUI.Game
{
    public partial class PlayerHud : UserControl
    {
        private bool m_Main = false;
        private bool m_Alive = false;

        public string PlayerName
        {
            get { return lblName.Text; }
            set { lblName.Text = value; }
        }
        public bool Main
        {
            get { return m_Main; }
            set { m_Main = value; }
        }
        public bool Alive
        {
            get { return m_Alive; }
            set 
            { 
                m_Alive = value;
                if (m_Alive)
                {
                    if (m_Main)
                    {
                        lblName.BackColor = Color.FromArgb(112, 128, 214);
                        lblName.ForeColor = Color.White;
                    }
                    else
                    {
                        lblName.BackColor = Color.FromArgb(162, 178, 194);
                        lblName.ForeColor = Color.Black;
                    }
                    lblAction.BackColor = Color.White;
                    lblStatus.BackColor = Color.White;
                    pnlCenter.BackColor = Color.White;
                }
                else
                {
                    lblName.BackColor = Color.Gray;
                    lblAction.BackColor = Color.Gray;
                    lblStatus.BackColor = Color.Gray;
                    pnlCenter.BackColor = Color.Gray;
                }
            }
        }

        public PlayerHud()
        {
            InitializeComponent();
        }

        public void DoAction(GameActionEnum action )
        {
            DoAction(action, 0);
        }

        public void DoAction(GameActionEnum action, int amnt)
        {
            string s = "";
            switch (action)
            {
                case GameActionEnum.Call:
                    if (amnt == 0)
                        s = "CHECK";
                    else
                        s = "CALL";
                    break;
                case GameActionEnum.Raise:
                    if (amnt == -1)
                        s = "BET";
                    else
                        s = "RAISE";
                    break;
                case GameActionEnum.Fold:
                    s = "FOLD";
                    break;
            }
            lblAction.Text = s;
        }

        public void SetCards(GameCard c1, GameCard c2)
        {
            picCard1.Card = c1;
            picCard2.Card = c2;
        }

        public void SetMoney(int money)
        {
            lblStatus.Text = "$" + money;
        }

        public void SetDealerButtonVisible(bool visible)
        {
            picDealer.Button = visible ? ButtonPictureBox.ButtonType.Dealer : ButtonPictureBox.ButtonType.None;
        }

        public void SetSmallBlind()
        {
            picBlind.Button = ButtonPictureBox.ButtonType.SmallBlind;
        }

        public void SetBigBlind()
        {
            picBlind.Button = ButtonPictureBox.ButtonType.BigBlind;
        }

        public void SetNoBlind()
        {
            picBlind.Button = ButtonPictureBox.ButtonType.None;
        }

        public void SetPlaying()
        {
            if (m_Alive)
            {
                lblAction.BackColor = Color.Orange;
                lblAction.Text = "Thinking ...";
            }
        }

        public void SetWinning()
        {
            if (m_Alive)
            {
                lblAction.BackColor = Color.FromArgb(42, 186, 229);
                lblAction.Text = "WIN";
            }
        }

        public void SetSleeping()
        {
            if (m_Alive)
            {
                lblAction.BackColor = Color.White;
            }
        }
    }
}
