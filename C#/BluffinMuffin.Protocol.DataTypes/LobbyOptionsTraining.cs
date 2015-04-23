using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class LobbyOptionsTraining : LobbyOptions
    {
        public override LobbyTypeEnum OptionType
        {
            get { return LobbyTypeEnum.Training; }
        }

        public int StartingAmount { get; set; }

        public LobbyOptionsTraining()
        {
            StartingAmount = 1500;
        }

        public override int MaximumAmountForBuyIn
        {
            get { return StartingAmount; }
        }

        public override int MinimumAmountForBuyIn
        {
            get { return StartingAmount; }
        }
    }
}
