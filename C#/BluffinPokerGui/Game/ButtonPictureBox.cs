using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EricUtility.Games.CardGame;
using System.Drawing;
using EricUtility.Games.Windows.Forms;

namespace BluffinPokerGui.Game
{
    public class ButtonPictureBox : PictureBox
    {
        public enum ButtonType
        {
            None,
            Dealer,
            SmallBlind,
            BigBlind
        }
        private ButtonType m_Button;
        public ButtonType Button
        {
            get
            {
                return m_Button;
            }
            set
            {
                m_Button = value;
                RefreshButton();
            }
        }

        public ButtonPictureBox()
            : base()
        {
            Size = new Size(30, 30);
            BackColor = Color.Transparent;
        }

        private void RefreshButton()
        {
            switch (m_Button)
            {
                case ButtonType.None: Image = null; break;
                case ButtonType.Dealer: Image = Properties.Resources.dealer; break;
                case ButtonType.SmallBlind: Image = Properties.Resources.small_blind; break;
                case ButtonType.BigBlind: Image = Properties.Resources.big_blind; break;
            }
        }
    }
}
