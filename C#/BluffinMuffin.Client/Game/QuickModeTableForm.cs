using BluffinMuffin.Poker.Windows.Forms.Game;
using BluffinMuffin.Protocol.DataTypes;

namespace BluffinMuffin.Client.Game
{
    public partial class QuickModeTableForm : TableForm
    {
        public QuickModeTableForm()
        {
            InitializeComponent();
        }

        protected override int GetSitInMoneyAmount()
        {
            return ((LobbyOptionsQuickMode)m_Game.Table.Params.Lobby).StartingAmount;
        }
    }
}
