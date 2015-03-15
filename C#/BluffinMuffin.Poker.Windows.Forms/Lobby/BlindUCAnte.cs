using System;
using System.Windows.Forms;

namespace BluffinMuffin.Poker.Windows.Forms.Lobby
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
