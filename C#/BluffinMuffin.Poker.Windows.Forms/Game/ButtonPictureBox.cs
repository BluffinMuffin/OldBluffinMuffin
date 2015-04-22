using System.Windows.Forms;
using System.Drawing;
using BluffinMuffin.Poker.Windows.Forms.Properties;

namespace BluffinMuffin.Poker.Windows.Forms.Game
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
            set
            {
                m_Button = value;
                RefreshButton();
            }
        }

        public ButtonPictureBox()
        {
            Size = new Size(30, 30);
            BackColor = Color.Transparent;
        }

        private void RefreshButton()
        {
            switch (m_Button)
            {
                case ButtonType.None: Image = null; break;
                case ButtonType.Dealer: Image = Resources.dealer; break;
                case ButtonType.SmallBlind: Image = Resources.small_blind; break;
                case ButtonType.BigBlind: Image = Resources.big_blind; break;
            }
        }
    }
}
