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
            StringWriter sw = new StringWriter();
            TableParams parms = m_Game.Table.Params;
            sw.WriteLine("Your account have ${0}", User.TotalMoney);
            sw.WriteLine("Current Money Unit is ${0}", parms.MoneyUnit);
            sw.WriteLine("Minimum amount is ${0}", parms.LimitedMinimumBuyIn);
            sw.WriteLine("If limited, Maximum amount would ${0}", parms.LimitedMaximumBuyIn);
            sw.WriteLine("The maximum buy-in is currently {0}limited !!", parms.LimitMaximumBuyIn ? "" : "un");
            sw.WriteLine("");
            sw.WriteLine("TODO: Ask for amount. Currently putting 1542");
            MessageBox.Show(sw.ToString());
            return 1542;
        }
    }
}
