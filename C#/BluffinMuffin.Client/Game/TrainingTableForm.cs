using BluffinMuffin.Poker.DataTypes.Parameters;
using BluffinMuffin.Poker.Windows.Forms.Game;

namespace BluffinMuffin.Client.Game
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
