using BluffinMuffin.Poker.DataTypes;
using BluffinMuffin.Poker.Windows.Forms.Game;

namespace BluffinMuffin.Client.Game
{
    public partial class RegisteredModeTableForm : TableForm
    {
        UserInfo User { get; set; }
        public RegisteredModeTableForm( UserInfo user )
        {
            User = user;
            InitializeComponent();
        }

        protected override int GetSitInMoneyAmount()
        {
            var parms = m_Game.Table.Params;
            if (User.TotalMoney < parms.Lobby.MinimumAmountForBuyIn)
                return -1;
            var bif = new BuyInForm(User, m_Game.Table.Params);
            bif.ShowDialog();
            if (bif.Ok)
                return bif.BuyIn;
            return -1;
        }
    }
}
