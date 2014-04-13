using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.Poker.GUI.Lobby
{
    public partial class BlindUCBlinds : UserControl
    {
        public BlindUCBlinds()
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
