using Com.Ericmas001.Game.BluffinMuffin.Client.Properties;
using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;
using System;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Game
{
    public partial class BuyInForm : Form
    {
        public bool Ok { get; set; }
        public int BuyIn { get; set; }

        public BuyInForm(UserInfo user, TableParams parms)
        {
            InitializeComponent();
            lblAccountMoney.Text = Resources.BuyInForm_BuyInForm_Dollar + user.TotalMoney;
            lblMoneyUnit.Text = Resources.BuyInForm_BuyInForm_Dollar + parms.MoneyUnit;
            lblMin.Text = Resources.BuyInForm_BuyInForm_Dollar + parms.LimitedMinimumBuyIn;
            lblMax.Text = Resources.BuyInForm_BuyInForm_Dollar + Math.Min(parms.LimitMaximumBuyIn ? parms.LimitedMaximumBuyIn : int.MaxValue, user.TotalMoney);
            nudBuyIn.Minimum = parms.LimitedMinimumBuyIn;
            nudBuyIn.Maximum = (decimal)Math.Min(parms.LimitMaximumBuyIn ? parms.LimitedMaximumBuyIn : int.MaxValue, user.TotalMoney);
            nudBuyIn.Increment = parms.MoneyUnit;
            nudBuyIn.Value = parms.LimitedMinimumBuyIn;
        }

        private void btnSitIn_Click(object sender, EventArgs e)
        {
            BuyIn = (int)nudBuyIn.Value;
            Ok = true;
            Close();
        }
    }
}
