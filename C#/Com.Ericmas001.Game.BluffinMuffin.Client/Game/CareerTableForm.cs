using Com.Ericmas001.Game.Poker.GUI.Game;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Game
{
    public partial class CareerTableForm : TableForm
    {
        public CareerTableForm()
        {
            InitializeComponent();
        }

        protected override int GetSitInMoneyAmount()
        {
            MessageBox.Show("TODO: Ask for amount. For now, putting 1542");
            return 1542;
        }
    }
}
