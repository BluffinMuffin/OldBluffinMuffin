using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class LimitOptionsPot : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.PotLimit; } }
    }
}
