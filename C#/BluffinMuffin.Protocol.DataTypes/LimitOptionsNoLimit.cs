using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class LimitOptionsNoLimit : LimitOptions
    {
        public override LimitTypeEnum OptionType { get { return LimitTypeEnum.NoLimit; } }
    }
}
