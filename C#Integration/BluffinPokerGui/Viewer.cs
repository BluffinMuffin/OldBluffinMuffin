using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using EricUtility.Games.Windows.Forms;
using EricUtility.Games.CardGame;

namespace BluffinPokerGui
{
    public partial class Viewer : Form
    {
        public Viewer()
        {
            InitializeComponent();
            pictureBox1.BackgroundImage = CardImage.GetImage(GameCardSpecial.Hidden, 1);
        }

        private void button1_Click(object sender, EventArgs e)
        {
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            GameCardKind kind = (GameCardKind)numericUpDown1.Value;
            GameCardValue v = (GameCardValue)numericUpDown2.Value;
            pictureBox1.BackgroundImage = CardImage.GetImage(kind, v, 1);
            cardPictureBox1.Card = new GameCard(kind, v);
        }
    }
}
