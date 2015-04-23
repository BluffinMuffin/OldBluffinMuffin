using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class LimitOptionsFixed : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.FixedLimit; } }
    }
}
