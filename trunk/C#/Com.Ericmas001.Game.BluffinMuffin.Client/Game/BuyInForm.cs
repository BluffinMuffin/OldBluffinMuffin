using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;
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
    public partial class BuyInForm : Form
    {
        public bool OK { get; set; }
        public int BuyIn { get; set; }

        public BuyInForm(UserInfo user, TableParams parms)
        {
            InitializeComponent();
            lblAccountMoney.Text = "$" + user.TotalMoney;
            lblMoneyUnit.Text = "$" + parms.MoneyUnit;
            lblMin.Text = "$" + parms.LimitedMinimumBuyIn;
            lblMax.Text = "$" + Math.Min(parms.LimitMaximumBuyIn ? parms.LimitedMaximumBuyIn : int.MaxValue, user.TotalMoney);
            nudBuyIn.Minimum = parms.LimitedMinimumBuyIn;
            nudBuyIn.Maximum = (decimal)Math.Min(parms.LimitMaximumBuyIn ? parms.LimitedMaximumBuyIn : int.MaxValue, user.TotalMoney);
            nudBuyIn.Increment = parms.MoneyUnit;
            nudBuyIn.Value = parms.LimitedMinimumBuyIn;
        }

        private void btnSitIn_Click(object sender, EventArgs e)
        {
            BuyIn = (int)nudBuyIn.Value;
            OK = true;
            Close();
        }
    }
}
