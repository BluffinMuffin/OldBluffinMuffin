using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class BlindOptionsBlinds : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.Blinds; } }
        public int BigBlindAmount { get { return MoneyUnit; } }
        public int SmallBlindAmount { get { return MoneyUnit / 2; } }
    }
}
