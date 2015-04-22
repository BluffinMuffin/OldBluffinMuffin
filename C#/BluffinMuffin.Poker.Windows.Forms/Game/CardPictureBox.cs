using System.Windows.Forms;
using Com.Ericmas001.Games;
using System.Drawing;
using Com.Ericmas001.Games.Windows.Forms;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    public class CardPictureBox : PictureBox
    {
        private GameCard m_Card;
        public GameCard Card
        {
            set
            {
                m_Card = value;
                RefreshCard();
            }
        }

        public CardPictureBox()
        {
            Size = new Size(40, 56);
            BackColor = Color.Transparent;
        }

        private void RefreshCard()
        {
            if (m_Card == null || m_Card.Special == GameCardSpecial.Null)
                Image = null;
            else
                Image = m_Card.Special != GameCardSpecial.None ? CardImage.GetImage(m_Card.Special, 1) : CardImage.GetImage(m_Card.Kind, m_Card.Value, 1);
        }
    }
}
