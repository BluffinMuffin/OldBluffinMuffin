using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using EricUtility.Games.CardGame;
using System.Drawing;
using EricUtility.Games.Windows.Forms;

namespace BluffinPokerGui.Game
{
    public class CardPictureBox : PictureBox
    {
        private GameCard m_Card;
        public GameCard Card
        {
            get
            {
                return m_Card;
            }
            set
            {
                m_Card = value;
                RefreshCard();
            }
        }

        public CardPictureBox()
            : base()
        {
            Size = new Size(40, 56);
            BackColor = Color.Transparent;
        }

        private void RefreshCard()
        {
            if (m_Card == null || m_Card.Special == GameCardSpecial.Null)
                Image = null;
            else
            {
                if (m_Card.Special != GameCardSpecial.None)
                    Image = CardImage.GetImage(m_Card.Special, 1);
                else
                    Image = CardImage.GetImage(m_Card.Kind, m_Card.Value, 1);
            }
        }
    }
}
