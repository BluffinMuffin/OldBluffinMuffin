using Com.Ericmas001.Game.Poker.DataTypes.Enums;

namespace Com.Ericmas001.Game.Poker.DataTypes.Parameters
{
    public class BlindOptionsBlinds : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.Blinds; } }
        public int BigBlindAmount { get { return MoneyUnit; } }
        public int SmallBlindAmount { get { return MoneyUnit / 2; } }
    }
}
