using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class BlindOptionsAnte : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.Antes; } }
        public int AnteAmount { get { return MoneyUnit; } }
    }
}
