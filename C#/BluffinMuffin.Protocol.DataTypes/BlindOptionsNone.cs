using BluffinMuffin.Protocol.DataTypes.Enums;

namespace BluffinMuffin.Protocol.DataTypes
{
    public class BlindOptionsNone : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.None; } }
    }
}