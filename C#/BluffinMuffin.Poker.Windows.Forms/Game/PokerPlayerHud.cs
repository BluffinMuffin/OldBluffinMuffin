using System.Drawing;
using System.Windows.Forms;
using BluffinMuffin.Poker.Windows.Forms.Properties;
using Com.Ericmas001.Games;
using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public partial class PlayerHud : UserControl
    {
        private bool m_Main;
        private bool m_Alive;

        public string PlayerName
        {
            set { lblName.Text = value; }
        }
        public bool Main
        {
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

        public void DoAction(GameActionEnum action, int amnt = 0)
        {
            var s = "";
            switch (action)
            {
                case GameActionEnum.Call:
                    s = amnt == 0 ? "CHECK" : "CALL";
                    break;
                case GameActionEnum.Raise:
                    s = amnt == -1 ? "BET" : "RAISE";
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
            lblStatus.Text = Resources.PlayerHud_SetMoney_Dollar + money;
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
                lblAction.Text = Resources.PlayerHud_SetPlaying_Thinking;
            }
        }

        public void SetWinning()
        {
            if (m_Alive)
            {
                lblAction.BackColor = Color.FromArgb(42, 186, 229);
                lblAction.Text = Resources.PlayerHud_SetWinning_WIN;
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
