using Com.Ericmas001.Game.Poker.DataTypes.Parameters;
using Com.Ericmas001.Game.Poker.GUI.Game;

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
