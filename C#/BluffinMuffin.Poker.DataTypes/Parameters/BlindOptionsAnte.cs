using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class BlindOptionsAnte : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.Antes; } }
        public int AnteAmount { get { return MoneyUnit; } }
    }
}
