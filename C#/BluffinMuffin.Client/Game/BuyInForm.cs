using BluffinMuffin.Client.Properties;
using BluffinMuffin.Poker.DataTypes;
using System;
using System.Windows.Forms;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Client.Game
{
    public partial class BuyInForm : Form
    {
        public bool Ok { get; private set; }
        public int BuyIn { get; private set; }

        public BuyInForm(UserInfo user, TableParams parms)
        {
            InitializeComponent();
            lblAccountMoney.Text = Resources.BuyInForm_BuyInForm_Dollar + user.TotalMoney;
            lblMoneyUnit.Text = Resources.BuyInForm_BuyInForm_Dollar + parms.MoneyUnit;
            lblMin.Text = Resources.BuyInForm_BuyInForm_Dollar + parms.Lobby.MinimumAmountForBuyIn;
            lblMax.Text = Resources.BuyInForm_BuyInForm_Dollar + Math.Min(parms.Lobby.MaximumAmountForBuyIn, user.TotalMoney);
            nudBuyIn.Minimum = parms.Lobby.MinimumAmountForBuyIn;
            nudBuyIn.Maximum = (decimal)Math.Min(parms.Lobby.MaximumAmountForBuyIn, user.TotalMoney);
            nudBuyIn.Increment = parms.MoneyUnit;
            nudBuyIn.Value = parms.Lobby.MinimumAmountForBuyIn;
        }

        private void btnSitIn_Click(object sender, EventArgs e)
        {
            BuyIn = (int)nudBuyIn.Value;
            Ok = true;
            Close();
        }
    }
}
