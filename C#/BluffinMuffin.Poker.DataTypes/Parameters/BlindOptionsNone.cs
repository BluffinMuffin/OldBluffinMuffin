using BluffinMuffin.Poker.DataTypes.Enums;

namespace BluffinMuffin.Poker.DataTypes.Parameters
{
    public class BlindOptionsNone : BlindOptions
    {
        public override BlindTypeEnum OptionType { get { return BlindTypeEnum.None; } }
    }
}