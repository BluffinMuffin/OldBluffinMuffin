using Com.Ericmas001.Game.Poker.DataTypes;
using Com.Ericmas001.Game.Poker.DataTypes.Parameters;
using Com.Ericmas001.Game.Poker.GUI.Game;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Com.Ericmas001.Game.BluffinMuffin.Client.Game
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
            TableParams parms = m_Game.Table.Params;
            if (User.TotalMoney < parms.LimitedMinimumBuyIn)
                return -1;
            BuyInForm bif = new BuyInForm(User, m_Game.Table.Params);
            bif.ShowDialog();
            if (bif.OK)
                return bif.BuyIn;
            return -1;
        }
    }
}
