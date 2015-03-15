using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Windows.Forms.Game;

namespace BluffinMuffin.Client.Game
{
    public partial class CareerTableForm : TableForm
    {
        UserInfo User { get; set; }
        public CareerTableForm( UserInfo user )
        {
            User = user;
            InitializeComponent();
        }

        protected override int GetSitInMoneyAmount()
        {
            var parms = m_Game.Table.Params;
            if (User.TotalMoney < parms.LimitedMinimumBuyIn)
                return -1;
            var bif = new BuyInForm(User, m_Game.Table.Params);
            bif.ShowDialog();
            if (bif.Ok)
                return bif.BuyIn;
            return -1;
        }
    }
}
