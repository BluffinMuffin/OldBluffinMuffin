using Com.Ericmas001.Game.Poker.DataTypes.Parameters;
using Com.Ericmas001.Game.Poker.GUI.Game;
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
    public partial class TrainingTableForm : TableForm
    {
        public TrainingTableForm()
        {
            InitializeComponent();
        }

        protected override int GetSitInMoneyAmount()
        {
            return ((LobbyOptionsTraining)m_Game.Table.Params.Lobby).StartingAmount;
        }
    }
}
