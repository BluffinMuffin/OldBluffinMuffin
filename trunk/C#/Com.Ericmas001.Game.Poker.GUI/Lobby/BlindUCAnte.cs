using System;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    public partial class BlindUcAnte : UserControl
    {
        public BlindUcAnte()
        {
            InitializeComponent();
        }

        public void SetAnte( int ante )
        {
            lblAnte.Text = String.Format("${0}", ante);
        }
    }
}
