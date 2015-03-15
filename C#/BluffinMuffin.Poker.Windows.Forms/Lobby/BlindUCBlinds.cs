using System;
using System.Windows.Forms;

namespace BluffinMuffin.Poker.Windows.Forms.Lobby
{
    public partial class BlindUcBlinds : UserControl
    {
        public BlindUcBlinds()
        {
            InitializeComponent();
        }

        public void SetBlinds( int bigblind )
        {
            lblSmallBlind.Text = String.Format("${0}", bigblind / 2);
            lblBigBlind.Text = String.Format("${0}", bigblind);
        }
    }
}
