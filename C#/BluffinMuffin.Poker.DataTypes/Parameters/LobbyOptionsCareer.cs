using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class LobbyOptionsCareer : LobbyOptions
    {
        public override LobbyTypeEnum OptionType
        {
            get { return LobbyTypeEnum.Career; }
        }

        public int MoneyUnit { get; set; }
        public bool IsMaximumBuyInLimited { get; set; }

        public LobbyOptionsCareer()
        {
            MoneyUnit = 10;
            IsMaximumBuyInLimited = false;
        }

        public override int MaximumAmountForBuyIn
        {
            get { return IsMaximumBuyInLimited ? 100 * MoneyUnit : int.MaxValue; }
        }

        public override int MinimumAmountForBuyIn
        {
            get { return 20 * MoneyUnit; }
        }
    }
}
